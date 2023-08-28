package ai.maths.sat3.model;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DisjunctClause<T extends Clause> implements Clause {

    protected final Set<T> disjuncts;

    protected DisjunctClause(Set<T> disjuncts) {
        this.disjuncts = disjuncts;
    }

    public Set<T> getDisjuncts() {
        return disjuncts;
    }

    @Override
    public Set<VariableOrBoolean> getAllVariablesAndConstants() {
        return disjuncts.stream().flatMap(t -> t.getAllVariablesAndConstants().stream()).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisjunctClause<?> that = (DisjunctClause<?>) o;
        return disjuncts.equals(that.disjuncts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disjuncts);
    }

    @Override
    public String toString() {
        return disjuncts.stream().map(Object::toString).collect(Collectors.joining("∨", "(", ")"));
    }
}
