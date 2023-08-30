package ai.maths.sat3.model;

import java.util.Set;

public abstract class SingletonClause<T extends VariableOrBoolean> implements Clause {

    public abstract T getVariableOrBoolean();

    @Override
    public Set<VariableOrBoolean> getAllVariablesAndConstants() {
        return Set.of(getVariableOrBoolean());
    }

    @Override
    public Clause simplify() {
        return this;
    }
}
