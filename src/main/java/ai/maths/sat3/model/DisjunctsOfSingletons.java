package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctsOfSingletons extends Disjuncts<Singleton> implements DisjunctOfSingletonsOrSingleton {

    protected DisjunctsOfSingletons(Set<Singleton> disjuncts) {
        super(disjuncts);
    }
}
