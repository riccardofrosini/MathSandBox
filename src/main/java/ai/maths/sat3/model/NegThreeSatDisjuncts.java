package ai.maths.sat3.model;

import java.util.Set;

public class NegThreeSatDisjuncts extends Conjuncts<ConjunctOfSingletonsOrSingleton<?>> {

    protected NegThreeSatDisjuncts(Set<ConjunctOfSingletonsOrSingleton<?>> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
