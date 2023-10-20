package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.Conjuncts;
import ai.maths.sat3.model.ConjunctsOfSingletons;
import ai.maths.sat3.model.Disjuncts;
import ai.maths.sat3.model.DisjunctsOfSingletons;
import ai.maths.sat3.model.Negation;
import ai.maths.sat3.model.ThreeSatConjuncts;
import ai.maths.sat3.model.Variable;
import ai.maths.sat3.sets.ConnectedVariables;

public class Probability {

    public static double probability(Clause<?> clause) {
        if (clause == Disjuncts.FALSE) {
            return 0;
        }
        if (clause == Conjuncts.TRUE) {
            return 1;
        }
        if (clause instanceof Variable) {
            return 1d / 2;
        }
        if (clause instanceof Negation) {
            return 1 - probability(clause.getAnySubClause());
        }
        if (clause instanceof DisjunctsOfSingletons) {
            return 1 - 1 / Math.pow(2, clause.getSubClauses().count());
        }
        if (clause instanceof ConjunctsOfSingletons) {
            return 1 / Math.pow(2, clause.getSubClauses().count());
        }
        if (clause instanceof ThreeSatConjuncts) {
            Set<Clause<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts((ThreeSatConjuncts) clause);
            if (independentConnectedConjuncts.size() == 1) {
                Clause<?> next = independentConnectedConjuncts.iterator().next();
                if (next instanceof ThreeSatConjuncts) {
                    SplitThreeSatClause split = SplitThreeSatClause.split((ThreeSatConjuncts) next);
                    return probability(split.getRest()) + probability(ClauseBuilder.buildNegation(split.getFirst())) * probability(split.makeRestIndependentToFirst());

                }
                return probability(next);
            }
            return independentConnectedConjuncts
                    .stream().mapToDouble(variables -> probability(clause)).reduce(1, (left, right) -> left * right);
        }
        return 0;
    }
}
