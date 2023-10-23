package ai.maths.sat3.probability;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.CNFOrDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.Singleton;

public class SplitClauses {

    private final DisjunctOfSingletonsOrSingleton first;
    private final Set<DisjunctOfSingletonsOrSingleton> rest;
    private final Set<DisjunctOfSingletonsOrSingleton> negatedClauses;
    private final Set<DisjunctOfSingletonsOrSingleton> positiveClauses;
    private final Set<DisjunctOfSingletonsOrSingleton> independentClauses;

    private SplitClauses(DisjunctOfSingletonsOrSingleton first, Set<DisjunctOfSingletonsOrSingleton> rest) {
        this.first = first;
        this.rest = rest;
        this.negatedClauses = buildNegatedClauses();
        this.positiveClauses = buildPositiveClauses();
        this.independentClauses = buildIndependentClauses();
    }

    protected static SplitClauses split(CNF<?> conjuncts) {
        DisjunctOfSingletonsOrSingleton anySubClause = conjuncts.getAnySubClause();
        return conjuncts.getSubClauses()
                .map(disjunct -> new SplitClauses(disjunct, conjuncts.getSubClauses().filter(t -> t != disjunct).collect(Collectors.toUnmodifiableSet())))
                .filter(splitClauses -> {
                    CNFOrDisjunctOfSingletonsOrSingleton<?> cnfOrDisjunctOfSingletonsOrSingleton = ClauseBuilder.buildCNF(splitClauses.getDisconnectedFromFirst());
                    if (cnfOrDisjunctOfSingletonsOrSingleton instanceof CNF) {
                        return ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) cnfOrDisjunctOfSingletonsOrSingleton).size() == 1;
                    }
                    return true;
                })
                .findAny()
                .orElse(new SplitClauses(anySubClause, conjuncts.getSubClauses().filter(t -> t != anySubClause).collect(Collectors.toUnmodifiableSet())));
    }

    private Set<DisjunctOfSingletonsOrSingleton> buildNegatedClauses() {
        return rest.stream()
                .filter(disjunct ->
                        disjunct.getSingletons().stream()
                                .anyMatch(singleton -> first.getSingletons().contains((Singleton) ClauseBuilder.buildNegation(singleton))))
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<DisjunctOfSingletonsOrSingleton> buildPositiveClauses() {
        return rest.stream()
                .filter(disjunct -> disjunct.getSingletons().stream()
                        .anyMatch(singleton -> first.getSingletons().contains(singleton)) &&
                        !negatedClauses.contains(disjunct))
                .map(disjunct -> disjunct.getSubClauses()
                        .filter(singleton -> !first.getSingletons().contains(singleton))
                        .collect(Collectors.toUnmodifiableSet()))
                .filter(singletons -> !singletons.isEmpty())
                .map(ClauseBuilder::buildDisjunctsOfSingletons)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<DisjunctOfSingletonsOrSingleton> buildIndependentClauses() {
        return rest.stream()
                .filter(disjunct -> disjunct.getSingletons().stream()
                        .noneMatch(singleton -> first.getSingletons().contains(singleton)) &&
                        !negatedClauses.contains(disjunct))
                .collect(Collectors.toUnmodifiableSet());
    }

    public Set<DisjunctOfSingletonsOrSingleton> getDisconnectedFromFirst() {
        return Stream.concat(positiveClauses.stream(), independentClauses.stream()).collect(Collectors.toUnmodifiableSet());
    }

    public DisjunctOfSingletonsOrSingleton getFirst() {
        return first;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getRest() {
        return rest;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getNegatedClauses() {
        return negatedClauses;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getPositiveClauses() {
        return positiveClauses;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getIndependentClauses() {
        return independentClauses;
    }
}
