package ai.maths.sat3;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.bayesian.ProbabilityOfClause;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.ThreeSatConjunctClause;
import ai.maths.sat3.model.ThreeSatDisjunctClause;
import ai.maths.sat3.model.Variable;

public class Main {

    public static void main(String[] args) {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");
        Variable u = new Variable("u");
        Variable v = new Variable("v");
        Variable w = new Variable("w");
        NegateVariable nx = new NegateVariable(x);
        NegateVariable ny = new NegateVariable(y);
        NegateVariable nz = new NegateVariable(z);
        ThreeSatDisjunctClause threeSatDisjunctClause = new ThreeSatDisjunctClause(x, y, z);
        ThreeSatDisjunctClause threeSatDisjunctClause1 = new ThreeSatDisjunctClause(x, y, u);
        ThreeSatDisjunctClause threeSatDisjunctClause2 = new ThreeSatDisjunctClause(x, u, v);
        ThreeSatDisjunctClause threeSatDisjunctClause3 = new ThreeSatDisjunctClause(u, v, w);
        ThreeSatDisjunctClause threeSatDisjunctClause4 = new ThreeSatDisjunctClause(nx, y, z);
        ThreeSatDisjunctClause threeSatDisjunctClause5 = new ThreeSatDisjunctClause(nx, ny, z);
        ThreeSatDisjunctClause threeSatDisjunctClause6 = new ThreeSatDisjunctClause(nx, ny, nz);
        ThreeSatDisjunctClause threeSatDisjunctClause7 = new ThreeSatDisjunctClause(nx, y, u);
        ThreeSatDisjunctClause threeSatDisjunctClause8 = new ThreeSatDisjunctClause(nx, ny, u);
        ThreeSatDisjunctClause threeSatDisjunctClause9 = new ThreeSatDisjunctClause(nx, u, v);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause1);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause2);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause3);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause4);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause5);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause6);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause7);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause8);
        probGiven(threeSatDisjunctClause, threeSatDisjunctClause9);

        ProbabilityOfClause<?> probabilityOfClause1 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause);
        ProbabilityOfClause<?> probabilityOfClause2 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause1);
        ProbabilityOfClause<?> probabilityOfClause3 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause2);
        ProbabilityOfClause<?> probabilityOfClause4 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause3);
        ProbabilityOfClause<?> probabilityOfClause5 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause4);
        ProbabilityOfClause<?> probabilityOfClause6 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause5);
        ProbabilityOfClause<?> probabilityOfClause7 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause6);
        ProbabilityOfClause<?> probabilityOfClause8 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause7);
        ProbabilityOfClause<?> probabilityOfClause9 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause8);
        ProbabilityOfClause<?> probabilityOfClause10 = ProbabilityOfClause.probabilityOfIntersection(threeSatDisjunctClause, threeSatDisjunctClause9);
        System.out.println(probabilityOfClause1);
        System.out.println(probabilityOfClause2);
        System.out.println(probabilityOfClause3);
        System.out.println(probabilityOfClause4);
        System.out.println(probabilityOfClause5);
        System.out.println(probabilityOfClause6);
        System.out.println(probabilityOfClause7);
        System.out.println(probabilityOfClause8);
        System.out.println(probabilityOfClause9);
        System.out.println(probabilityOfClause10);
        System.out.println(probabilityOfClause1.apply(new ProbabilityClause(probabilityOfClause1.getClause())));
        System.out.println(probabilityOfClause2.apply(new ProbabilityClause(probabilityOfClause2.getClause())));
        System.out.println(probabilityOfClause3.apply(new ProbabilityClause(probabilityOfClause3.getClause())));
        System.out.println(probabilityOfClause4.apply(new ProbabilityClause(probabilityOfClause4.getClause())));
        System.out.println(probabilityOfClause5.apply(new ProbabilityClause(probabilityOfClause5.getClause())));
        System.out.println(probabilityOfClause6.apply(new ProbabilityClause(probabilityOfClause6.getClause())));
        System.out.println(probabilityOfClause7.apply(new ProbabilityClause(probabilityOfClause7.getClause())));
        System.out.println(probabilityOfClause8.apply(new ProbabilityClause(probabilityOfClause8.getClause())));
        System.out.println(probabilityOfClause9.apply(new ProbabilityClause(probabilityOfClause9.getClause())));
        System.out.println(probabilityOfClause10.apply(new ProbabilityClause(probabilityOfClause10.getClause())));
        System.out.println(probabilityOfClause1.convertToFormula());
        System.out.println(probabilityOfClause2.convertToFormula());
        System.out.println(probabilityOfClause3.convertToFormula());
        System.out.println(probabilityOfClause4.convertToFormula());
        System.out.println(probabilityOfClause5.convertToFormula());
        System.out.println(probabilityOfClause6.convertToFormula());
        System.out.println(probabilityOfClause7.convertToFormula());
        System.out.println(probabilityOfClause8.convertToFormula());
        System.out.println(probabilityOfClause9.convertToFormula());
        System.out.println(probabilityOfClause10.convertToFormula());

        System.out.println(ProbabilityOfClause.buildProbabilityOfClause(threeSatDisjunctClause).convertToFormula());
    }

    private static void probGiven(ThreeSatDisjunctClause threeSatDisjunctClause1, ThreeSatDisjunctClause threeSatDisjunctClause2) {
        ThreeSatConjunctClause clause = new ThreeSatConjunctClause(threeSatDisjunctClause1, threeSatDisjunctClause2);
        ProbabilityClause probabilityClause = new ProbabilityClause(clause);
        System.out.println(probabilityClause.probabilityOfGiven(threeSatDisjunctClause1, threeSatDisjunctClause2));
    }
}