package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatDisjunctClause extends DisjunctOfSingletons {

    public ThreeSatDisjunctClause(SingletonClause<?> singletonClause1, SingletonClause<?> singletonClause2, SingletonClause<?> singletonClause3) {
        super(Set.of(singletonClause1, singletonClause2, singletonClause3));
    }
}
