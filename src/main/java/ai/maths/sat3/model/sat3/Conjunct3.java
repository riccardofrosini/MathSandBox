package ai.maths.sat3.model.sat3;

import java.util.Set;

public class Conjunct3 extends ConjunctOfSingletons implements ThreeConjunctOfSingletonsOrSingleton {

    protected Conjunct3(Singleton singleton1, Singleton singleton2, Singleton singleton3) {
        super(Set.of(singleton1, singleton2, singleton3));
    }
}
