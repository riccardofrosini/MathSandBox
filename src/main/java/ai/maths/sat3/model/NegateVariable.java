package ai.maths.sat3.model;

import java.util.Objects;

public class NegateVariable extends SingletonClause<Variable> {

    private final Variable variable;

    public NegateVariable(Variable variable) {
        this.variable = variable;
    }

    @Override
    public Variable getVariableOrBoolean() {
        return variable;
    }

    @Override
    public boolean isEqualNegated(SingletonClause<?> other) {
        return (other instanceof Variable) && variable.getName().equals(((Variable) other).getVariableOrBoolean().getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NegateVariable that = (NegateVariable) o;
        return variable.equals(that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }

    @Override
    public String toString() {
        return "¬" + variable;
    }
}