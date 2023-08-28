package ai.maths.sat3.bayesian;


import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.SingletonClause;
import ai.maths.sat3.model.ThreeSatDisjunctClause;
import ai.maths.sat3.model.Variable;
import ai.maths.sat3.model.VariableOrBoolean;

public class ProbabilityClause {

    private Map<VariableOrBoolean, Double> probabilities;

    public ProbabilityClause(Clause clause) {
        Set<VariableOrBoolean> variableOrBooleanSet = clause.getAllVariablesAndConstants();
        probabilities = variableOrBooleanSet.stream()
                .collect(Collectors.toMap(variableOrBoolean -> variableOrBoolean,
                        ProbabilityClause::getDefaultProbability));
    }

    private static double getDefaultProbability(VariableOrBoolean variableOrBoolean) {
        return (variableOrBoolean instanceof Variable) ? 0.5 :
                (variableOrBoolean.equals(TRUE_CONSTANT) ? 1d : 0d);
    }

    public double probabilityOfDisjunction(ThreeSatDisjunctClause threeSatDisjunctClause) {
        if (threeSatDisjunctClause.simplify() == TRUE_CONSTANT) {
            return 1;
        }
        return threeSatDisjunctClause.getDisjuncts().stream()
                .mapToDouble(this::probabilityOfSingletonClause)
                .reduce(0, (probabilityTotal, probability) -> probability + (1 - probability) * probabilityTotal);
    }

    public double probabilityOfTwoConjunctSingletonClause(SingletonClause singletonClause1,
            SingletonClause singletonClause2) {
        if (singletonClause1.isEqualNegated(singletonClause2)) {
            return 0;
        }
        double probabilityClause1 = probabilityOfSingletonClause(singletonClause1);
        double probabilityClause2 = probabilityOfSingletonClause(singletonClause2);
        if (singletonClause1.equals(singletonClause2)) {
            return probabilityClause1;
        }
        return probabilityClause1 * probabilityClause2;
    }

    public double probabilityOfTwoDisjunctSingletonClause(SingletonClause singletonClause1,
            SingletonClause singletonClause2) {
        if (singletonClause1.isEqualNegated(singletonClause2)) {
            return 1;
        }
        double probabilityClause1 = probabilityOfSingletonClause(singletonClause1);
        double probabilityClause2 = probabilityOfSingletonClause(singletonClause2);
        if (singletonClause1.equals(singletonClause2)) {
            return probabilityClause1;
        }
        return probabilityClause1 + probabilityClause2 - probabilityClause1 * probabilityClause2;
    }

    public double probabilityOfSingletonClause(SingletonClause singletonClause) {
        return singletonClause instanceof NegateVariable ?
                1 - probabilities.get(singletonClause.getVariableOrBoolean()) :
                probabilities.get(singletonClause.getVariableOrBoolean());
    }
}
