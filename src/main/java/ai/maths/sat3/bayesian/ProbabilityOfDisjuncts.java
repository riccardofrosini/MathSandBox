package ai.maths.sat3.bayesian;

import java.util.Objects;
import java.util.Optional;

import ai.maths.sat3.algebraic.Formula;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.DisjunctClause;

public class ProbabilityOfDisjuncts<T extends Clause> extends ProbabilityOfClause<DisjunctClause<T>> {

    private final ProbabilityOfClause<?> probabilityOfClauses1;
    private final ProbabilityOfClause<?> probabilityOfClauses2;
    private final ProbabilityOfClause<?> intersection;

    protected ProbabilityOfDisjuncts(DisjunctClause<T> clause) {
        super(clause);
        Optional<T> any = clause.getDisjunctsStream().findAny();
        T disjunct = any.get();
        Clause otherDisjuncts = clause.getOtherDisjuncts(disjunct);
        probabilityOfClauses1 = ProbabilityOfClause.buildProbabilityOfClause(disjunct);
        probabilityOfClauses2 = ProbabilityOfClause.buildProbabilityOfClause(otherDisjuncts);
        intersection = ProbabilityOfClause.probabilityOfIntersection(disjunct, otherDisjuncts);
    }

    public ProbabilityOfClause<?> simplify() {
        if (probabilityOfClauses1.equals(intersection)) {
            return probabilityOfClauses2;
        }
        if (probabilityOfClauses2.equals(intersection)) {
            return probabilityOfClauses1;
        }
        return this;
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return probabilityOfClauses1.apply(probabilityClause) +
                probabilityOfClauses2.apply(probabilityClause) -
                intersection.apply(probabilityClause);
    }

    @Override
    public Formula convertToFormula() {
        return Formula.buildSumsForIntersection(probabilityOfClauses1.convertToFormula(),
                probabilityOfClauses2.convertToFormula(),
                intersection.convertToFormula());

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