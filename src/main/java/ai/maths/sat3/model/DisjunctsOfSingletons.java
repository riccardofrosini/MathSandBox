package ai.maths.sat3.model;

import java.util.Collections;
import java.util.Set;

public class DisjunctsOfSingletons extends Disjuncts<Singleton> implements DisjunctOfSingletonsOrSingleton {

    public static final DisjunctsOfSingletons FALSE = new DisjunctsOfSingletons(Collections.emptySet());

    protected DisjunctsOfSingletons(Set<Singleton> disjuncts) {
        super(disjuncts);
    }
}
