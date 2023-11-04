package ai.maths.sat3.model.probability;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbabilityFormulaOfCNF {

    private final Map<Set<ProbabilityFormulaOfCNF>, Integer> sumsOfMultiplications;

    protected ProbabilityFormulaOfCNF() {
        this.sumsOfMultiplications = Map.of(Set.of(this), 1);
    }

    protected ProbabilityFormulaOfCNF(Map<Set<ProbabilityFormulaOfCNF>, Integer> sumsOfMultiplications) {
        this.sumsOfMultiplications = sumsOfMultiplications;
    }

    protected Stream<Entry<Set<ProbabilityFormulaOfCNF>, Integer>> getSumsOfMultiplications() {
        return sumsOfMultiplications.entrySet().stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProbabilityFormulaOfCNF that = (ProbabilityFormulaOfCNF) o;
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
                        .map(ProbabilityFormulaOfCNF::toString)
                        .collect(Collectors.joining(" * ")))
                .collect(Collectors.joining(" + ")) + ")";
    }
}
