package ai.maths.sat3.model;

import java.util.Set;
import java.util.stream.Collectors;

public interface Clause {

    Set<Variable> getAllVariables();

    Clause simplify();

    Clause addConjunct(Clause clause);

    static boolean areThereClashingVariables(Set<NonBoolean> juncts) {
        return juncts.stream()
                .collect(Collectors.groupingBy(NonBoolean::getVariable))
                .values()
                .stream()
                .map(variableListEntry -> variableListEntry.stream()
                        .map(singletonClause -> singletonClause instanceof NegateVariable)
                        .collect(Collectors.toSet()))
                .anyMatch(booleans -> booleans.size() > 1);
    }

    static <T extends Clause> Set<NonBoolean> getAllSingletons(Set<T> juncts) {
        return juncts.stream()
                .filter(t -> t instanceof NonBoolean)
                .map(t -> (NonBoolean) t)
                .collect(Collectors.toSet());
    }
}
