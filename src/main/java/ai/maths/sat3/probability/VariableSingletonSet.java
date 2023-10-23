package ai.maths.sat3.probability;

import java.util.Set;

import ai.maths.sat3.model.Singleton;
import ai.maths.sat3.model.Variable;

public abstract class VariableSingletonSet {

    private final Set<Variable> variables;
    private final Set<Singleton> singletons;

    protected VariableSingletonSet() {
        this.variables = Set.of((Variable) this);
        this.singletons = Set.of((Singleton) this);
    }

    protected VariableSingletonSet(Set<Variable> variables) {
        this.variables = variables;
        this.singletons = Set.of((Singleton) this);
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