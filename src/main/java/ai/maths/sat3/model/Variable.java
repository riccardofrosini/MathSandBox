package ai.maths.sat3.model;

import java.util.Objects;
import java.util.stream.Stream;

public class Variable implements Singleton {

    private String var;

    protected Variable(String var) {
        this.var = var;
    }

    @Override
    public Stream<Variable> getSubClauses() {
        return Stream.of(this);
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
