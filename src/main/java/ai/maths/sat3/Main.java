package ai.maths.sat3;

import ai.maths.sat3.bayesian.ProbabilityClause;
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
    }

    private static void probGiven(ThreeSatDisjunctClause threeSatDisjunctClause1, ThreeSatDisjunctClause threeSatDisjunctClause2) {
        ThreeSatConjunctClause clause = new ThreeSatConjunctClause(threeSatDisjunctClause1, threeSatDisjunctClause2);
        ProbabilityClause probabilityClause = new ProbabilityClause(clause);
        System.out.println(probabilityClause.probabilityOfGiven(threeSatDisjunctClause1, threeSatDisjunctClause2));
    }
}