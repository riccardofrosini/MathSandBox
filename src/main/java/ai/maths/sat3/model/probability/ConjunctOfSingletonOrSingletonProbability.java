package ai.maths.sat3.model.probability;

import java.util.Objects;

import ai.maths.sat3.model.sat3.ConjunctOfSingletons;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.Singleton;

public class ConjunctOfSingletonOrSingletonProbability extends ProbabilityOfCNF {

    public static final ConjunctOfSingletonOrSingletonProbability TRUE = new ConjunctOfSingletonOrSingletonProbability(ConjunctOfSingletons.TRUE);

    private final ConjunctOfSingletonsOrSingleton conjunctOfSingletonsOrSingleton;

    public ConjunctOfSingletonOrSingletonProbability(ConjunctOfSingletonsOrSingleton conjunctOfSingletonsOrSingleton) {
        super();
        this.conjunctOfSingletonsOrSingleton = conjunctOfSingletonsOrSingleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConjunctOfSingletonOrSingletonProbability that = (ConjunctOfSingletonOrSingletonProbability) o;
        return conjunctOfSingletonsOrSingleton.equals(that.conjunctOfSingletonsOrSingleton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conjunctOfSingletonsOrSingleton);
    }

    @Override
    public String toString() {
        if (conjunctOfSingletonsOrSingleton == ConjunctOfSingletons.TRUE) {
            return "1";
        }
        if (conjunctOfSingletonsOrSingleton instanceof Singleton) {
            return "P(" + conjunctOfSingletonsOrSingleton + ")";
        }
        return "P" + conjunctOfSingletonsOrSingleton;
    }
}
