package ai.maths.sat3.bayesian;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ConjunctClause;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctClause;
import ai.maths.sat3.model.DisjunctOfSingletons;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.NonBoolean;
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

    public double probabilityOfClause(Clause clause) {
        double probability = 0;
        if (probabilitiesOfClauses.containsKey(clause)) {
            probability = probabilitiesOfClauses.get(clause);
        } else if (clause instanceof NonBoolean) {
            probability = probabilityOfSingletonClause((NonBoolean) clause);
        } else if (clause instanceof DisjunctOfSingletons) {
            probability = probabilityOfSingletonDisjunction((DisjunctOfSingletons) clause);
        } else if (clause instanceof ConjunctOfSingletons) {
            probability = probabilityOfSingletonConjuncts((ConjunctOfSingletons) clause);
        } else if (clause instanceof DisjunctClause) {
            probability = probabilityOfDisjuncts((DisjunctClause<?>) clause);
        } else if (clause instanceof ConjunctClause) {
            probability = probabilityOfConjuncts((ConjunctClause<?>) clause);
        }
        probabilitiesOfClauses.put(clause, probability);
        return probability;
    }

    private <X extends Clause> double probabilityOfDisjuncts(DisjunctClause<X> disjunctClause) {
        Optional<X> disjunctOptional = disjunctClause.getDisjunctsStream().findAny();
        if (disjunctOptional.isEmpty()) {
            return 0d;
        }
        X disjunct = disjunctOptional.get();
        Clause otherDisjunct = disjunctClause.getOtherDisjuncts(disjunct);
        return probabilityOfClause(disjunct) + probabilityOfClause(otherDisjunct) - probabilityOfClause(disjunct.addConjunct(otherDisjunct));
    }

    private <X extends Clause> double probabilityOfConjuncts(ConjunctClause<X> conjunctClause) {
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

    public double probabilityOfSingletonClause(NonBoolean singletonClause) {
        return singletonClause instanceof NegateVariable ?
                1d - probabilities.get((singletonClause).getVariable()) :
                probabilities.get(singletonClause.getVariable());
    }
}
