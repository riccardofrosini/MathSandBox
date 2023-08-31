package ai.maths.sat3.lazyalgebraic;

import java.util.Objects;
import java.util.Optional;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.DisjunctClause;

public class ProbabilityOfDisjunctsLazy<T extends Clause> extends ProbabilityOfClauseLazy<DisjunctClause<T>> {

    private final T disjunct1;
    private final Clause disjunct2;
    private final Clause intersection;

    protected ProbabilityOfDisjunctsLazy(DisjunctClause<T> clause) {
        super(clause);
        Optional<T> any = clause.getDisjunctsStream().findAny();
        disjunct1 = any.get();
        disjunct2 = clause.getOtherDisjuncts(disjunct1);
        intersection = disjunct1.addConjunct(disjunct2);
    }

    @Override
    public Double apply(ProbabilityClause probabilityClause) {
        return ProbabilityOfClauseLazy.buildProbabilityOfClause(disjunct1).apply(probabilityClause) +
                ProbabilityOfClauseLazy.buildProbabilityOfClause(disjunct2).apply(probabilityClause) -
                ProbabilityOfClauseLazy.buildProbabilityOfClause(intersection).apply(probabilityClause);
    }

    @Override
    public String toString() {
        return "(P(" + disjunct1 + ")+P(" + disjunct1 + ")-P(" + intersection + "))";
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
        ProbabilityOfDisjunctsLazy<?> that = (ProbabilityOfDisjunctsLazy<?>) o;
        return disjunct1.equals(that.disjunct1) && disjunct2.equals(that.disjunct2) && intersection.equals(that.intersection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), disjunct1, disjunct2, intersection);
    }
}
