package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ConjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.NegVariable;
import ai.maths.sat3.model.sat3.Variable;

public class Probability {

    public static double probabilityOfCNF(CNF<?> clause) {
        if (clause == DisjunctOfSingletons.FALSE) {
            return 0d;
        }
        if (clause == ConjunctOfSingletons.TRUE) {
            return 1d;
        }
        if (clause instanceof Variable) {
            return 1d / 2;
        }
        if (clause instanceof NegVariable) {
            return 1d - probabilityOfCNF(((NegVariable) clause).getNegatedClause());
        }
        if (clause instanceof DisjunctOfSingletons) {
            return 1d - 1d / Math.pow(2, clause.getVariables().size());
        }
        if (clause instanceof ConjunctOfSingletons) {
            return 1d / Math.pow(2, clause.getVariables().size());
        }
        SimplifyCNF simplifiedCNF = SimplifyCNF.simplify(clause);
        if (simplifiedCNF.getSimplifiedCnf() != clause) {
            return probabilityOfCNF(simplifiedCNF.getSimplifiedCnf())
                    / Math.pow(2, simplifiedCNF.getGivenVariables().size());
        }
        return probabilityOfCNFSimplified(clause);
    }

    private static double probabilityOfCNFSimplified(CNF<?> cnf) {
        Set<CNF<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts(cnf);
        if (independentConnectedConjuncts.size() == 1) {
            SplitClauses split = SplitClauses.split(cnf);
            return (probabilityOfCNF(split.getRest()) -
                    probabilityOfCNF(split.getDisconnectedFromFirst()) / Math.pow(2, split.getFirst().getVariables().size()));
        }
        return independentConnectedConjuncts.stream().mapToDouble(Probability::probabilityOfCNF).reduce(1, (left, right) -> left * right);
    }
}
