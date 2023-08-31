package ai.maths.sat3.algebraic;

import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.ConjunctOfSingletons;

public class ProbabilityOfSingletonConjuncts extends ProbabilityOfClause<ConjunctOfSingletons> {

    Set<ProbabilityOfSingleton> probabilityOfSingletons;

    public ProbabilityOfSingletonConjuncts(ConjunctOfSingletons clause) {
        super(clause);
        probabilityOfSingletons = clause.getConjunctsStream().map(ProbabilityOfSingleton::new).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return clause.getConjunctsStream().mapToDouble(probabilityClause::probabilityOfClause).reduce(1, (left, right) -> left * right);
    }

    @Override
    public String toString() {
        return probabilityOfSingletons.stream().map(ProbabilityOfSingleton::toString).collect(Collectors.joining("*", "(", ")"));
    }
}
