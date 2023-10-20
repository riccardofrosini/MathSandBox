package ai.maths.sat3.model;

import java.util.Objects;
import java.util.stream.Stream;

import ai.maths.sat3.sets.VariableSet;

public class Variable extends VariableSet implements Singleton {

    private String var;

    protected Variable(String var) {
        super();
        this.var = var;
    }

    @Override
    public Stream<Variable> getSubClauses() {
        return Stream.of(this);
    }

    @Override
    public Variable getAnySubClause() {
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
