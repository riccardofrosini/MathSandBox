package ai.maths.sat3.model;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ConjunctClause<T extends Clause> implements Clause {

    protected final Set<T> conjuncts;

    protected ConjunctClause(Set<T> conjuncts) {
        this.conjuncts = conjuncts;
    }

    public Set<T> getConjuncts() {
        return conjuncts;
    }

    @Override
    public Set<VariableOrBoolean> getAllVariablesAndConstants() {
        return conjuncts.stream().flatMap(t -> t.getAllVariablesAndConstants().stream()).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConjunctClause<?> that = (ConjunctClause<?>) o;
        return conjuncts.equals(that.conjuncts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conjuncts);
    }

    @Override
    public String toString() {
        return conjuncts.stream().map(Object::toString).collect(Collectors.joining("∧", "(", ")"));
    }
}
