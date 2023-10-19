package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatConjuncts extends Conjuncts<DisjunctsOfSingletons> {

    protected ThreeSatConjuncts(Set<DisjunctsOfSingletons> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
