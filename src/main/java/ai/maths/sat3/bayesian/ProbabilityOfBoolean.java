package ai.maths.sat3.bayesian;

import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.model.BooleanConstant;

public class ProbabilityOfBoolean extends ProbabilityOfClause<BooleanConstant> {

    protected ProbabilityOfBoolean(BooleanConstant clause) {
        super(clause);
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityClause.probabilityOfBoolean(clause);
    }

    @Override
    public Formula convertToFormula() {
        if (clause == TRUE_CONSTANT) {
            return Formula.buildConstant(1);
        }
        return Formula.buildConstant(0);
    }

    @Override
    public String toString() {
        if (clause == TRUE_CONSTANT) {
            return "1";
        }
        return "0";
    }

}
