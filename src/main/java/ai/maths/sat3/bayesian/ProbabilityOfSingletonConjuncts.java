package ai.maths.sat3.bayesian;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.algebraic.Products;
import ai.maths.sat3.model.ConjunctOfSingletons;

public class ProbabilityOfSingletonConjuncts extends ProbabilityOfClause<ConjunctOfSingletons> {

    private final Set<ProbabilityOfSingleton> probabilityOfSingletons;

    protected ProbabilityOfSingletonConjuncts(ConjunctOfSingletons clause) {
        super(clause);
        probabilityOfSingletons = clause.getConjunctsStream().map(ProbabilityOfSingleton::new).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityOfSingletons.stream().mapToDouble(value -> value.apply(probabilityClause)).reduce(1, (left, right) -> left * right);
    }

    @Override
    public Formula convertToFormula() {
        return new Products(probabilityOfSingletons.stream().map(ProbabilityOfSingleton::convertToFormula).collect(Collectors.toUnmodifiableSet()));
    }

    @Override
    public String toString() {
        return probabilityOfSingletons.stream().map(ProbabilityOfSingleton::toString).collect(Collectors.joining("*"));
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
        ProbabilityOfSingletonConjuncts that = (ProbabilityOfSingletonConjuncts) o;
        return probabilityOfSingletons.equals(that.probabilityOfSingletons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), probabilityOfSingletons);
    }
}
