package ai.maths.sat3.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnonConjunct {

    private final Map<InOutProbabilities, List<AnonConjunct>> anonConjuncts;

    protected AnonConjunct() {
        this.anonConjuncts = new HashMap<>();
    }

    protected void addAnonConjunct(AnonConjunct anonConjunct, double out, double in) {
        anonConjuncts.computeIfAbsent(new InOutProbabilities(out, in), inOutProbabilities -> new ArrayList<>()).add(anonConjunct);
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
        return anonConjuncts.entrySet().stream().allMatch(inOutProbabilitiesListEntry1 ->
                that.anonConjuncts.entrySet().stream().anyMatch(inOutProbabilitiesListEntry2 ->
                        inOutProbabilitiesListEntry1.getKey().equals(inOutProbabilitiesListEntry2.getKey()) &&
                                inOutProbabilitiesListEntry1.getValue().size() == inOutProbabilitiesListEntry2.getValue().size())) &&
                that.anonConjuncts.entrySet().stream().allMatch(inOutProbabilitiesListEntry1 ->
                        anonConjuncts.entrySet().stream().anyMatch(inOutProbabilitiesListEntry2 ->
                                inOutProbabilitiesListEntry1.getKey().equals(inOutProbabilitiesListEntry2.getKey()) &&
                                        inOutProbabilitiesListEntry1.getValue().size() == inOutProbabilitiesListEntry2.getValue().size()));
    }

    @Override
    public int hashCode() {
        return anonConjuncts.entrySet().stream()
                .mapToInt(value -> value.getKey().hashCode() * value.getValue().size())
                .reduce(1, (left, right) -> left * right);
    }

    @Override
    public String toString() {
        return hashCode() + " " + anonConjuncts.entrySet().stream()
                .map(inOutProbabilitiesListEntry -> inOutProbabilitiesListEntry.getValue().stream()
                        .map(anonConjunct -> anonConjunct.hashCode() + " " + inOutProbabilitiesListEntry.getKey().out + " " + inOutProbabilitiesListEntry.getKey().in)
                        .collect(Collectors.joining("\n|\t\t", "\n|\t\t", "")))
                .collect(Collectors.joining(""));
    }


    public static class InOutProbabilities {

        private final double out;
        private final double in;

        public InOutProbabilities(double out, double in) {
            this.out = out;
            this.in = in;
        }

        public double getOut() {
            return out;
        }

        public double getIn() {
            return in;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InOutProbabilities that = (InOutProbabilities) o;
            return that.out == out && that.in == in;
        }

        @Override
        public int hashCode() {
            return (int) Math.round(out * 8191 - in * 127);
        }
    }
}
