package ai.maths.sat3.model.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.probability.Probability;

public class Graph {

    private Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> variableAnonVariableMap;

    protected Graph(Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> variableAnonVariableMap) {
        this.variableAnonVariableMap = variableAnonVariableMap;
    }

    public static Graph buildGraph(CNF<?> cnf) {
        Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> disjunctOfSingletonsOrSingletonAnonConjunctsMap = new HashMap<>();
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
                                    probabilityOfCNF / Probability.probabilityOfCNF(disjunctOfSingletonsOrSingleton1));
                            anonConjunct2.addAnonConjunct(anonConjunct1,
                                    probabilityOfCNF / Probability.probabilityOfCNF(disjunctOfSingletonsOrSingleton2));
                        }
                    });
                });
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
        Graph graph = (Graph) o;
        Map<AnonConjunct, Integer> anonConjunctCounterMap1 = variableAnonVariableMap.values().stream().collect(Collectors.groupingBy(anonConjunct -> anonConjunct))
                .entrySet().stream().collect(Collectors.toMap(Entry::getKey, anonConjunctListEntry -> anonConjunctListEntry.getValue().size()));
        Map<AnonConjunct, Integer> anonConjunctCounterMap2 = graph.variableAnonVariableMap.values().stream().collect(Collectors.groupingBy(anonConjunct -> anonConjunct))
                .entrySet().stream().collect(Collectors.toMap(Entry::getKey, anonConjunctListEntry -> anonConjunctListEntry.getValue().size()));
        return Objects.equals(anonConjunctCounterMap1, anonConjunctCounterMap2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variableAnonVariableMap);
    }
}