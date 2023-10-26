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
        HashSet<CNF<?>> CNFs = new HashSet<>();
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

            HashSet<CNF<?>> newCNFs = new HashSet<>();
            if (previous.isEmpty()) {
                for (DisjunctOfSingletonsOrSingleton disjunct : newDisjuncts) {
                    CNF<?> cnf = ClauseBuilder.buildCNF(disjunct);
                    if (!(cnf instanceof ConjunctOfSingletonsOrSingleton) && (cnf instanceof DisjunctOfSingletonsOrSingleton || ConnectedVariables.getIndependentConnectedConjuncts(cnf).size() == 1)) {
                        newCNFs.add(cnf);
                    }
                }
            } else {
                for (DisjunctOfSingletonsOrSingleton disjunct : newDisjuncts) {
                    for (CNF<?> cnf : previous) {
                        CNF<?> newCNF;
                        if (cnf instanceof DisjunctOfSingletonsOrSingleton) {
                            newCNF = ClauseBuilder.buildCNF((DisjunctOfSingletonsOrSingleton) cnf, disjunct);
                        } else {
                            newCNF = ClauseBuilder.buildCNF(disjunct, cnf.getSubClauses().collect(Collectors.toSet()).toArray(DisjunctOfSingletonsOrSingleton[]::new));
                        }
                        if (!(newCNF instanceof ConjunctOfSingletonsOrSingleton) && (newCNF instanceof DisjunctOfSingletonsOrSingleton
                                || ConnectedVariables.getIndependentConnectedConjuncts(newCNF).size() == 1)) {
                            newCNFs.add(newCNF);
                        }
                    }
                }
            }
            previous.clear();
            newCNFs.stream().filter(cnf -> !CNFs.contains(cnf)).forEach(cnf -> {
                double probability = Probability.probability(cnf);
                long countSolutions = SolutionCounter.countSolutions(cnf);
                System.out.println(cnf + " " + probability + " " + countSolutions + " " + ((double) countSolutions / probability));
                previous.add(cnf);
                CNFs.add(cnf);
            });
        }
    }
}
