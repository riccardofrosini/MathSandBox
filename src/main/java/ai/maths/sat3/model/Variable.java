package ai.maths.sat3.model;

import java.util.Objects;
import java.util.Set;

public class Variable extends NonBoolean {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Variable getVariable() {
        return this;
    }

    @Override
    public Set<Variable> getAllVariables() {
        return Set.of(this);
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
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
