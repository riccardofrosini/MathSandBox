package ai.maths.sat3.model;

import java.util.Set;

public class Conjuncts3 extends ConjunctsOfSingletons implements ThreeConjunctOfSingletonsOrSingleton<Singleton> {

    protected Conjuncts3(Singleton singleton1, Singleton singleton2, Singleton singleton3) {
        super(Set.of(singleton1, singleton2, singleton3));
    }
}
