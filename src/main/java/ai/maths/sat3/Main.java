package ai.maths.sat3;

import java.util.HashSet;

import ai.maths.sat3.model.probability.ProbabilityFormulaOfCNF;
import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.Variable;
import ai.maths.sat3.probability.ConnectedVariables;
import ai.maths.sat3.probability.Probability;
import ai.maths.sat3.probability.ProbabilityFormula;
import ai.maths.sat3.probability.SolutionCounter;

public class Main {

    public static void main(String[] args) {
        HashSet<CNF<?>> CNFs = new HashSet<>();
        HashSet<Variable> variables = new HashSet<>();
        HashSet<CNF<?>> previous = new HashSet<>();
        for (int varNumbers = 0; varNumbers < 100; varNumbers++) {
            variables.add(ClauseBuilder.buildVariable("x" + varNumbers));
            HashSet<DisjunctOfSingletonsOrSingleton> newDisjuncts = new HashSet<>();
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
            System.out.println(newDisjuncts.size() + " " + previous.size());
            if (previous.isEmpty()) {
                for (DisjunctOfSingletonsOrSingleton disjunct : newDisjuncts) {
                    CNF<?> cnf = ClauseBuilder.buildCNF(disjunct);
                    if (!CNFs.contains(cnf) && !(cnf instanceof ConjunctOfSingletonsOrSingleton) && cnf instanceof DisjunctOfSingletonsOrSingleton) {
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
                            newCNF = ClauseBuilder.buildCNF(disjunct, cnf.getSubClauses().toArray(DisjunctOfSingletonsOrSingleton[]::new));
                        }
                        if (!CNFs.contains(newCNF) && !(newCNF instanceof ConjunctOfSingletonsOrSingleton) &&
                                (newCNF instanceof DisjunctOfSingletonsOrSingleton || ConnectedVariables.getIndependentConnectedConjuncts(newCNF).size() == 1)) {
                            newCNFs.add(newCNF);
                        }
                    }
                }
            }
            previous.clear();
            newCNFs.forEach(cnf -> {
                double probability = Probability.probabilityOfCNF(cnf);
                if (probability != 0) {
                    System.out.println(cnf);
                    previous.add(cnf);
                } else {
                    ProbabilityFormulaOfCNF formulaOfCNF = ProbabilityFormula.getFormulaOfCNF(cnf);
                    System.out.println(cnf);
                    System.out.println(formulaOfCNF);
                    System.out.println(probability + " " + SolutionCounter.countSolutionsOfCNF(cnf));
                    probability = formulaOfCNF.getProbability();
                    System.out.println(probability + " " + probability * Math.pow(2, cnf.getVariables().size()));
                }
                CNFs.add(cnf);
            });
        }
    }
}
