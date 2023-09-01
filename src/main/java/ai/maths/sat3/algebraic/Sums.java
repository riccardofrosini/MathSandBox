package ai.maths.sat3.algebraic;

import static ai.maths.sat3.algebraic.Constant.CONSTANT_0;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sums extends Formula {

    private final Map<Formula, Integer> addends;

    protected Sums(Map<Formula, Integer> addends) {
        this.addends = addends;
    }

    public Formula simplify() {
        HashMap<Formula, Integer> newAddends = new HashMap<>(addends);
        newAddends.entrySet().removeIf(formulaIntegerEntry -> formulaIntegerEntry.getValue().equals(0));
        if (newAddends.isEmpty()) {
            return CONSTANT_0;
        }
        if (newAddends.size() == 1) {
            Entry<Formula, Integer> next = newAddends.entrySet().iterator().next();
            return new Products(Set.of(next.getKey(), new Constant(next.getValue()))).simplify();
        }
        Set<Sums> sumsSet = newAddends.keySet().stream()
                .filter(integer -> integer instanceof Sums)
                .map(integer -> (Sums) integer)
                .collect(Collectors.toUnmodifiableSet());
        newAddends.keySet().removeAll(sumsSet);
        if (newAddends.equals(addends)) {
            return this;
        }
        return new Sums(Stream.concat(sumsSet.stream()
                                .flatMap(sums -> sums.addends.entrySet()
                                        .stream()
                                        .collect(Collectors.toMap(Entry::getKey, formulaIntegerEntry -> formulaIntegerEntry.getValue() * addends.get(sums))).entrySet().stream()),
                        newAddends.entrySet().stream())
                .collect(Collectors.toUnmodifiableMap(Entry::getKey, Entry::getValue, Integer::sum))).simplify();
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
