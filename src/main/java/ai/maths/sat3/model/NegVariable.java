package ai.maths.sat3.model;

import java.util.stream.Stream;

public class NegVariable extends Negation<Singleton> implements Singleton {

    protected NegVariable(Variable variable) {
        super(variable);
    }

    @Override
    public Singleton getAnySubClause() {
        return this;
    }

    @Override
    public Stream<Singleton> getSubClauses() {
        return Stream.of(this);
    }
}
