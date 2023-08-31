package ai.maths.sat3.lazyalgebraic;

import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.SingletonClause;
import ai.maths.sat3.model.Variable;

public class ProbabilityOfSingletonLazy extends ProbabilityOfClauseLazy<SingletonClause<?>> {

    protected ProbabilityOfSingletonLazy(SingletonClause<?> clause) {
        super(clause);
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityClause.probabilityOfClause(clause);
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
