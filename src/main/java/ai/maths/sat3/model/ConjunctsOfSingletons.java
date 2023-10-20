package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctsOfSingletons extends Conjuncts<Singleton> implements ConjunctOfSingletonsOrSingleton<Singleton> {

    protected ConjunctsOfSingletons(Set<Singleton> conjuncts) {
        super(conjuncts);
    }
}
