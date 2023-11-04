package ai.maths.sat3.model.sat3;

import java.util.Set;

public class ThreeSatDisjuncts extends Disjuncts<ThreeConjunctOfSingletonsOrSingleton> implements DNF<ThreeConjunctOfSingletonsOrSingleton> {

    protected ThreeSatDisjuncts(Set<ThreeConjunctOfSingletonsOrSingleton> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
