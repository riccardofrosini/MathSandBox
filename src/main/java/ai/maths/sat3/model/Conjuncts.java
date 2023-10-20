package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.maths.sat3.sets.VariableSet;

public class Conjuncts<T extends Clause<?>> extends VariableSet implements Clause<T> {

    public static final Conjuncts<?> TRUE = new Conjuncts<>(Collections.emptySet());

    protected Set<T> conjuncts;

    protected Conjuncts(Set<T> conjuncts) {
        super(conjuncts.stream().flatMap(t -> t.getVariables().stream()).collect(Collectors.toUnmodifiableSet()));
        this.conjuncts = conjuncts;
    }

    @Override
    public Stream<T> getSubClauses() {
        return conjuncts.stream();
    }

    @Override
    public T getAnySubClause() {
        return conjuncts.iterator().next();
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
        if (this == TRUE) {
            return "T";
        }
        return conjuncts.stream().map(Object::toString).collect(Collectors.joining("∧", "(", ")"));
    }
}
