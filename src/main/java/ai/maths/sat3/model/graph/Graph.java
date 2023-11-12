package ai.maths.sat3.model.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.probability.Probability;

public class Graph {

    private final Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> variableAnonVariableMap;

    protected Graph(Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> variableAnonVariableMap) {
        this.variableAnonVariableMap = variableAnonVariableMap;
    }

    public static Graph buildGraph(CNF<?> cnf) {
        Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> disjunctOfSingletonsOrSingletonAnonConjunctsMap = new HashMap<>();
        if (cnf instanceof DisjunctOfSingletons) {
            AnonConjunct anonConjunct = new AnonConjunct();
            disjunctOfSingletonsOrSingletonAnonConjunctsMap.put((DisjunctOfSingletons) cnf, anonConjunct);
        } else {
            cnf.getSubClauses().forEach(
                    disjunctOfSingletonsOrSingleton1 -> {
                        AnonConjunct anonConjunct1 = disjunctOfSingletonsOrSingletonAnonConjunctsMap.computeIfAbsent(disjunctOfSingletonsOrSingleton1,
                                k -> new AnonConjunct());
                        cnf.getSubClauses().forEach(disjunctOfSingletonsOrSingleton2 -> {
                            AnonConjunct anonConjunct2 = disjunctOfSingletonsOrSingletonAnonConjunctsMap.computeIfAbsent(disjunctOfSingletonsOrSingleton2,
                                    k -> new AnonConjunct());
                            if (anonConjunct1 != anonConjunct2) {
                                double probabilityOfCNF = Probability.probabilityOfCNF(ClauseBuilder.buildCNF(disjunctOfSingletonsOrSingleton1, disjunctOfSingletonsOrSingleton2));
                                anonConjunct1.addAnonConjunct(anonConjunct2,
                                        probabilityOfCNF / Probability.probabilityOfCNF(disjunctOfSingletonsOrSingleton1),
                                        probabilityOfCNF / Probability.probabilityOfCNF(disjunctOfSingletonsOrSingleton2));
                            }
                        });
                    });
        }
        return new Graph(disjunctOfSingletonsOrSingletonAnonConjunctsMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Graph that = (Graph) o;

        return variableAnonVariableMap.values().stream().allMatch(anonConjunct ->
                that.variableAnonVariableMap.values().stream().anyMatch(anonConjunct::equals)) &&
                that.variableAnonVariableMap.values().stream().allMatch(anonConjunct ->
                        variableAnonVariableMap.values().stream().anyMatch(anonConjunct::equals));
    }

    @Override
    public int hashCode() {
        return variableAnonVariableMap.values().stream()
                .mapToInt(AnonConjunct::hashCode)
                .reduce(1, (left, right) -> left * right);
    }

    @Override
    public String toString() {
        return variableAnonVariableMap.entrySet().stream()
                .map(anonConjunct -> anonConjunct.getKey() + " " + anonConjunct.getValue())
                .collect(Collectors.joining("\n\t", "\t", ""));
    }
}