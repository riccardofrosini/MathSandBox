package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctOfSingletons extends ConjunctClause<SingletonClause<?>> {

    protected ConjunctOfSingletons(SingletonClause<?>... conjuncts) {
        super(Set.of(conjuncts));
    }
}
