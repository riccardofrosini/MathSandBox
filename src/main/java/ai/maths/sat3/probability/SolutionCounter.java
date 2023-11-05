package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ConjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.NegVariable;
import ai.maths.sat3.model.sat3.Variable;

public class SolutionCounter {

    public static long countSolutionsOfCNF(CNF<?> clause) {
        if (clause == DisjunctOfSingletons.FALSE) {
            return 0;
        }
        if (clause == ConjunctOfSingletons.TRUE) {
            return 1;
        }
        if (clause instanceof Variable) {
            return 1;
        }
        if (clause instanceof NegVariable) {
            return (long) Math.pow(2, clause.getVariables().size()) - countSolutionsOfCNF(((NegVariable) clause).getNegatedClause());
        }
        if (clause instanceof DisjunctOfSingletons) {
            return (long) Math.pow(2, clause.getVariables().size()) - 1;
        }
        if (clause instanceof ConjunctOfSingletons) {
            return 1;
        }
        SimplifyCNF simplifiedCNF = SimplifyCNF.simplify(clause);
        if (simplifiedCNF.getSimplifiedCnf() != clause) {
            return countSolutionsOfCNF(simplifiedCNF.getSimplifiedCnf())
                    * (long) Math.pow(2, simplifiedCNF.getLostVariablesNotGiven().size());
        }
        return countSolutionsOfCNFNonSimplified(clause);

    }

    private static Long countSolutionsOfCNFNonSimplified(CNF<?> cnf) {
        Set<CNF<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) cnf);
        if (independentConnectedConjuncts.size() == 1) {
            SplitClauses split = SplitClauses.split(cnf);
            CNF<?> first = split.getRest();
            CNF<?> independentCNF = split.getDisconnectedFromFirst();
            return countSolutionsOfCNF(first) *
                    (long) Math.pow(2, split.getFirst().getVariables().stream()
                            .filter(variable -> !first.getVariables().contains(variable)).count())
                    - countSolutionsOfCNF(independentCNF) *
                    (long) Math.pow(2, first.getVariables().stream()
                            .filter(variable -> !independentCNF.getVariables().contains(variable) && !split.getFirst().getVariables().contains(variable)).count());
        }
        return independentConnectedConjuncts.stream().mapToLong(SolutionCounter::countSolutionsOfCNF).reduce(1, (left, right) -> left * right);

    }
}
