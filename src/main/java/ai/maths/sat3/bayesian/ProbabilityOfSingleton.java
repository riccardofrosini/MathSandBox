package ai.maths.sat3.bayesian;

import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import java.util.Map;

import ai.maths.sat3.algebraic.Constant;
import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.algebraic.SingletonVariable;
import ai.maths.sat3.algebraic.Sums;
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

    public Formula convertToFormula() {
        if (clause instanceof Variable) {
            return new SingletonVariable("P(" + clause + ")");
        }
        if (clause instanceof NegateVariable) {
            return new Sums(Map.of(new Constant(1), 1, new SingletonVariable("P(" + clause.getVariableOrBoolean() + ")"), -1));
        }
        if (clause == TRUE_CONSTANT) {
            return new Constant(1);
        }
        return new Constant(0);
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
