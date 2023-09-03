package ai.maths.sat3.bayesian;

import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.algebraic.NotAProduct;
import ai.maths.sat3.model.SingletonVariable;
import ai.maths.sat3.model.Variable;

public class ProbabilityOfSingleton extends ProbabilityOfClause<SingletonVariable> {

    protected ProbabilityOfSingleton(SingletonVariable clause) {
        super(clause);
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityClause.probabilityOfSingletonClause(clause);
    }

    @Override
    public NotAProduct convertToFormula() {
        if (clause instanceof Variable) {
            return Formula.buildSingletonVariable("P(" + clause + ")");
        }
        return Formula.buildSumsForNegation(Formula.buildSingletonVariable("P(" + clause.getVariable() + ")"));
    }

    @Override
    public String toString() {
        if (clause instanceof Variable) {
            return "P(" + clause + ")";
        }
        return "(1-P(" + clause.getVariable() + "))";
    }
}
