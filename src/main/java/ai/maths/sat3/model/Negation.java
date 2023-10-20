package ai.maths.sat3.model;

import java.util.Objects;
import java.util.stream.Stream;

public class Negation<T extends Clause<?>> implements Clause<T> {

    protected T clause;

    protected Negation(T clause) {
        this.clause = clause;
    }

    @Override
    public Stream<T> getSubClauses() {
        return Stream.of(clause);
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
