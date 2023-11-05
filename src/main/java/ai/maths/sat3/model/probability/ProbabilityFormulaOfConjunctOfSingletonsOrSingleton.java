package ai.maths.sat3.model.probability;

import java.util.Objects;

import ai.maths.sat3.model.sat3.ConjunctOfSingletons;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.Singleton;

public class ProbabilityFormulaOfConjunctOfSingletonsOrSingleton extends ProbabilityFormulaOfCNF {

    public static final ProbabilityFormulaOfConjunctOfSingletonsOrSingleton TRUE = new ProbabilityFormulaOfConjunctOfSingletonsOrSingleton(ConjunctOfSingletons.TRUE);

    private final ConjunctOfSingletonsOrSingleton conjunctOfSingletonsOrSingleton;

    protected ProbabilityFormulaOfConjunctOfSingletonsOrSingleton(ConjunctOfSingletonsOrSingleton conjunctOfSingletonsOrSingleton) {
        super();
        this.conjunctOfSingletonsOrSingleton = conjunctOfSingletonsOrSingleton;
    }

    public double getProbability() {
        return this == TRUE ? 1.0 : 1 / Math.pow(2, conjunctOfSingletonsOrSingleton.getVariables().size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProbabilityFormulaOfConjunctOfSingletonsOrSingleton that = (ProbabilityFormulaOfConjunctOfSingletonsOrSingleton) o;
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
