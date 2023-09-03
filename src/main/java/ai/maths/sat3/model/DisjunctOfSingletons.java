package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfSingletons extends DisjunctClause<SingletonVariable> {

    protected DisjunctOfSingletons(Set<SingletonVariable> disjuncts) {
        super(disjuncts);
    }
}
