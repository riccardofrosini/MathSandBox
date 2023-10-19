package ai.maths.sat3.model;

import java.util.Set;

public abstract class DisjunctsOfSingletons extends Disjuncts<Singleton> {

    public DisjunctsOfSingletons(Set<Singleton> disjuncts) {
        super(disjuncts);
    }
}
