package ai.maths.sat3.model;

import java.util.Objects;
import java.util.stream.Stream;

import ai.maths.sat3.sets.VariableSingletonSet;

public class Variable extends VariableSingletonSet implements Singleton {

    private final String var;

    protected Variable(String var) {
        super();
        this.var = var;
    }

    @Override
    public Stream<Singleton> getSubClauses() {
        return Stream.of(this);
    }

    @Override
    public Singleton getAnySubClause() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variable variable = (Variable) o;
        return var.equals(variable.var);
    }

    @Override
    public int hashCode() {
        return Objects.hash(var);
    }

    @Override
    public String toString() {
        return var;
    }
}
