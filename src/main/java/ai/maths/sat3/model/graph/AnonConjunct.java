package ai.maths.sat3.model.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AnonConjunct {

    private final Map<AnonConjunct, Double> anonConjuncts;

    protected AnonConjunct() {
        this.anonConjuncts = new HashMap<>();
    }

    protected void addAnonConjunct(AnonConjunct anonConjunct, Double probability) {
        anonConjuncts.put(anonConjunct, probability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnonConjunct that = (AnonConjunct) o;
        Map<Double[], Integer> probabilitiesMap1 = anonConjuncts.entrySet().stream()
                .map(anonConjunctDoubleEntry -> new Double[]{anonConjunctDoubleEntry.getValue(), anonConjunctDoubleEntry.getKey().anonConjuncts.get(this)})
                .collect(Collectors.groupingBy(doubles -> doubles)).entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, doublesListEntry -> doublesListEntry.getValue().size()));
        Map<Double[], Integer> probabilitiesMap2 = that.anonConjuncts.entrySet().stream()
                .map(anonConjunctDoubleEntry -> new Double[]{anonConjunctDoubleEntry.getValue(), anonConjunctDoubleEntry.getKey().anonConjuncts.get(that)})
                .collect(Collectors.groupingBy(doubles -> doubles)).entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, doublesListEntry -> doublesListEntry.getValue().size()));
        return probabilitiesMap1.entrySet().stream().allMatch(anonConjunctIntegerEntry1 ->
                probabilitiesMap2.entrySet().stream().anyMatch(anonConjunctIntegerEntry2 ->
                        Arrays.equals(anonConjunctIntegerEntry1.getKey(), anonConjunctIntegerEntry2.getKey()) &&
                                anonConjunctIntegerEntry1.getValue().equals(anonConjunctIntegerEntry2.getValue())
                )) && probabilitiesMap2.entrySet().stream().allMatch(anonConjunctIntegerEntry2 ->
                probabilitiesMap1.entrySet().stream().anyMatch(anonConjunctIntegerEntry1 ->
                        Arrays.equals(anonConjunctIntegerEntry2.getKey(), anonConjunctIntegerEntry1.getKey()) &&
                                anonConjunctIntegerEntry2.getValue().equals(anonConjunctIntegerEntry1.getValue())
                ));
    }
}
