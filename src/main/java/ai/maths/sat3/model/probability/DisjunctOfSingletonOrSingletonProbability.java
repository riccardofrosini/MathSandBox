package ai.maths.sat3.model.probability;

import java.util.Map;
import java.util.Set;

import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;

public class DisjunctOfSingletonOrSingletonProbability extends ProbabilityOfCNF {

    public static final DisjunctOfSingletonOrSingletonProbability FALSE = new DisjunctOfSingletonOrSingletonProbability();

    private DisjunctOfSingletonOrSingletonProbability() {
        super(Map.of(Set.of(), 0));
    }

    public DisjunctOfSingletonOrSingletonProbability(DisjunctOfSingletonsOrSingleton disjunctOfSingletonsOrSingleton) {
        super(Map.of(Set.of(ConjunctOfSingletonOrSingletonProbability.TRUE), 1,
                Set.of(new ConjunctOfSingletonOrSingletonProbability(ClauseBuilder.buildNegationOfDisjunctOfSingletons(disjunctOfSingletonsOrSingleton))), -1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return 67 * super.hashCode();
    }
}
