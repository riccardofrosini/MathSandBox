package ai.maths.sat3.probability;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.ThreeDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.ThreeSatConjuncts;

public class SplitThreeSatClause {

    private ThreeDisjunctOfSingletonsOrSingleton<?> first;
    private Clause<?> rest;

    private SplitThreeSatClause(ThreeDisjunctOfSingletonsOrSingleton<?> first, Clause<?> rest) {
        this.first = first;
        this.rest = rest;
    }

    public static SplitThreeSatClause split(ThreeSatConjuncts conjuncts) {
        ThreeDisjunctOfSingletonsOrSingleton<?> anySubClause = conjuncts.getAnySubClause();
        Clause<?> remaining = ClauseBuilder.buildConjuncts(conjuncts.getSubClauses().filter(t -> t != anySubClause).toArray(
                (ThreeDisjunctOfSingletonsOrSingleton[]::new)));
        return new SplitThreeSatClause(anySubClause, remaining);
    }


    public Clause<?> makeRestIndependentToFirst() {
        return null;
    }

    public ThreeDisjunctOfSingletonsOrSingleton<?> getFirst() {
        return first;
    }

    public Clause<?> getRest() {
        return rest;
    }
}
