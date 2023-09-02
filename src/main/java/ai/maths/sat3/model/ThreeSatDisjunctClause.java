package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatDisjunctClause extends DisjunctOfSingletons {

    public ThreeSatDisjunctClause(NonBoolean singletonClause1, NonBoolean singletonClause2, NonBoolean singletonClause3) {
        super(Set.of(singletonClause1, singletonClause2, singletonClause3));
    }
}
