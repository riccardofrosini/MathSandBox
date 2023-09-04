package ai.maths.sat3.model;

public abstract class Singleton implements SingletonOrDisjunctsConjunctsOfNonConstant {

    @Override
    public Singleton simplify() {
        return this;
    }
}
