package ai.maths.sat3.model;

import java.util.Set;

public class Conjunct2 extends ConjunctOfSingletons implements ThreeConjunctOfSingletonsOrSingleton, TwoConjunctOfSingletonsOrSingleton {

    protected Conjunct2(Singleton singleton1, Singleton singleton2) {
        super(Set.of(singleton1, singleton2));
    }
}
