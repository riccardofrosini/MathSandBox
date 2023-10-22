package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Set;

public class ConjunctsOfSingletons extends Conjuncts<Singleton> implements ConjunctOfSingletonsOrSingleton {

    public static final ConjunctsOfSingletons TRUE = new ConjunctsOfSingletons(Collections.emptySet());

    protected ConjunctsOfSingletons(Set<Singleton> conjuncts) {
        super(conjuncts);
    }
}
