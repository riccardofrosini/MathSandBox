package ai.maths.sat3.probability;

import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
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
        this.disconnectedFromFirst = makeRestIndependentToFirst();
    }

    public static SplitClauses split(CNF<?> conjuncts) {
        DisjunctOfSingletonsOrSingleton anySubClause = conjuncts.getAnySubClause(); //TODO choose best sub-clause
        Set<DisjunctOfSingletonsOrSingleton> rest = conjuncts.getSubClauses().filter(t -> t != anySubClause).collect(Collectors.toUnmodifiableSet());
        return new SplitClauses(anySubClause, rest);
    }

    private Set<DisjunctOfSingletonsOrSingleton> makeRestIndependentToFirst() {
        Set<DisjunctOfSingletonsOrSingleton> disjunctsWithoutNegativeVars = rest.stream()
                .filter(disjunct ->
                        disjunct.getSingletons().stream()
                                .noneMatch(singleton -> first.getSingletons().contains((Singleton) ClauseBuilder.buildNegation(singleton))))
                .collect(Collectors.toSet());
        Set<DisjunctOfSingletonsOrSingleton> toReplace = disjunctsWithoutNegativeVars.stream()
                .filter(disjunct -> disjunct.getSingletons().stream()
                        .anyMatch(singleton -> first.getSingletons().contains(singleton)))
                .collect(Collectors.toSet());
        disjunctsWithoutNegativeVars.removeAll(toReplace);
        Set<DisjunctOfSingletonsOrSingleton> newClauses = toReplace.stream()
                .map(disjunct -> disjunct.getSubClauses()
                        .filter(singleton -> !first.getSingletons().contains(singleton))
                        .collect(Collectors.toSet()))
                .filter(singletons -> singletons.size() != 0)
                .map(ClauseBuilder::buildDisjunctsOfSingletons)
                .collect(Collectors.toSet());
        disjunctsWithoutNegativeVars.addAll(newClauses);
        return disjunctsWithoutNegativeVars;
    }

    public DisjunctOfSingletonsOrSingleton getFirst() {
        return first;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getRest() {
        return rest;
    }

    public Set<DisjunctOfSingletonsOrSingleton> getDisconnectedFromFirst() {
        return disconnectedFromFirst;
    }
}
