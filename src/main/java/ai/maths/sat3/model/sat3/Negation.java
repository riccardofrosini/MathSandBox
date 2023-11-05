package ai.maths.sat3.model.sat3;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class Negation<T extends Clause<?>> extends VariableSingletonSet implements Clause<T> {

    private final T clause;

    protected Negation(T clause) {
        throw new RuntimeException("Negation not implemented");
    }

    protected Negation(Variable clause) {
        super(clause);
        this.clause = (T) clause;
    }

    @Override
    public Stream<T> getSubClauses() {
        throw new RuntimeException("Negation not implemented");
    }

    @Override
    public T getAnySubClause() {
        throw new RuntimeException("Negation not implemented");
    }

    public T getNegatedClause() {
        return clause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Negation<?> negation = (Negation<?>) o;
        return clause.equals(negation.clause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clause);
    }

    @Override
    public String toString() {
        return "¬" + clause;
    }
}
