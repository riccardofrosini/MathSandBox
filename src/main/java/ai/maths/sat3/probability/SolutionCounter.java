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
            return (long) Math.pow(2, clause.getSubClauses().count()) - countSolutions(clause.getAnySubClause());
        }
        if (clause instanceof DisjunctsOfSingletons) {
            return (long) Math.pow(2, clause.getSubClauses().count()) - 1;
        }
        if (clause instanceof ConjunctsOfSingletons) {
            return 1;
        }
        if (clause instanceof CNF) {
            Set<CNFOrDisjunctOfSingletonsOrSingleton<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) clause);
            if (independentConnectedConjuncts.size() == 1) {
                CNFOrDisjunctOfSingletonsOrSingleton<?> cnfOrDisjunctOfSingletonsOrSingleton = independentConnectedConjuncts.iterator().next();
                if (cnfOrDisjunctOfSingletonsOrSingleton instanceof CNF) {
                    SplitClauses split = SplitClauses.split((CNF<?>) cnfOrDisjunctOfSingletonsOrSingleton);
                    CNFOrDisjunctOfSingletonsOrSingleton<?> cnf = ClauseBuilder.buildCNF(split.getRest());
                    CNFOrDisjunctOfSingletonsOrSingleton<?> independentCNF = ClauseBuilder.buildCNF(split.makeRestIndependentToFirst());
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
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }
}
