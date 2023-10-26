package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctOfSingletons;
import ai.maths.sat3.model.Negation;
import ai.maths.sat3.model.Variable;

public class Probability {

    public static double probability(Clause<?> clause) {
        if (clause == DisjunctOfSingletons.FALSE) {
            return 0d;
        }
        if (clause == ConjunctOfSingletons.TRUE) {
            return 1d;
        }
        if (clause instanceof Variable) {
            return 1d / 2;
        }
        if (clause instanceof Negation) {
            return 1d - probability(((Negation<?>) clause).getNegatedClause());
        }
        if (clause instanceof DisjunctOfSingletons) {
            return 1d - 1d / Math.pow(2, clause.getVariables().size());
        }
        if (clause instanceof ConjunctOfSingletons) {
            return 1d / Math.pow(2, clause.getVariables().size());
        }
        if (clause instanceof CNF) {
            SimplifyCNF simplifiedCNF = SimplifyCNF.simplify((CNF<?>) clause);
            return probabilityOfCNFOrDisjunctOfSingletonsOrSingleton(simplifiedCNF.getCnfOrDisjunctOfSingletonsOrSingleton())
                    / Math.pow(2, simplifiedCNF.getLostVariables().size());
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    private static double probabilityOfCNFOrDisjunctOfSingletonsOrSingleton(CNF<?> simplifiedCNF) {
        Set<CNF<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts(simplifiedCNF);
        if (independentConnectedConjuncts.size() == 1) {
            simplifiedCNF = independentConnectedConjuncts.iterator().next();
            SplitClauses split = SplitClauses.split(simplifiedCNF);
            return (probability(split.getRest()) -
                    probability(split.getDisconnectedFromFirst()) / Math.pow(2, split.getFirst().getVariables().size()));
        }
        return independentConnectedConjuncts.stream().mapToDouble(Probability::probability).reduce(1, (left, right) -> left * right);
    }
}
