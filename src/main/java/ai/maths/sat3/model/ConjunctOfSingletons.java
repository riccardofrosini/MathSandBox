package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Set;

public class ConjunctOfSingletons extends Conjuncts<Singleton> implements ConjunctOfSingletonsOrSingleton {

    public static final ConjunctOfSingletons TRUE = new ConjunctOfSingletons(Collections.emptySet());

    protected ConjunctOfSingletons(Set<Singleton> conjuncts) {
        super(conjuncts);
    }
}
