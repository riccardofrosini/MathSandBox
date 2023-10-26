package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctOfSingletons;
import ai.maths.sat3.model.Negation;
import ai.maths.sat3.model.Variable;

public class SolutionCounter {

    public static long countSolutions(Clause<?> clause) {
        if (clause == DisjunctOfSingletons.FALSE) {
            return 0;
        }
        if (clause == ConjunctOfSingletons.TRUE) {
            return 1;
        }
        if (clause instanceof Variable) {
            return 1;
        }
        if (clause instanceof Negation) {
            return (long) Math.pow(2, clause.getVariables().size()) - countSolutions(((Negation<?>) clause).getNegatedClause());
        }
        if (clause instanceof DisjunctOfSingletons) {
            return (long) Math.pow(2, clause.getVariables().size()) - 1;
        }
        if (clause instanceof ConjunctOfSingletons) {
            return 1;
        }
        if (clause instanceof CNF) {
            SimplifyCNF simplifiedCNF = SimplifyCNF.simplify((CNF<?>) clause);
            return countSolutionsOfCNFOrDisjunctOfSingletonsOrSingleton(simplifiedCNF.getCnfOrDisjunctOfSingletonsOrSingleton())
                    * (long) Math.pow(2, simplifiedCNF.getLostVariablesNotGivenAsTrue().size());
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    private static Long countSolutionsOfCNFOrDisjunctOfSingletonsOrSingleton(CNF<?> cnfOrDisjunctOfSingletonsOrSingleton) {
        Set<CNF<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) cnfOrDisjunctOfSingletonsOrSingleton);
        if (independentConnectedConjuncts.size() == 1) {
            cnfOrDisjunctOfSingletonsOrSingleton = independentConnectedConjuncts.iterator().next();
            SplitClauses split = SplitClauses.split(cnfOrDisjunctOfSingletonsOrSingleton);
            CNF<?> cnf = split.getRest();
            CNF<?> independentCNF = split.getDisconnectedFromFirst();
            return countSolutions(cnf) *
                    (long) Math.pow(2, split.getFirst().getVariables().stream()
                            .filter(variable -> !cnf.getVariables().contains(variable)).count())
                    - countSolutions(independentCNF) *
                    (long) Math.pow(2, cnf.getVariables().stream()
                            .filter(variable -> !independentCNF.getVariables().contains(variable) && !split.getFirst().getVariables().contains(variable)).count());
        }
        return independentConnectedConjuncts.stream().mapToLong(SolutionCounter::countSolutions).reduce(1, (left, right) -> left * right);

    }
}
