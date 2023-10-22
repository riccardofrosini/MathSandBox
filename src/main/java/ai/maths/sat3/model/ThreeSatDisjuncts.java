package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatDisjuncts extends Conjuncts<ThreeConjunctOfSingletonsOrSingleton> implements DNF<ThreeConjunctOfSingletonsOrSingleton> {

    protected ThreeSatDisjuncts(Set<ThreeConjunctOfSingletonsOrSingleton> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
