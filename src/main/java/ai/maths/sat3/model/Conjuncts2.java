package ai.maths.sat3.model;

import java.util.Set;

public class Conjuncts2 extends ConjunctsOfSingletons implements ThreeConjunctOfSingletonsOrSingleton {

    protected Conjuncts2(Singleton singleton1, Singleton singleton2) {
        super(Set.of(singleton1, singleton2));
    }
}
