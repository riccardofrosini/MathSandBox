package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatConjuncts extends Conjuncts<ThreeDisjunctOfSingletonsOrSingleton<?>> {

    protected ThreeSatConjuncts(Set<ThreeDisjunctOfSingletonsOrSingleton<?>> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
