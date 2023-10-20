package ai.maths.sat3.model;

import java.util.Set;

public class NegThreeSatDisjuncts extends Conjuncts<ThreeConjunctOfSingletonsOrSingleton<?>> {

    protected NegThreeSatDisjuncts(Set<ThreeConjunctOfSingletonsOrSingleton<?>> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
