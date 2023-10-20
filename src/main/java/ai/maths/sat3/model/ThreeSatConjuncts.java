package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatConjuncts extends Conjuncts<DisjunctOfSingletonsOrSingleton<?>> {

    protected ThreeSatConjuncts(Set<DisjunctOfSingletonsOrSingleton<?>> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
