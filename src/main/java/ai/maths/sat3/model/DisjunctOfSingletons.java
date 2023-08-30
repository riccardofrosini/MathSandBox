package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfSingletons extends DisjunctClause<SingletonClause<?>> {

    protected DisjunctOfSingletons(SingletonClause<?>... disjuncts) {
        super(Set.of(disjuncts));
    }
}
