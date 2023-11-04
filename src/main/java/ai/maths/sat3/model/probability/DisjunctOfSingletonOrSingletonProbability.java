package ai.maths.sat3.model.probability;

import java.util.Objects;

import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.Singleton;

public class DisjunctOfSingletonOrSingletonProbability extends ProbabilityOfCNF {

    public static final DisjunctOfSingletonOrSingletonProbability FALSE = new DisjunctOfSingletonOrSingletonProbability(DisjunctOfSingletons.FALSE);

    private final DisjunctOfSingletonsOrSingleton disjunctOfSingletonsOrSingleton;

    public DisjunctOfSingletonOrSingletonProbability(DisjunctOfSingletonsOrSingleton disjunctOfSingletonsOrSingleton) {
        super();
        this.disjunctOfSingletonsOrSingleton = disjunctOfSingletonsOrSingleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisjunctOfSingletonOrSingletonProbability that = (DisjunctOfSingletonOrSingletonProbability) o;
        return disjunctOfSingletonsOrSingleton.equals(that.disjunctOfSingletonsOrSingleton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disjunctOfSingletonsOrSingleton);
    }

    @Override
    public String toString() {
        if (disjunctOfSingletonsOrSingleton == DisjunctOfSingletons.FALSE) {
            return "0";
        }
        if (disjunctOfSingletonsOrSingleton instanceof Singleton) {
            return "P(" + disjunctOfSingletonsOrSingleton + ")";
        }
        return "P" + disjunctOfSingletonsOrSingleton;
    }
}
