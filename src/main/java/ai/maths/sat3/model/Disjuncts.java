package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Disjuncts<T extends Clause<?>> implements Clause<T> {

    public static final Disjuncts<?> TRUE = new Disjuncts<>(Collections.emptySet());

    protected Set<T> disjuncts;

    protected Disjuncts(Set<T> disjuncts) {
        this.disjuncts = disjuncts;
    }

    @Override
    public Stream<T> getSubClauses() {
        return disjuncts.stream();
    }

    @Override
    public T getAnySubClause() {
        return disjuncts.iterator().next();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disjuncts<?> disjuncts = (Disjuncts<?>) o;
        return this.disjuncts.equals(disjuncts.disjuncts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disjuncts);
    }

    @Override
    public String toString() {
        if (this.equals(TRUE)) {
            return "T";
        }
        return disjuncts.stream().map(Object::toString).collect(Collectors.joining("∨", "(", ")"));
    }
}
