package ai.maths.sat3;

import java.util.HashSet;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.NegVariable;
import ai.maths.sat3.model.Variable;
import ai.maths.sat3.probability.Probability;
import ai.maths.sat3.probability.SolutionCounter;

public class Main {

    public static void main(String[] args) {
        HashSet<CNF<?>> cnfs = new HashSet<>();
        HashSet<Variable> variables = new HashSet<>();
        for (int varNumbers = 0; varNumbers < 100; varNumbers++) {
            for (int i = 0; i < 3; i++) {
                variables.add(ClauseBuilder.buildVariable("x" + (3 * varNumbers + i)));
            }
            HashSet<DisjunctOfSingletonsOrSingleton> disjuncts = new HashSet<>(14);
            for (Variable variable1 : variables) {
                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1));
                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1)));
                for (Variable variable2 : variables) {
                    if (variable1 != variable2) {
                        disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, variable2));
                        disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), variable2));
                        disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, ClauseBuilder.buildNegationOfSingleton(variable2)));
                        disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), ClauseBuilder.buildNegationOfSingleton(variable2)));
                        for (Variable variable3 : variables) {
                            if (variable1 != variable3 && variable2 != variable3) {
                                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, variable2, variable3));
                                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), variable2, variable3));
                                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, ClauseBuilder.buildNegationOfSingleton(variable2), variable3));
                                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, variable2, ClauseBuilder.buildNegationOfSingleton(variable3)));
                                disjuncts.add(
                                        ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), ClauseBuilder.buildNegationOfSingleton(variable2), variable3));
                                disjuncts.add(
                                        ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), variable2, ClauseBuilder.buildNegationOfSingleton(variable3)));
                                disjuncts.add(
                                        ClauseBuilder.buildDisjunctsOfSingletons(variable1, ClauseBuilder.buildNegationOfSingleton(variable2), ClauseBuilder.buildNegationOfSingleton(variable3)));
                                disjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), ClauseBuilder.buildNegationOfSingleton(variable2),
                                        ClauseBuilder.buildNegationOfSingleton(variable3)));
                            }
                        }
                    }
                }
            }

            for (DisjunctOfSingletonsOrSingleton disjunct : disjuncts) {
                HashSet<CNF<?>> newCnfs = new HashSet<>();
                if (cnfs.isEmpty()) {
                    CNF<?> cnf = ClauseBuilder.buildCNF(disjunct);
                    newCnfs.add(cnf);
                } else {
                    for (CNF<?> cnf : cnfs) {
                        CNF<?> newCnf;
                        if (cnf instanceof DisjunctOfSingletonsOrSingleton) {
                            newCnf = ClauseBuilder.buildCNF((DisjunctOfSingletonsOrSingleton) cnf, disjunct);
                        } else {
                            newCnf = ClauseBuilder.buildCNF(disjunct, cnf.getSubClauses().collect(Collectors.toSet()).toArray(DisjunctOfSingletonsOrSingleton[]::new));
                        }
                        newCnfs.add(newCnf);
                    }
                }
                newCnfs.stream().filter(cnf -> !cnfs.contains(cnf)).forEach(cnf -> {
                    System.out.println(cnf);
                    System.out.println(Probability.probability(cnf));
                    System.out.println(SolutionCounter.countSolutions(cnf));
                });
                cnfs.addAll(newCnfs);
            }
        }
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
