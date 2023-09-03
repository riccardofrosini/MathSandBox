package ai.maths.sat3.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SingletonVariable extends SingletonClause {

    public abstract Variable getVariable();

    @Override
    public Clause addConjunct(Clause conjunct) {
        return new ConjunctClause<>(Stream.of(this, conjunct).collect(Collectors.toSet())).simplify();
    }

}
