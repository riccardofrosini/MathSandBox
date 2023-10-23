package ai.maths.sat3.model;

import java.util.Set;

public class TwoSatDisjuncts extends Disjuncts<TwoConjunctOfSingletonsOrSingleton> implements DNF<TwoConjunctOfSingletonsOrSingleton> {

    protected TwoSatDisjuncts(Set<TwoConjunctOfSingletonsOrSingleton> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
