package ai.maths.sat3.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Clause {

    Set<VariableOrBoolean> getAllVariablesAndConstants();

    Clause simplify();

    Clause addConjunct(Clause clause);

    static boolean areThereClashingVariables(Set<SingletonClause<?>> juncts) {
        return areThereClashingVariables(juncts.stream());
    }

    static boolean areThereClashingVariables(Stream<SingletonClause<?>> juncts) {
        return juncts
                .collect(Collectors.groupingBy(SingletonClause::getVariableOrBoolean))
                .values()
                .stream()
                .map(variableListEntry -> variableListEntry.stream()
                        .map(singletonClause -> singletonClause instanceof NegateVariable)
                        .collect(Collectors.toSet()))
                .anyMatch(booleans -> booleans.size() > 1);
    }

    static <T extends Clause> Set<SingletonClause<?>> getAllSingletons(Set<T> juncts) {
        return getAllSingletons(juncts.stream());
    }

    static <T extends Clause> Set<SingletonClause<?>> getAllSingletons(Stream<T> juncts) {
        return juncts
                .filter(t -> t instanceof SingletonClause)
                .map(t -> (SingletonClause<?>) t)
                .collect(Collectors.toSet());
    }
}
