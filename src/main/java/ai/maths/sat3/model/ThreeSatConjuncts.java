package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatConjuncts extends Conjuncts<ThreeDisjunctOfSingletonsOrSingleton> implements CNF<ThreeDisjunctOfSingletonsOrSingleton> {

    protected ThreeSatConjuncts(Set<ThreeDisjunctOfSingletonsOrSingleton> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
