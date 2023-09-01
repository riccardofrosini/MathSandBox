package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfSingletons extends DisjunctClause<SingletonClause<?>> {

    protected DisjunctOfSingletons(Set<SingletonClause<?>> disjuncts) {
        super(disjuncts);
    }
}
