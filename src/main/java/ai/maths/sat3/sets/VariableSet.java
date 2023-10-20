package ai.maths.sat3.sets;

import java.util.Set;

import ai.maths.sat3.model.Variable;

public abstract class VariableSet {

    protected Set<Variable> variables;

    protected VariableSet() {
        this.variables = Set.of((Variable) this);
    }

    protected VariableSet(Set<Variable> variables) {
        this.variables = variables;
    }

    public Set<Variable> getVariables() {
        return variables;
    }
}
