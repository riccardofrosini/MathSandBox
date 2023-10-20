package ai.maths.sat3.sets;

import java.util.Set;

import ai.maths.sat3.model.Singleton;
import ai.maths.sat3.model.Variable;

public abstract class VariableSingletonSet {

    protected Set<Variable> variables;
    protected Set<Singleton> singletons;

    protected VariableSingletonSet() {
        this.variables = Set.of((Variable) this);
        this.singletons = Set.of((Variable) this);
    }

    protected VariableSingletonSet(Set<Variable> variables, Set<Singleton> singletons) {
        this.variables = variables;
        this.singletons = singletons;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public Set<Singleton> getSingletons() {
        return singletons;
    }
}
