package ai.maths.sat3.model;

import java.util.Objects;
import java.util.Set;

public class NegateVariable extends SingletonVariable {

    private final Variable variable;

    public NegateVariable(Variable variable) {
        this.variable = variable;
    }

    @Override
    public Variable getVariable() {
        return variable;
    }

    @Override
    public Set<Variable> getAllVariables() {
        return Set.of(variable);
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
        return -Objects.hash(variable);
    }

    @Override
    public String toString() {
        return "¬" + variable;
    }
}
