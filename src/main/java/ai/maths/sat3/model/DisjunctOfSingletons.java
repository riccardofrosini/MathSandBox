package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Set;

public class DisjunctOfSingletons extends Disjuncts<Singleton> implements DisjunctOfSingletonsOrSingleton {

    public static final DisjunctOfSingletons FALSE = new DisjunctOfSingletons(Collections.emptySet());

    protected DisjunctOfSingletons(Set<Singleton> disjuncts) {
        super(disjuncts);
    }
}
