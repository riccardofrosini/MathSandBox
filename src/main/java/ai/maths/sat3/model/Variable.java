package ai.maths.sat3.model;

import java.util.Objects;

public class Variable extends VariableOrBoolean {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Variable getVariableOrBoolean() {
        return this;
    }

    @Override
    public boolean isEqualNegated(SingletonClause other) {
        return (other instanceof NegateVariable) && equals(other.getVariableOrBoolean());
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
