package ai.maths.sat3.model;

import java.util.Set;

public class ThreeSatConjunctClause extends ConjunctClause<ThreeSatDisjunctClause> {

    public ThreeSatConjunctClause(ThreeSatDisjunctClause... clauses) {
        super(Set.of(clauses));
    }
}
