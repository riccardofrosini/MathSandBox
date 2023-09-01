package ai.maths.sat3.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ThreeSatConjunctClause extends ConjunctClause<ThreeSatDisjunctClause> {

    public ThreeSatConjunctClause(ThreeSatDisjunctClause... conjuncts) {
        super(Arrays.stream(conjuncts).collect(Collectors.toSet()));
    }
}
