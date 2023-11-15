package ai.maths.sat3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import ai.maths.sat3.model.graph.Graph;
import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.Variable;
import ai.maths.sat3.probability.ConnectedVariables;
import ai.maths.sat3.probability.Probability;

public class Main {

    public static void main(String[] args) {
        HashSet<CNF<?>> CNFs = new HashSet<>();
        HashMap<Graph, HashSet<CNF<?>>> graphs = new HashMap<>();
        HashSet<Variable> variables = new HashSet<>();
        HashSet<CNF<?>> previous = new HashSet<>();
        for (int varNumbers = 0; varNumbers < 5; varNumbers++) {
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
                    previous.add(cnf);
                }
                //System.out.println(cnf);
                //ProbabilityFormulaOfCNF formulaOfCNF = ProbabilityFormula.getFormulaOfCNF(cnf);
                //System.out.println(formulaOfCNF + " " + probability + " " + SolutionCounter.countSolutionsOfCNF(cnf));
                graphs.computeIfAbsent(Graph.buildGraph(cnf), graph -> new HashSet<>()).add(cnf);
                CNFs.add(cnf);
            });

            graphs.entrySet().stream().filter(graphHashSetEntry -> graphHashSetEntry.getValue().stream()
                            .map(Probability::probabilityOfCNF)
                            .collect(Collectors.groupingBy(o -> o)).size() > 1)
                    .forEach(hashSetEntry ->
                            System.out.println(hashSetEntry.getKey()
                                    + "\n CNFs: " + hashSetEntry.getValue().stream()
                                    .map(cnf -> {
                                        double probability = Probability.probabilityOfCNF(cnf);
                                        return cnf + " " + probability + " " + (1L << cnf.getVariables().size()) * probability;
                                    }).collect(Collectors.joining("\n\t", "\n\t", ""))));
        }
    }
}
