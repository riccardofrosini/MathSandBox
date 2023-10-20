package ai.maths.sat3.sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.ThreeDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.ThreeSatConjuncts;
import ai.maths.sat3.model.Variable;

public class ConnectedVariables {

    public static Set<Clause<?>> getIndependentConnectedConjuncts(ThreeSatConjuncts threeSatConjuncts) {
        Map<Variable, Set<ThreeDisjunctOfSingletonsOrSingleton<?>>> varToConjunct = new HashMap<>();
        threeSatConjuncts.getSubClauses().forEach(disjunct ->
                disjunct.getVariables().forEach(variable ->
                        varToConjunct.computeIfAbsent(variable, v -> new HashSet<>()).add(disjunct)));
        return getIndependentConnectedVariables(threeSatConjuncts).stream()
                .map(variables -> ClauseBuilder.buildConjuncts(variables.stream()
                        .flatMap(variable -> varToConjunct.get(variable).stream()).toArray(Clause[]::new)))
                .collect(Collectors.toUnmodifiableSet());
    }

    public static Set<Set<Variable>> getIndependentConnectedVariables(ThreeSatConjuncts threeSatConjuncts) {
        Set<Set<Variable>> disconnectedSets = new HashSet<>();
        threeSatConjuncts.getSubClauses().forEach(threeDisjunctOfSingletonsOrSingleton -> {
            Set<Variable> newVariables = threeDisjunctOfSingletonsOrSingleton.getVariables();
            Set<Set<Variable>> toRemove = disconnectedSets.stream()
                    .filter(variables -> newVariables.stream().anyMatch(variables::contains))
                    .collect(Collectors.toUnmodifiableSet());
            disconnectedSets.removeAll(toRemove);
            Set<Variable> toAdd = Stream.concat(newVariables.stream(), toRemove.stream().flatMap(Collection::stream))
                    .collect(Collectors.toUnmodifiableSet());
            disconnectedSets.add(toAdd);
        });
        return disconnectedSets;
    }
}
