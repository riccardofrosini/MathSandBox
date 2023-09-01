package ai.maths.sat3.algebraic;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Sums extends Formula {

    private final Map<Formula, Integer> addends;

    public Sums(Map<Formula, Integer> addends) {
        this.addends = addends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sums sums = (Sums) o;
        return addends.equals(sums.addends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addends);
    }

    @Override
    public String toString() {
        Set<Entry<Formula, Integer>> collect = addends.entrySet().stream()
                .filter(formulaIntegerEntry -> !formulaIntegerEntry.getValue().equals(0)).collect(Collectors.toUnmodifiableSet());
        return "(" + collect.stream().filter(formulaIntegerEntry -> formulaIntegerEntry.getValue() > 0).map(formulaIntegerEntry ->
                        (formulaIntegerEntry.getValue().equals(1) ? "" : formulaIntegerEntry.getValue().toString())
                                + formulaIntegerEntry.getKey())
                .collect(Collectors.joining("+")) +
                collect.stream().filter(formulaIntegerEntry -> formulaIntegerEntry.getValue() < 0)
                        .map(formulaIntegerEntry -> (formulaIntegerEntry.getValue().equals(-1) ? "-" : formulaIntegerEntry.getValue().toString()) +
                                "" + formulaIntegerEntry.getKey())
                        .collect(Collectors.joining()) + ")";
    }
}
