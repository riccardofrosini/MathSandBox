package ai.maths.sat3.lazyalgebraic;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.SingletonClause;

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
        return "P(" + clause + ")";
    }
}
