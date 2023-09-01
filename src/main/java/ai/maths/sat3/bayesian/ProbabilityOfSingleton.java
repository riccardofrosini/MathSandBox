package ai.maths.sat3.bayesian;

import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.SingletonClause;
import ai.maths.sat3.model.Variable;

public class ProbabilityOfSingleton extends ProbabilityOfClause<SingletonClause<?>> {

    protected ProbabilityOfSingleton(SingletonClause<?> clause) {
        super(clause);
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityClause.probabilityOfSingletonClause(clause);
    }

    @Override
    public Formula convertToFormula() {
        if (clause instanceof Variable) {
            return Formula.buildSingletonVariable("P(" + clause + ")");
        }
        if (clause instanceof NegateVariable) {
            return Formula.buildSumsForNegation(Formula.buildSingletonVariable("P(" + clause.getVariableOrBoolean() + ")"));
        }
        if (clause == TRUE_CONSTANT) {
            return Formula.buildConstant(1);
        }
        return Formula.buildConstant(0);
    }

    @Override
    public String toString() {
        if (clause instanceof Variable) {
            return "P(" + clause + ")";
        }
        if (clause instanceof NegateVariable) {
            return "(1-P(" + clause.getVariableOrBoolean() + "))";
        }
        if (clause == TRUE_CONSTANT) {
            return "1";
        }
        return "0";
    }
}
