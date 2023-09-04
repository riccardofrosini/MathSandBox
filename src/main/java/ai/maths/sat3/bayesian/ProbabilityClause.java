package ai.maths.sat3.bayesian;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.BooleanConstant;
import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ConjunctOfNonConstants;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctOfNonConstants;
import ai.maths.sat3.model.DisjunctOfSingletons;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.SingletonOrDisjunctsConjunctsOfNonConstant;
import ai.maths.sat3.model.SingletonVariable;
import ai.maths.sat3.model.SingletonVariableOrConjunctsOfNonConstants;
import ai.maths.sat3.model.SingletonVariableOrDisjunctsConjunctsOfNonConstant;
import ai.maths.sat3.model.SingletonVariableOrDisjunctsOfNonConstants;
import ai.maths.sat3.model.ThreeSatConjunctClause;
import ai.maths.sat3.model.ThreeSatDisjunctClause;
import ai.maths.sat3.model.Variable;


public class ProbabilityClause {

    private final Map<Variable, Double> probabilities;
    private final Map<Clause, Double> probabilitiesOfClauses;

    public ProbabilityClause(Clause clause) {
        Set<Variable> variableOrBooleanSet = clause.getAllVariables();
        probabilities = variableOrBooleanSet.stream()
                .collect(Collectors.toMap(variableOrBoolean -> variableOrBoolean, variableOrBoolean -> 0.5));
        probabilitiesOfClauses = new HashMap<>();
    }

    public double probabilityOfGiven(ThreeSatDisjunctClause threeSatDisjunctClause, ThreeSatDisjunctClause givenThreeSatDisjunctClause) {
        return probabilityOfClause(new ThreeSatConjunctClause(threeSatDisjunctClause, givenThreeSatDisjunctClause))
                / probabilityOfClause(givenThreeSatDisjunctClause);
    }

    public double probabilityOfClause(SingletonOrDisjunctsConjunctsOfNonConstant clause) {
        double probability = 0;
        if (probabilitiesOfClauses.containsKey(clause)) {
            probability = probabilitiesOfClauses.get(clause);
        } else if (clause instanceof BooleanConstant) {
            probability = probabilityOfBoolean((BooleanConstant) clause);
        } else if (clause instanceof SingletonVariable) {
            probability = probabilityOfSingletonClause((SingletonVariable) clause);
        } else if (clause instanceof DisjunctOfSingletons) {
            probability = probabilityOfSingletonDisjunction((DisjunctOfSingletons) clause);
        } else if (clause instanceof ConjunctOfSingletons) {
            probability = probabilityOfSingletonConjuncts((ConjunctOfSingletons) clause);
        } else if (clause instanceof DisjunctOfNonConstants) {
            probability = probabilityOfDisjuncts((DisjunctOfNonConstants<?>) clause);
        } else if (clause instanceof ConjunctOfNonConstants) {
            probability = probabilityOfConjuncts((ConjunctOfNonConstants<?>) clause);
        }
        probabilitiesOfClauses.put(clause, probability);
        return probability;
    }

    private <X extends SingletonVariableOrConjunctsOfNonConstants> double probabilityOfDisjuncts(DisjunctOfNonConstants<X> disjunctClause) {
        Optional<X> disjunctOptional = disjunctClause.getDisjunctsStream().findAny();
        if (disjunctOptional.isEmpty()) {
            return 0d;
        }
        X disjunct = disjunctOptional.get();
        SingletonVariableOrDisjunctsConjunctsOfNonConstant otherDisjunct = disjunctClause.getOtherDisjuncts(disjunct);
        return probabilityOfClause(disjunct) + probabilityOfClause(otherDisjunct) - probabilityOfClause(disjunct.addConjunct(otherDisjunct));
    }

    private <X extends SingletonVariableOrDisjunctsOfNonConstants> double probabilityOfConjuncts(ConjunctOfNonConstants<X> conjunctClause) {
        return probabilityOfClause(conjunctClause.makeAsDisjunct());
    }

    private double probabilityOfSingletonDisjunction(DisjunctOfSingletons disjunctOfSingletons) {
        return disjunctOfSingletons.getDisjunctsStream()
                .mapToDouble(this::probabilityOfSingletonClause)
                .reduce(0d, (probabilityTotal, probability) -> probability + (1 - probability) * probabilityTotal);
    }

    private double probabilityOfSingletonConjuncts(ConjunctOfSingletons conjunctOfSingletons) {
        return conjunctOfSingletons.getConjunctsStream()
                .mapToDouble(this::probabilityOfSingletonClause)
                .reduce(1d, (probabilityTotal, probability) -> probability * probabilityTotal);
    }

    public double probabilityOfSingletonClause(SingletonVariable singletonClause) {
        return singletonClause instanceof NegateVariable ?
                1d - probabilities.get((singletonClause).getVariable()) :
                probabilities.get(singletonClause.getVariable());
    }

    public double probabilityOfBoolean(BooleanConstant clause) {
        return clause == BooleanConstant.TRUE_CONSTANT ? 1 : 0;
    }
}
