package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctOfSingletons extends ConjunctClause<NonBoolean> {

    protected ConjunctOfSingletons(Set<NonBoolean> conjuncts) {
        super(conjuncts);
    }
}
