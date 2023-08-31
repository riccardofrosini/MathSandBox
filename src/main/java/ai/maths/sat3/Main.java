package ai.maths.sat3;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.lazyalgebraic.ProbabilityOfClauseLazy;
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
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(x, y, z));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(x, y, u));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(x, u, v));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(u, v, w));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, y, z));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, ny, z));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, ny, nz));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, y, u));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, ny, u));
        probGiven(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, u, v));

        ProbabilityOfClauseLazy<?> probabilityOfClause1 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(x, y, z));
        ProbabilityOfClauseLazy<?> probabilityOfClause2 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(x, y, u));
        ProbabilityOfClauseLazy<?> probabilityOfClause3 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(x, u, v));
        ProbabilityOfClauseLazy<?> probabilityOfClause4 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(u, v, w));
        ProbabilityOfClauseLazy<?> probabilityOfClause5 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, y, z));
        ProbabilityOfClauseLazy<?> probabilityOfClause6 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, ny, z));
        ProbabilityOfClauseLazy<?> probabilityOfClause7 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, ny, nz));
        ProbabilityOfClauseLazy<?> probabilityOfClause8 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, y, u));
        ProbabilityOfClauseLazy<?> probabilityOfClause9 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, ny, u));
        ProbabilityOfClauseLazy<?> probabilityOfClause10 = ProbabilityOfClauseLazy.probabilityOfIntersection(new ThreeSatDisjunctClause(x, y, z), new ThreeSatDisjunctClause(nx, u, v));
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
    }

    private static void probGiven(ThreeSatDisjunctClause threeSatDisjunctClause1, ThreeSatDisjunctClause threeSatDisjunctClause2) {
        ThreeSatConjunctClause clause = new ThreeSatConjunctClause(threeSatDisjunctClause1, threeSatDisjunctClause2);
        ProbabilityClause probabilityClause = new ProbabilityClause(clause);
        System.out.println(probabilityClause.probabilityOfGiven(threeSatDisjunctClause1, threeSatDisjunctClause2));
    }
}