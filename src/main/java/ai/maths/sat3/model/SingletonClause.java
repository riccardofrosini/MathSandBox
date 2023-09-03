package ai.maths.sat3.model;

public abstract class SingletonClause extends Clause {

    @Override
    public Clause simplify() {
        return this;
    }
}
