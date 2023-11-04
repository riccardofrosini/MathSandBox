package ai.maths.sat3.model.sat3;

import java.util.Set;

public class TwoSatConjuncts extends Conjuncts<TwoDisjunctOfSingletonsOrSingleton> implements CNF<TwoDisjunctOfSingletonsOrSingleton> {

    protected TwoSatConjuncts(Set<TwoDisjunctOfSingletonsOrSingleton> disjunctsOfSingletons) {
        super(disjunctsOfSingletons);
    }
}
