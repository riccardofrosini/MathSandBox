package ai.maths.sat3.probability;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.probability.ConjunctOfSingletonOrSingletonProbability;
import ai.maths.sat3.model.probability.DisjunctOfSingletonOrSingletonProbability;
import ai.maths.sat3.model.probability.ProbabilityOfCNF;
import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.ConjunctOfSingletons;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;

public class ProbabilityFormula {


    public static ProbabilityOfCNF getFormulaOfCNF(CNF<?> clause) {
        if (clause instanceof ConjunctOfSingletonsOrSingleton) {
            if (clause == ConjunctOfSingletons.TRUE) {
                return ConjunctOfSingletonOrSingletonProbability.TRUE;
            }
            return new ConjunctOfSingletonOrSingletonProbability((ConjunctOfSingletonsOrSingleton) clause);
        }
        if (clause instanceof DisjunctOfSingletonsOrSingleton) {
            if (clause == DisjunctOfSingletons.FALSE) {
                return DisjunctOfSingletonOrSingletonProbability.FALSE;
            }
            return new DisjunctOfSingletonOrSingletonProbability((DisjunctOfSingletonsOrSingleton) clause);
        }
        SimplifyCNF simplifiedCNF = SimplifyCNF.simplify(clause);
        return new ProbabilityOfCNF(Map.of(Set.of(getFormulaOfCNFSimplified(simplifiedCNF.getSimplifiedCnf()), getFormulaOfCNF(simplifiedCNF.getGivenSingletons())), 1));
    }

    private static ProbabilityOfCNF getFormulaOfCNFSimplified(CNF<?> simplifiedCNF) {
        Set<CNF<?>> independentConnectedConjuncts = ConnectedVariables.getIndependentConnectedConjuncts(simplifiedCNF);
        if (independentConnectedConjuncts.size() == 1) {
            SplitClauses split = SplitClauses.split(simplifiedCNF);
            return new ProbabilityOfCNF(Map.of(
                    Set.of(getFormulaOfCNF(split.getRest())), 1,
                    Set.of(getFormulaOfCNF(split.getRest()), getFormulaOfCNF(ClauseBuilder.buildNegationOfDisjunctOfSingletons(split.getFirst()))), -1));
        }
        return new ProbabilityOfCNF(Map.of(independentConnectedConjuncts.stream().map(ProbabilityFormula::getFormulaOfCNF).collect(Collectors.toUnmodifiableSet()), 1));
    }


}
