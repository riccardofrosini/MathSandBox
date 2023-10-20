package ai.maths.sat3;

import java.util.Set;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.NegVariable;
import ai.maths.sat3.model.Variable;

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

        Clause<?> clause = ClauseBuilder.buildDisjuncts(Set.of(x, y, z));

        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(nx, y, z)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(nx, ny, z)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(nx, ny, nz)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(x, y, c)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(nx, y, c)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(nx, ny, c)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(x, b, c)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(nx, b, c)))));
        System.out.println(ClauseBuilder.buildConjuncts(Set.of(clause, ClauseBuilder.buildDisjuncts(Set.of(a, b, c)))));
    }
}
