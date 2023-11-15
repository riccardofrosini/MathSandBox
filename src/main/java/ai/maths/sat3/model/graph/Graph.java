package ai.maths.sat3.model.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.probability.Probability;

public class Graph {

    private final Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> variableAnonVariableMap;
    private final int variables;

    protected Graph(Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> variableAnonVariableMap, int variables) {
        this.variableAnonVariableMap = variableAnonVariableMap;
        this.variables = variables;
    }

    public static Graph buildGraph(CNF<?> cnf) {
        Map<DisjunctOfSingletonsOrSingleton, AnonConjunct> disjunctOfSingletonsOrSingletonAnonConjunctsMap = new HashMap<>();
        if (cnf instanceof DisjunctOfSingletons) {
            AnonConjunct anonConjunct = new AnonConjunct(cnf.getVariables().size());
            disjunctOfSingletonsOrSingletonAnonConjunctsMap.put((DisjunctOfSingletons) cnf, anonConjunct);
        } else {
            cnf.getSubClauses().forEach(
                    disjunctOfSingletonsOrSingleton1 -> {
                        AnonConjunct anonConjunct1 = disjunctOfSingletonsOrSingletonAnonConjunctsMap.computeIfAbsent(disjunctOfSingletonsOrSingleton1,
                                k -> new AnonConjunct(k.getVariables().size()));
                        cnf.getSubClauses().forEach(disjunctOfSingletonsOrSingleton2 -> {
                            AnonConjunct anonConjunct2 = disjunctOfSingletonsOrSingletonAnonConjunctsMap.computeIfAbsent(disjunctOfSingletonsOrSingleton2,
                                    k -> new AnonConjunct(k.getVariables().size()));
                            if (anonConjunct1 != anonConjunct2) {
                                double probabilityOfCNF = Probability.probabilityOfCNF(ClauseBuilder.buildCNF(disjunctOfSingletonsOrSingleton1, disjunctOfSingletonsOrSingleton2));
                                long count = disjunctOfSingletonsOrSingleton1.getVariables().stream().filter(variable -> disjunctOfSingletonsOrSingleton2.getVariables().contains(variable))
                                        .count();
                                anonConjunct1.addAnonConjunct(anonConjunct2, (int) count,
                                        probabilityOfCNF / Probability.probabilityOfCNF(disjunctOfSingletonsOrSingleton1),
                                        probabilityOfCNF / Probability.probabilityOfCNF(disjunctOfSingletonsOrSingleton2));
                            }
                        });
                    });
        }
        return new Graph(disjunctOfSingletonsOrSingletonAnonConjunctsMap, cnf.getVariables().size());
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
        Map<AnonConjunct, List<AnonConjunct>> groupedBy1 = variableAnonVariableMap.values().stream().collect(Collectors.groupingBy(anonConjunct -> anonConjunct));
        Map<AnonConjunct, List<AnonConjunct>> groupedBy2 = that.variableAnonVariableMap.values().stream().collect(Collectors.groupingBy(anonConjunct -> anonConjunct));
        return variables == that.variables &&
                groupedBy1.entrySet().stream()
                        .allMatch(anonConjunctListEntry -> groupedBy2.containsKey(anonConjunctListEntry.getKey()) &&
                                groupedBy2.get(anonConjunctListEntry.getKey()).size() == anonConjunctListEntry.getValue().size()) &&
                groupedBy2.entrySet().stream().allMatch(
                        anonConjunctListEntry -> groupedBy1.containsKey(anonConjunctListEntry.getKey()) &&
                                groupedBy1.get(anonConjunctListEntry.getKey()).size() == anonConjunctListEntry.getValue().size());
    }

    @Override
    public int hashCode() {
        return variableAnonVariableMap.values().stream()
                .mapToInt(AnonConjunct::hashCode)
                .reduce(1 << variables, (left, right) -> left * right);
    }

    @Override
    public String toString() {
        return variableAnonVariableMap.entrySet().stream()
                .map(anonConjunct -> anonConjunct.getKey() + " " + anonConjunct.getValue())
                .collect(Collectors.joining("\n\t", "\t", ""));
    }
}