package ai.maths.sat3.model;

import java.util.Set;
import java.util.stream.Collectors;

public interface Clause {

    Set<Variable> getAllVariables();

    DisjunctsConjunctsOfNonConstantAndSingletons simplify();

    DisjunctsConjunctsOfNonConstantAndSingletons addConjunct(DisjunctsConjunctsOfNonConstantAndSingletons clause);

    static boolean areThereClashingVariables(Set<SingletonVariable> juncts) {
        return juncts.stream()
                .collect(Collectors.groupingBy(SingletonVariable::getVariable))
                .values()
                .stream()
                .map(variableListEntry -> variableListEntry.stream()
                        .map(singletonClause -> singletonClause instanceof NegateVariable)
                        .collect(Collectors.toSet()))
                .anyMatch(booleans -> booleans.size() > 1);
    }

    static <T extends Clause> Set<SingletonVariable> getAllSingletons(Set<T> juncts) {
        return juncts.stream()
                .filter(t -> t instanceof SingletonVariable)
                .map(t -> (SingletonVariable) t)
                .collect(Collectors.toSet());
    }
}
