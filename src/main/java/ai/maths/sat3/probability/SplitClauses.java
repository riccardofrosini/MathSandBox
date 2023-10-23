package ai.maths.sat3.probability;

import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.CNFOrDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.Singleton;

public class SplitClauses {

    private final DisjunctOfSingletonsOrSingleton first;
    private final Set<DisjunctOfSingletonsOrSingleton> rest;
    private final Set<DisjunctOfSingletonsOrSingleton> disconnectedFromFirst;

    private SplitClauses(DisjunctOfSingletonsOrSingleton first, Set<DisjunctOfSingletonsOrSingleton> rest) {
        this.first = first;
        this.rest = rest;
        this.disconnectedFromFirst = buildDisconnectedFromFirst();
    }

    protected static SplitClauses split(CNF<?> conjuncts) {
        DisjunctOfSingletonsOrSingleton anySubClause = conjuncts.getAnySubClause();
        return conjuncts.getSubClauses()
                .map(disjunct -> new SplitClauses(disjunct, conjuncts.getSubClauses().filter(t -> t != disjunct).collect(Collectors.toUnmodifiableSet())))
                .filter(splitClauses -> {
                    CNFOrDisjunctOfSingletonsOrSingleton<?> cnfOrDisjunctOfSingletonsOrSingleton = ClauseBuilder.buildCNF(splitClauses.disconnectedFromFirst);
                    if (cnfOrDisjunctOfSingletonsOrSingleton instanceof CNF) {
                        return ConnectedVariables.getIndependentConnectedConjuncts((CNF<?>) cnfOrDisjunctOfSingletonsOrSingleton).size() == 1;
                    }
                    return true;
                })
                .findAny()
                .orElse(new SplitClauses(anySubClause, conjuncts.getSubClauses().filter(t -> t != anySubClause).collect(Collectors.toUnmodifiableSet())));
    }

    private Set<DisjunctOfSingletonsOrSingleton> buildDisconnectedFromFirst() {
        return rest.stream()
                .filter(disjunct ->
                        disjunct.getSingletons().stream()
                                .noneMatch(singleton -> first.getSingletons().contains((Singleton) ClauseBuilder.buildNegation(singleton))))
                .map(disjunct ->
                        disjunct.getSingletons().stream().anyMatch(singleton -> first.getSingletons().contains(singleton)) ?
                                ClauseBuilder.buildDisjunctsOfSingletons(disjunct.getSingletons().stream().filter(singleton -> !first.getSingletons().contains(singleton)).collect(Collectors.toSet()))
                                : disjunct)

                .collect(Collectors.toSet());
    }

    public Set<DisjunctOfSingletonsOrSingleton> getDisconnectedFromFirst() {
        return disconnectedFromFirst;
    }

    public DisjunctOfSingletonsOrSingleton getFirst() {
        return first;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getRest() {
        return rest;
    }
}
