package ai.maths.sat3;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.NegVariable;
import ai.maths.sat3.model.Variable;
import ai.maths.sat3.probability.Probability;

public class Main {

    public static void main(String[] args) {
        Variable x = ClauseBuilder.buildVariable("x");
        Variable y = ClauseBuilder.buildVariable("y");
        Variable z = ClauseBuilder.buildVariable("z");
        Variable a = ClauseBuilder.buildVariable("a");
        Variable b = ClauseBuilder.buildVariable("b");
        Variable c = ClauseBuilder.buildVariable("c");

        NegVariable nx = (NegVariable) ClauseBuilder.buildNegation(x);
        NegVariable ny = (NegVariable) ClauseBuilder.buildNegation(y);
        NegVariable nz = (NegVariable) ClauseBuilder.buildNegation(z);

        Clause<?> clause = ClauseBuilder.buildDisjuncts(x, y, z);

        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(x, y, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(x, b, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(a, b, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(nx, y, z))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(nx, ny, z))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(nx, ny, nz))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(nx, y, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(nx, ny, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildConjuncts(clause, ClauseBuilder.buildDisjuncts(nx, b, c))));
    }
}
