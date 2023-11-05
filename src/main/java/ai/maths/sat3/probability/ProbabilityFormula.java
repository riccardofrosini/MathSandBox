package ai.maths.sat3.probability;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.probability.ProbabilityFormulaBuilder;
import ai.maths.sat3.model.probability.ProbabilityFormulaOfCNF;
import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;

public class ProbabilityFormula {

    public static ProbabilityFormulaOfCNF getFormulaOfCNF(CNF<?> clause) {
        if (clause instanceof ConjunctOfSingletonsOrSingleton) {
            return ProbabilityFormulaBuilder.buildProbabilityFormulaOfConjunctOfSingletonOrSingleton((ConjunctOfSingletonsOrSingleton) clause);
        }
        if (clause instanceof DisjunctOfSingletonsOrSingleton) {
            return ProbabilityFormulaBuilder.buildProbabilityFormulaOfDisjunctOfSingletonOrSingleton((DisjunctOfSingletonsOrSingleton) clause);
        }
        SimplifyCNF simplifiedCNF = SimplifyCNF.simplify(clause);
        if (simplifiedCNF.getSimplifiedCnf() != clause) {
            return ProbabilityFormulaBuilder.buildProductOfProbability(Set.of(getFormulaOfCNF(simplifiedCNF.getSimplifiedCnf()),
                    getFormulaOfCNF(simplifiedCNF.getGivenSingletons())));
        }
        return getFormulaOfCNFNonSimplified(clause);
    }

    private static ProbabilityFormulaOfCNF getFormulaOfCNFNonSimplified(CNF<?> cnf) {
        Set<CNF<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts(cnf);
        if (independentConnectedConjuncts.size() == 1) {
            SplitClauses split = SplitClauses.split(cnf);
            return ProbabilityFormulaBuilder.buildSumOfProbability(Map.of(
                    Set.of(getFormulaOfCNF(split.getRest())), 1L,
                    Set.of(getFormulaOfCNF(split.getDisconnectedFromFirst()), getFormulaOfCNF(ClauseBuilder.buildNegationOfDisjunctOfSingletons(split.getFirst()))), -1L));
        }
        return ProbabilityFormulaBuilder.buildProductOfProbability(independentConnectedConjuncts.stream().map(ProbabilityFormula::getFormulaOfCNF).collect(Collectors.toUnmodifiableSet()));
    }


}
