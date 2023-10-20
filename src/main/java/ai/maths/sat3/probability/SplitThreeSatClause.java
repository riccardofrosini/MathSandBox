package ai.maths.sat3.probability;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.Singleton;
import ai.maths.sat3.model.ThreeDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.ThreeSatConjuncts;

public class SplitThreeSatClause {

    private ThreeDisjunctOfSingletonsOrSingleton<?> first;
    private ThreeDisjunctOfSingletonsOrSingleton<?>[] rest;

    private SplitThreeSatClause(ThreeDisjunctOfSingletonsOrSingleton<?> first, ThreeDisjunctOfSingletonsOrSingleton<?>[] rest) {
        this.first = first;
        this.rest = rest;
    }

    public static SplitThreeSatClause split(ThreeSatConjuncts conjuncts) {
        ThreeDisjunctOfSingletonsOrSingleton<?> anySubClause = conjuncts.getAnySubClause();
        ThreeDisjunctOfSingletonsOrSingleton<?>[] rest = conjuncts.getSubClauses().filter(t -> t != anySubClause).toArray(ThreeDisjunctOfSingletonsOrSingleton<?>[]::new);
        return new SplitThreeSatClause(anySubClause, rest);
    }


    public Clause<?> makeRestIndependentToFirst() {
        Set<ThreeDisjunctOfSingletonsOrSingleton<?>> disjunctsWithoutNegativeVars = new HashSet<>(Arrays.asList(rest)).stream()
                .filter(disjunct ->
                        disjunct.getSingletons().stream()
                                .noneMatch(singleton -> first.getSingletons().contains((Singleton) ClauseBuilder.buildNegation(singleton))))
                .collect(Collectors.toSet());
        Set<ThreeDisjunctOfSingletonsOrSingleton<?>> toReplace = disjunctsWithoutNegativeVars.stream()
                .filter(disjunct -> disjunct.getSingletons().stream()
                        .anyMatch(singleton -> first.getSingletons().contains(singleton)))
                .collect(Collectors.toSet());
        disjunctsWithoutNegativeVars.removeAll(toReplace);
        Set<ThreeDisjunctOfSingletonsOrSingleton<?>> newClauses = toReplace.stream()
                .map(disjunct -> disjunct.getSubClauses()
                        .filter(singleton -> !first.getSingletons().contains(singleton)).toArray(Singleton[]::new))
                .filter(singletons -> singletons.length != 0)
                .map(singletons -> (ThreeDisjunctOfSingletonsOrSingleton<?>) ClauseBuilder.buildDisjuncts(singletons))
                .collect(Collectors.toSet());
        disjunctsWithoutNegativeVars.addAll(newClauses);
        return ClauseBuilder.buildConjuncts(disjunctsWithoutNegativeVars.toArray(new Clause<?>[0]));
    }

    public ThreeDisjunctOfSingletonsOrSingleton<?> getFirst() {
        return first;
    }

    public ThreeDisjunctOfSingletonsOrSingleton<?>[] getRest() {
        return rest;
    }
}
