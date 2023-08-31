package ai.maths.sat3.lazyalgebraic;

import java.util.Objects;
import java.util.function.Function;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ConjunctClause;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctClause;
import ai.maths.sat3.model.SingletonClause;

public abstract class ProbabilityOfClauseLazy<T extends Clause> implements Function<ProbabilityClause, Double> {

    protected final T clause;

    protected ProbabilityOfClauseLazy(T clause) {
        this.clause = clause;
    }

    public static ProbabilityOfClauseLazy<?> probabilityOfIntersection(Clause clause1, Clause clause2) {
        return buildProbabilityOfClause(clause1.addConjunct(clause2));
    }

    public T getClause() {
        return clause;
    }

    public static ProbabilityOfClauseLazy<?> buildProbabilityOfClause(Clause clause) {
        if (clause instanceof SingletonClause) {
            return new ProbabilityOfSingletonLazy((SingletonClause<?>) clause);
        }
        if (clause instanceof DisjunctClause) {
            return new ProbabilityOfDisjunctsLazy<>((DisjunctClause<?>) clause);
        }
        if (clause instanceof ConjunctClause) {
            if (clause instanceof ConjunctOfSingletons) {
                return new ProbabilityOfSingletonConjunctsLazy((ConjunctOfSingletons) clause);
            }
            return buildProbabilityOfClause(((ConjunctClause<?>) clause).makeAsDisjunct());
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProbabilityOfClauseLazy<?> that = (ProbabilityOfClauseLazy<?>) o;
        return clause.equals(that.clause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clause);
    }
}
