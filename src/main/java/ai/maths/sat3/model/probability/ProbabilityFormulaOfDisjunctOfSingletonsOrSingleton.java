package ai.maths.sat3.model.probability;

import java.util.Map;
import java.util.Set;

import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;

public class ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton extends ProbabilityFormulaOfCNF {

    public static final ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton FALSE = new ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton();

    protected ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton() {
        super(Map.of(Set.of(), 0L));
    }

    protected ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton(DisjunctOfSingletonsOrSingleton disjunctOfSingletonsOrSingleton) {
        super(Map.of(Set.of(ProbabilityFormulaOfConjunctOfSingletonsOrSingleton.TRUE), 1L,
                Set.of(new ProbabilityFormulaOfConjunctOfSingletonsOrSingleton(ClauseBuilder.buildNegationOfDisjunctOfSingletons(disjunctOfSingletonsOrSingleton))), -1L));
    }

    public double getProbability() {
        return this == FALSE ? 0.0 : super.getProbability();
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

    @Override
    public String toString() {
        return this == FALSE ? "0" : super.toString();
    }
}
