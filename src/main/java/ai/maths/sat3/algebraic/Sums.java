package ai.maths.sat3.algebraic;

import static ai.maths.sat3.algebraic.Constant.CONSTANT_0;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sums extends NotAProduct {

    private final Map<Formula, Integer> addends;

    protected Sums(Map<Formula, Integer> addends) {
        this.addends = Collections.unmodifiableMap(addends);
    }

    public Stream<Entry<Formula, Integer>> getStreamOfAddends() {
        return addends.entrySet().stream();
    }

    @Override
    public NotAProduct simplify() {
        HashMap<Formula, Integer> newAddends = new HashMap<>(addends);
        newAddends.entrySet().removeIf(formulaIntegerEntry -> formulaIntegerEntry.getValue().equals(0) || formulaIntegerEntry.getKey().equals(CONSTANT_0));
        if (newAddends.isEmpty()) {
            return CONSTANT_0;
        }
        Set<Sums> sumsSet = newAddends.keySet().stream()
                .filter(addon -> addon instanceof Sums)
                .map(addon -> (Sums) addon)
                .collect(Collectors.toSet());
        newAddends.keySet().removeAll(sumsSet);
        if (newAddends.equals(addends)) {
            return this;
        }
        return new Sums(Stream.concat(sumsSet.stream()
                                .flatMap(sums -> sums.addends.entrySet().stream()
                                        .collect(Collectors.toMap(Entry::getKey, formulaIntegerEntry -> formulaIntegerEntry.getValue() * addends.get(sums))).entrySet().stream()),
                        newAddends.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Integer::sum))).simplify();
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
                .filter(formulaIntegerEntry -> !formulaIntegerEntry.getValue().equals(0)).collect(Collectors.toSet());
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
