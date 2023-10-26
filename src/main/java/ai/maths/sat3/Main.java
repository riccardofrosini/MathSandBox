package ai.maths.sat3;

import java.util.HashSet;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.Variable;
import ai.maths.sat3.probability.ConnectedVariables;
import ai.maths.sat3.probability.Probability;
import ai.maths.sat3.probability.SolutionCounter;

public class Main {

    public static void main(String[] args) {
        HashSet<CNF<?>> cnfs = new HashSet<>();
        HashSet<Variable> variables = new HashSet<>();
        HashSet<CNF<?>> previous = new HashSet<>();
        for (int varNumbers = 0; varNumbers < 100; varNumbers++) {
            variables.add(ClauseBuilder.buildVariable("x" + varNumbers));
            HashSet<DisjunctOfSingletonsOrSingleton> newDisjuncts = new HashSet<>(14);
            for (Variable variable1 : variables) {
                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1));
                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1)));
                for (Variable variable2 : variables) {
                    if (variable1 != variable2) {
                        newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, variable2));
                        newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), variable2));
                        newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, ClauseBuilder.buildNegationOfSingleton(variable2)));
                        newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), ClauseBuilder.buildNegationOfSingleton(variable2)));
                        for (Variable variable3 : variables) {
                            if (variable1 != variable3 && variable2 != variable3) {
                                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, variable2, variable3));
                                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), variable2, variable3));
                                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, ClauseBuilder.buildNegationOfSingleton(variable2), variable3));
                                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(variable1, variable2, ClauseBuilder.buildNegationOfSingleton(variable3)));
                                newDisjuncts.add(
                                        ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), ClauseBuilder.buildNegationOfSingleton(variable2), variable3));
                                newDisjuncts.add(
                                        ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), variable2, ClauseBuilder.buildNegationOfSingleton(variable3)));
                                newDisjuncts.add(
                                        ClauseBuilder.buildDisjunctsOfSingletons(variable1, ClauseBuilder.buildNegationOfSingleton(variable2), ClauseBuilder.buildNegationOfSingleton(variable3)));
                                newDisjuncts.add(ClauseBuilder.buildDisjunctsOfSingletons(ClauseBuilder.buildNegationOfSingleton(variable1), ClauseBuilder.buildNegationOfSingleton(variable2),
                                        ClauseBuilder.buildNegationOfSingleton(variable3)));
                            }
                        }
                    }
                }
            }

            HashSet<CNF<?>> newCnfs = new HashSet<>();
            if (previous.isEmpty()) {
                for (DisjunctOfSingletonsOrSingleton disjunct : newDisjuncts) {
                    CNF<?> cnf = ClauseBuilder.buildCNF(disjunct);
                    if (!(cnf instanceof ConjunctOfSingletonsOrSingleton) && (cnf instanceof DisjunctOfSingletonsOrSingleton || ConnectedVariables.getIndependentConnectedConjuncts(cnf).size() == 1)) {
                        newCnfs.add(cnf);
                    }
                }
            } else {
                for (DisjunctOfSingletonsOrSingleton disjunct : newDisjuncts) {
                    for (CNF<?> cnf : previous) {
                        CNF<?> newCnf;
                        if (cnf instanceof DisjunctOfSingletonsOrSingleton) {
                            newCnf = ClauseBuilder.buildCNF((DisjunctOfSingletonsOrSingleton) cnf, disjunct);
                        } else {
                            newCnf = ClauseBuilder.buildCNF(disjunct, cnf.getSubClauses().collect(Collectors.toSet()).toArray(DisjunctOfSingletonsOrSingleton[]::new));
                        }
                        if (!(newCnf instanceof ConjunctOfSingletonsOrSingleton) && (newCnf instanceof DisjunctOfSingletonsOrSingleton
                                || ConnectedVariables.getIndependentConnectedConjuncts(newCnf).size() == 1)) {
                            newCnfs.add(newCnf);
                        }
                    }
                }
            }
            previous.clear();
            newCnfs.stream().filter(cnf -> !cnfs.contains(cnf)).forEach(cnf -> {
                double probability = Probability.probability(cnf);
                long countSolutions = SolutionCounter.countSolutions(cnf);
                System.out.println(cnf + " " + probability + " " + countSolutions + " " + ((double) countSolutions / probability));
                previous.add(cnf);
                cnfs.add(cnf);
            });
        }
    }
}
