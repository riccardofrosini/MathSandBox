package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatDisjunctClause extends DisjunctOfSingletons {

    public ThreeSatDisjunctClause(SingletonVariable singletonClause1, SingletonVariable singletonClause2, SingletonVariable singletonClause3) {
        super(Set.of(singletonClause1, singletonClause2, singletonClause3));
    }
}
