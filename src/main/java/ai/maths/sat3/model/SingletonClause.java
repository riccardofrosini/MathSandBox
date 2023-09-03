package ai.maths.sat3.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SingletonClause extends Clause {

    @Override
    public Clause addConjunct(Clause conjunct) {
        return new ConjunctClause<>(Stream.of(this, conjunct).collect(Collectors.toSet())).simplify();
    }

    @Override
    public Clause simplify() {
        return this;
    }
}
