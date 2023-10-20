package ai.maths.sat3.probability;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.Conjuncts;
import ai.maths.sat3.model.Disjuncts;

public class Probability {

    public static double probability(Clause<?> clause) {
        if (clause == Disjuncts.FALSE) {
            return 0;
        }
        if (clause == Conjuncts.TRUE) {
            return 1;
        }
        return 0;
    }
}
