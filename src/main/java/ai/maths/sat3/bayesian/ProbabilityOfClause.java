package ai.maths.sat3.bayesian;

import java.util.Objects;
import java.util.function.Function;

import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.model.BooleanConstant;
import ai.maths.sat3.model.ConjunctOfNonConstants;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctOfNonConstants;
import ai.maths.sat3.model.SingletonOrDisjunctsConjunctsOfNonConstant;
import ai.maths.sat3.model.SingletonVariable;

public abstract class ProbabilityOfClause<T extends SingletonOrDisjunctsConjunctsOfNonConstant> implements Function<ProbabilityClause, Double> {

    protected final T clause;

    protected ProbabilityOfClause(T clause) {
        this.clause = clause;
    }

    public T getClause() {
        return clause;
    }

    public abstract Formula convertToFormula();

    public static ProbabilityOfClause<?> probabilityOfIntersection(SingletonOrDisjunctsConjunctsOfNonConstant clause1, SingletonOrDisjunctsConjunctsOfNonConstant clause2) {
        return buildProbabilityOfClause(clause1.addConjunct(clause2));
    }

    public static ProbabilityOfClause<?> buildProbabilityOfClause(SingletonOrDisjunctsConjunctsOfNonConstant clause) {
        if (clause instanceof BooleanConstant) {
            return new ProbabilityOfBoolean((BooleanConstant) clause);
        }
        if (clause instanceof SingletonVariable) {
            return new ProbabilityOfSingleton((SingletonVariable) clause);
        }
        if (clause instanceof DisjunctOfNonConstants) {
            return new ProbabilityOfDisjuncts<>((DisjunctOfNonConstants<?>) clause).simplify();
        }
        if (clause instanceof ConjunctOfNonConstants) {
            if (clause instanceof ConjunctOfSingletons) {
                return new ProbabilityOfSingletonConjuncts((ConjunctOfSingletons) clause);
            }
            return buildProbabilityOfClause(((ConjunctOfNonConstants<?>) clause).makeAsDisjunct());
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
        ProbabilityOfClause<?> that = (ProbabilityOfClause<?>) o;
        return clause.equals(that.clause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clause);
    }
}
