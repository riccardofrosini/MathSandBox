package ai.maths.sat3.probability;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.Variable;

public class ConnectedVariables {

    public static <T extends DisjunctOfSingletonsOrSingleton> Set<CNF<?>> getIndependentConnectedConjuncts(CNF<T> cnf) {
        Map<Variable, Set<T>> varToConjunct = new HashMap<>();
        cnf.getSubClauses().forEach(disjunct ->
                disjunct.getVariables().forEach(variable ->
                        varToConjunct.computeIfAbsent(variable, v -> new HashSet<>()).add(disjunct)));
        return getIndependentConnectedVariables(cnf).stream()
                .map(variables -> ClauseBuilder.buildCNF(variables.stream()
                        .flatMap(variable -> varToConjunct.get(variable).stream())
                        .collect(Collectors.toUnmodifiableSet())))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static <T extends DisjunctOfSingletonsOrSingleton> Set<Set<Variable>> getIndependentConnectedVariables(CNF<T> cnf) {
        Set<Set<Variable>> disconnectedSets = new HashSet<>();
        cnf.getSubClauses().forEach(disjunctOfSingletonsOrSingleton -> {
            Set<Variable> newVariables = disjunctOfSingletonsOrSingleton.getVariables();
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
