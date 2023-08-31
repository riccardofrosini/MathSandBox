package ai.maths.sat3.algebraic;

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
}
