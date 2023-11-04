package ai.maths.sat3.model.probability;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProbabilityOfCNF {

    private final Map<Set<ProbabilityOfCNF>, Integer> sumsOfMultiplications;

    public ProbabilityOfCNF() {
        this.sumsOfMultiplications = Map.of(Set.of(this), 1);
    }

    public ProbabilityOfCNF(Map<Set<ProbabilityOfCNF>, Integer> sumsOfMultiplications) {
        sumsOfMultiplications = new HashMap<>(sumsOfMultiplications);
        Map<Set<ProbabilityOfCNF>, Integer> sumsOfMultiplicationsToSimplify = sumsOfMultiplications.entrySet().stream()
                .filter(probabilityOfCNFEntry -> probabilityOfCNFEntry.getKey().stream()
                        .anyMatch(probabilityOfCNF -> !(probabilityOfCNF instanceof ConjunctOfSingletonOrSingletonProbability)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        sumsOfMultiplications.keySet().removeAll(sumsOfMultiplicationsToSimplify.keySet());
        sumsOfMultiplications.putAll(sumsOfMultiplicationsToSimplify.entrySet().stream()
                .flatMap(probabilityOfCNFEntry -> probabilityOfCNFEntry.getKey().stream()
                        .map(probabilityOfCNF -> probabilityOfCNF.sumsOfMultiplications)
                        .reduce(this::multiply).get().entrySet().stream()
                        .peek(setIntegerEntry -> setIntegerEntry.setValue(setIntegerEntry.getValue() * probabilityOfCNFEntry.getValue())))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Integer::sum)));
        this.sumsOfMultiplications = sumsOfMultiplications;
    }

    private Map<Set<ProbabilityOfCNF>, Integer> multiply(Map<Set<ProbabilityOfCNF>, Integer> probabilityOfCNF1, Map<Set<ProbabilityOfCNF>, Integer> probabilityOfCNF2) {
        return probabilityOfCNF1.entrySet().stream()
                .flatMap(setIntegerEntry1 ->
                        probabilityOfCNF2.entrySet().stream()
                                .flatMap(setIntegerEntry2 -> {
                                    Set<ProbabilityOfCNF> probabilityOfCNFS = new HashSet<>(setIntegerEntry1.getKey());
                                    probabilityOfCNFS.addAll(setIntegerEntry2.getKey());
                                    if (probabilityOfCNFS.size() == 1) {
                                        return Map.of(probabilityOfCNFS, setIntegerEntry1.getValue() * setIntegerEntry2.getValue()).entrySet().stream()
                                                .filter(setIntegerEntry -> setIntegerEntry.getValue() != 0);
                                    }
                                    return Map.of(probabilityOfCNFS.stream()
                                                    .filter(probabilityOfCNF -> !probabilityOfCNF.equals(ConjunctOfSingletonOrSingletonProbability.TRUE))
                                                    .collect(Collectors.toUnmodifiableSet()), setIntegerEntry1.getValue() * setIntegerEntry2.getValue()).entrySet().stream()
                                            .filter(setIntegerEntry -> setIntegerEntry.getValue() != 0);
                                }))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Integer::sum));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProbabilityOfCNF that = (ProbabilityOfCNF) o;
        return sumsOfMultiplications.equals(that.sumsOfMultiplications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sumsOfMultiplications);
    }

    @Override
    public String toString() {
        return "(" + sumsOfMultiplications.entrySet().stream()
                .map(setIntegerEntry -> (setIntegerEntry.getValue() == 1 || setIntegerEntry.getValue() == -1 ?
                        (setIntegerEntry.getValue() == -1 ? "- " : "") : setIntegerEntry.getValue() + " ")
                        + setIntegerEntry.getKey().stream()
                        .map(ProbabilityOfCNF::toString)
                        .collect(Collectors.joining(" * ")))
                .collect(Collectors.joining(" + ")) + ")";
    }
}
