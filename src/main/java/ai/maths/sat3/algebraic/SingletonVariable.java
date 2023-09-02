package ai.maths.sat3.algebraic;

import java.util.Objects;

public class SingletonVariable extends NotAProduct {

    private final String variable;

    protected SingletonVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public Formula simplify() {
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
        SingletonVariable that = (SingletonVariable) o;
        return variable.equals(that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }

    @Override
    public String toString() {
        return variable;
    }
}
