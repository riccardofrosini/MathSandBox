package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfSingletons extends DisjunctClause<NonBoolean> {

    protected DisjunctOfSingletons(Set<NonBoolean> disjuncts) {
        super(disjuncts);
    }
}
