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

public class SolutionCounter {

    public static long countSolutions(Clause<?> clause) {
        if (clause == DisjunctsOfSingletons.FALSE) {
            return 0;
        }
        if (clause == ConjunctsOfSingletons.TRUE) {
            return 1;
        }
        if (clause instanceof Variable) {
            return 1;
        }
        if (clause instanceof Negation) {
            return (long) Math.pow(2, clause.getVariables().size()) - countSolutions(clause.getAnySubClause());
        }
        if (clause instanceof DisjunctsOfSingletons) {
            return (long) Math.pow(2, clause.getVariables().size()) - 1;
        }
        if (clause instanceof ConjunctsOfSingletons) {
            return 1;
        }
        if (clause instanceof CNF) {
            return probabilityOfCNFOrDisjunctOfSingletonsOrSingleton(SimplifyCNF.simplify((CNF<?>) clause));
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    private static Long probabilityOfCNFOrDisjunctOfSingletonsOrSingleton(CNFOrDisjunctOfSingletonsOrSingleton<?> cnfOrDisjunctOfSingletonsOrSingleton) {
        if (cnfOrDisjunctOfSingletonsOrSingleton instanceof CNF && !(cnfOrDisjunctOfSingletonsOrSingleton instanceof ConjunctsOfSingletons)) {
            Set<CNFOrDisjunctOfSingletonsOrSingleton<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) cnfOrDisjunctOfSingletonsOrSingleton);
            if (independentConnectedConjuncts.size() == 1) {
                cnfOrDisjunctOfSingletonsOrSingleton = independentConnectedConjuncts.iterator().next();
                if (cnfOrDisjunctOfSingletonsOrSingleton instanceof CNF && !(cnfOrDisjunctOfSingletonsOrSingleton instanceof ConjunctsOfSingletons)) {
                    SplitClauses split = SplitClauses.split((CNF<?>) cnfOrDisjunctOfSingletonsOrSingleton);
                    CNFOrDisjunctOfSingletonsOrSingleton<?> cnf = ClauseBuilder.buildCNF(split.getRest());
                    CNFOrDisjunctOfSingletonsOrSingleton<?> independentCNF = ClauseBuilder.buildCNF(split.getDisconnectedFromFirst());
                    return countSolutions(cnf) *
                            (long) Math.pow(2, split.getFirst().getVariables().stream()
                                    .filter(variable -> !cnf.getVariables().contains(variable)).count())
                            - countSolutions(independentCNF) *
                            (long) Math.pow(2, cnf.getVariables().stream()
                                    .filter(variable -> !independentCNF.getVariables().contains(variable) && !split.getFirst().getVariables().contains(variable)).count());
                }
                return countSolutions(cnfOrDisjunctOfSingletonsOrSingleton);
            }
            return independentConnectedConjuncts.stream().mapToLong(SolutionCounter::countSolutions).reduce(1, (left, right) -> left * right);
        }
        return countSolutions(cnfOrDisjunctOfSingletonsOrSingleton);
    }
}
