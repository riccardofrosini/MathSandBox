package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.CNFOrDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.ConjunctsOfSingletons;
import ai.maths.sat3.model.DisjunctsOfSingletons;
import ai.maths.sat3.model.Negation;
import ai.maths.sat3.model.Variable;

public class Probability {

    public static double probability(Clause<?> clause) {
        if (clause == DisjunctsOfSingletons.FALSE) {
            return 0d;
        }
        if (clause == ConjunctsOfSingletons.TRUE) {
            return 1d;
        }
        if (clause instanceof Variable) {
            return 1d / 2;
        }
        if (clause instanceof Negation) {
            return 1d - probability(clause.getAnySubClause());
        }
        if (clause instanceof DisjunctsOfSingletons) {
            return 1d - 1d / Math.pow(2, clause.getVariables().size());
        }
        if (clause instanceof ConjunctsOfSingletons) {
            return 1d / Math.pow(2, clause.getVariables().size());
        }
        if (clause instanceof CNF) {
            SimplifyCNF simplifiedCNFWithLostVariables = SimplifyCNF.simplify((CNF<?>) clause);
            CNFOrDisjunctOfSingletonsOrSingleton<?> cnForDisjunctOfSingletonsOrSingleton = simplifiedCNFWithLostVariables.getCnfOrDisjunctOfSingletonsOrSingleton();
            if (cnForDisjunctOfSingletonsOrSingleton instanceof CNF) {
                Set<CNFOrDisjunctOfSingletonsOrSingleton<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) clause);
                if (independentConnectedConjuncts.size() == 1) {
                    cnForDisjunctOfSingletonsOrSingleton = independentConnectedConjuncts.iterator().next();
                    if (cnForDisjunctOfSingletonsOrSingleton instanceof CNF) {
                        SplitClauses split = SplitClauses.split((CNF<?>) cnForDisjunctOfSingletonsOrSingleton);
                        return (probability(ClauseBuilder.buildCNF(split.getRest())) -
                                probability(ClauseBuilder.buildCNF(split.getDisconnectedFromFirst())) / Math.pow(2, split.getFirst().getVariables().size())) / Math.pow(2,
                                simplifiedCNFWithLostVariables.getVariables().size());
                    }
                    return probability(cnForDisjunctOfSingletonsOrSingleton) / Math.pow(2, simplifiedCNFWithLostVariables.getVariables().size());
                }
                return independentConnectedConjuncts.stream().mapToDouble(Probability::probability).reduce(1, (left, right) -> left * right) / Math.pow(2,
                        simplifiedCNFWithLostVariables.getVariables().size());
            }
            return probability(cnForDisjunctOfSingletonsOrSingleton) / Math.pow(2, simplifiedCNFWithLostVariables.getVariables().size());
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }
}
