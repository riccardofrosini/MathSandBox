package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctOfSingletons extends ConjunctClause<SingletonVariable> {

    protected ConjunctOfSingletons(Set<SingletonVariable> conjuncts) {
        super(conjuncts);
    }
}
