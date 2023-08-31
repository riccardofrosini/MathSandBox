package ai.maths.sat3.algebraic;

import java.util.Objects;
import java.util.Optional;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.DisjunctClause;

public class ProbabilityOfDisjuncts<T extends Clause> extends ProbabilityOfClause<DisjunctClause<T>> {

    private ProbabilityOfClause<?> probabilityOfClauses1;
    private ProbabilityOfClause<?> probabilityOfClauses2;
    private ProbabilityOfClause<?> intersection;

    protected ProbabilityOfDisjuncts(DisjunctClause<T> clause) {
        super(clause);
        Optional<T> any = clause.getDisjunctsStream().findAny();
        T disjunct = any.get();
        Clause otherDisjuncts = clause.getOtherDisjuncts(disjunct);
        probabilityOfClauses1 = ProbabilityOfClause.buildProbabilityOfClause(disjunct);
        probabilityOfClauses2 = ProbabilityOfClause.buildProbabilityOfClause(otherDisjuncts);
        intersection = ProbabilityOfClause.probabilityOfIntersection(disjunct, otherDisjuncts);
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityOfClauses1.apply(probabilityClause) +
                probabilityOfClauses2.apply(probabilityClause) -
                intersection.apply(probabilityClause);
    }

    @Override
    public String toString() {
        return "(" + probabilityOfClauses1 + "+" + probabilityOfClauses2 + "-" + intersection + ")";
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
        ProbabilityOfDisjuncts<?> that = (ProbabilityOfDisjuncts<?>) o;
        return probabilityOfClauses1.equals(that.probabilityOfClauses1) && probabilityOfClauses2.equals(that.probabilityOfClauses2) && intersection.equals(that.intersection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), probabilityOfClauses1, probabilityOfClauses2, intersection);
    }
}
