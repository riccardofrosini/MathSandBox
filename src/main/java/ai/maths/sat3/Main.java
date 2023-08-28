package ai.maths.sat3;

import ai.maths.sat3.bayesian.ProbabilityClause;
import ai.maths.sat3.model.NegateVariable;
import ai.maths.sat3.model.ThreeSatDisjunctClause;
import ai.maths.sat3.model.Variable;

public class Main {

    public static void main(String[] args) {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");
        NegateVariable nx = new NegateVariable(x);
        ThreeSatDisjunctClause clause = new ThreeSatDisjunctClause(x, y, z);
        ProbabilityClause probabilityClause = new ProbabilityClause(clause);
        System.out.println(probabilityClause.probabilityOfDisjunction(clause));
    }
}