package ai.maths.sat3;

import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.NegVariable;
import ai.maths.sat3.model.Variable;
import ai.maths.sat3.probability.Probability;
import ai.maths.sat3.probability.SolutionCounter;

public class Main {

    public static void main(String[] args) {
        Variable x = ClauseBuilder.buildVariable("x");
        Variable y = ClauseBuilder.buildVariable("y");
        Variable z = ClauseBuilder.buildVariable("z");
        Variable a = ClauseBuilder.buildVariable("a");
        Variable b = ClauseBuilder.buildVariable("b");
        Variable c = ClauseBuilder.buildVariable("c");

        NegVariable nx = (NegVariable) ClauseBuilder.buildNegationOfSingleton(x);
        NegVariable ny = (NegVariable) ClauseBuilder.buildNegationOfSingleton(y);
        NegVariable nz = (NegVariable) ClauseBuilder.buildNegationOfSingleton(z);

        DisjunctOfSingletonsOrSingleton clause = ClauseBuilder.buildDisjunctsOfSingletons(x, y, z);

        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, clause)));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(x, y, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(x, b, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(a, b, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, y, z))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, ny, z))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, ny, nz))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, y, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, ny, c))));
        System.out.println(Probability.probability(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, b, c))));

        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, clause)));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(x, y, c))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(x, b, c))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(a, b, c))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, y, z))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, ny, z))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, ny, nz))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, y, c))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, ny, c))));
        System.out.println(SolutionCounter.countSolutions(ClauseBuilder.buildCNF(clause, ClauseBuilder.buildDisjunctsOfSingletons(nx, b, c))));
    }
}
