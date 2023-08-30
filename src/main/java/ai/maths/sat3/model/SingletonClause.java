package ai.maths.sat3.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SingletonClause<T extends VariableOrBoolean> implements Clause {

    public abstract T getVariableOrBoolean();

    @Override
    public Set<VariableOrBoolean> getAllVariablesAndConstants() {
        return Set.of(getVariableOrBoolean());
    }

    @Override
    public Clause addConjunct(Clause conjunct) {
        return new ConjunctClause<>(Stream.of(this, conjunct).collect(Collectors.toUnmodifiableSet())).simplify();
    }

    @Override
    public Clause simplify() {
        return this;
    }
}
