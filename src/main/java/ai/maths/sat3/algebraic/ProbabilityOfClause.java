package ai.maths.sat3.algebraic;

import java.util.function.Function;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ConjunctClause;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctClause;
import ai.maths.sat3.model.SingletonClause;

public abstract class ProbabilityOfClause<T extends Clause> implements Function<ProbabilityClause, Double> {

    protected final T clause;

    protected ProbabilityOfClause(T clause) {
        this.clause = clause;
    }

    public static ProbabilityOfClause<?> probabilityOfIntersection(Clause clause1, Clause clause2) {
        return buildProbabilityOfClause(clause1.addConjunct(clause2));
    }

    public static ProbabilityOfClause<?> buildProbabilityOfClause(Clause clause) {
        if (clause instanceof SingletonClause) {
            return new ProbabilityOfSingleton((SingletonClause<?>) clause);
        }
        if (clause instanceof DisjunctClause) {
            if (((DisjunctClause<?>) clause).size() == 1) {
                return ProbabilityOfClause.buildProbabilityOfClause(((DisjunctClause<?>) clause).getDisjunctsStream().findAny().get());
            }
            return new ProbabilityOfDisjuncts<>((DisjunctClause<?>) clause);
        }
        if (clause instanceof ConjunctClause) {
            if (((ConjunctClause<?>) clause).size() == 1) {
                return ProbabilityOfClause.buildProbabilityOfClause(((ConjunctClause<?>) clause).getConjunctsStream().findAny().get());
            }
            if (clause instanceof ConjunctOfSingletons) {
                return new ProbabilityOfSingletonConjuncts((ConjunctOfSingletons) clause);
            }
            return buildProbabilityOfClause(((ConjunctClause<?>) clause).makeAsDisjunct());
        }
        return null;
    }
}
