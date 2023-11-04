package ai.maths.sat3.model.sat3;

import java.util.Set;

public class Disjunct2 extends DisjunctOfSingletons implements ThreeDisjunctOfSingletonsOrSingleton, TwoDisjunctOfSingletonsOrSingleton {

    protected Disjunct2(Singleton singleton1, Singleton singleton2) {
        super(Set.of(singleton1, singleton2));
    }
}
