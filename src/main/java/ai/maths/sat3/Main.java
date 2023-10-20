package ai.maths.sat3;

import java.util.Set;

import ai.maths.sat3.model.Clause;
import ai.maths.sat3.model.ClauseUtils;
import ai.maths.sat3.model.NegVariable;
import ai.maths.sat3.model.Variable;

public class Main {

    public static void main(String[] args) {
        Variable x = ClauseUtils.buildVariable("x");
        Variable y = ClauseUtils.buildVariable("y");
        Variable z = ClauseUtils.buildVariable("z");
        Variable a = ClauseUtils.buildVariable("a");
        Variable b = ClauseUtils.buildVariable("b");
        Variable c = ClauseUtils.buildVariable("c");

        NegVariable nx = (NegVariable) ClauseUtils.buildNegation(x);
        NegVariable ny = (NegVariable) ClauseUtils.buildNegation(y);
        NegVariable nz = (NegVariable) ClauseUtils.buildNegation(z);

        Clause<?> clause = ClauseUtils.buildDisjuncts(Set.of(x, y, z));

        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(nx, y, z)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(nx, ny, z)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(nx, ny, nz)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(x, y, c)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(nx, y, c)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(nx, ny, c)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(x, b, c)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(nx, b, c)))));
        System.out.println(ClauseUtils.buildConjuncts(Set.of(clause, ClauseUtils.buildDisjuncts(Set.of(a, b, c)))));
    }
}
