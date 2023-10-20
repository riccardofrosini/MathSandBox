package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Conjuncts<T extends Clause<?>> implements Clause<T> {

    public static final Conjuncts<?> FALSE = new Conjuncts<>(Collections.emptySet());

    protected Set<T> conjuncts;

    protected Conjuncts(Set<T> conjuncts) {
        this.conjuncts = conjuncts;
    }

    public Stream<T> getSubClauses() {
        return conjuncts.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conjuncts<?> conjuncts = (Conjuncts<?>) o;
        return this.conjuncts.equals(conjuncts.conjuncts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conjuncts);
    }

    @Override
    public String toString() {
        if (this.equals(FALSE)) {
            return "F";
        }
        return conjuncts.stream().map(Object::toString).collect(Collectors.joining("∧", "(", ")"));
    }
}
