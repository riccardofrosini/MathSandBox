package ai.maths.sat3.model;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.maths.sat3.probability.VariableSingletonSet;

public abstract class Conjuncts<T extends Clause<?>> extends VariableSingletonSet implements Clause<T> {

    private final Set<T> conjuncts;

    protected Conjuncts(Set<T> conjuncts) {
        super(conjuncts.stream().flatMap(t -> t.getVariables().stream()).collect(Collectors.toUnmodifiableSet()),
                conjuncts.stream().flatMap(t -> t.getSingletons().stream()).collect(Collectors.toUnmodifiableSet()));
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
        if (this == ConjunctOfSingletons.TRUE) {
            return "T";
        }
        return conjuncts.stream().map(Object::toString).collect(Collectors.joining("∧", "(", ")"));
    }
}
