package ai.maths.sat3.lazyalgebraic;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.SingletonClause;

public class ProbabilityOfSingletonConjunctsLazy extends ProbabilityOfClauseLazy<ConjunctOfSingletons> {

    private final Set<SingletonClause<?>> singletonClauses;

    protected ProbabilityOfSingletonConjunctsLazy(ConjunctOfSingletons clause) {
        super(clause);
        singletonClauses = clause.getConjunctsStream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return singletonClauses.stream()
                .mapToDouble(value -> ProbabilityOfClauseLazy.buildProbabilityOfClause(value).apply(probabilityClause))
                .reduce(1, (left, right) -> left * right);
    }

    @Override
    public String toString() {
        return singletonClauses.stream().map(singletonClause -> "P(" + singletonClause + ")").collect(Collectors.joining("*"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ProbabilityOfSingletonConjunctsLazy that = (ProbabilityOfSingletonConjunctsLazy) o;
        return singletonClauses.equals(that.singletonClauses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), singletonClauses);
    }
}
