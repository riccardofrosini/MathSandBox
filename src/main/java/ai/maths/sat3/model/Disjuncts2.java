package ai.maths.sat3.model;

import java.util.Set;

public class Disjuncts2 extends DisjunctsOfSingletons implements ThreeDisjunctOfSingletonsOrSingleton<Singleton> {

    protected Disjuncts2(Singleton singleton1, Singleton singleton2) {
        super(Set.of(singleton1, singleton2));
    }
}
