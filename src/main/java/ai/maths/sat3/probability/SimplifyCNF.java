package ai.maths.sat3.probability;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.CNFOrDisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.DisjunctsOfSingletons;
import ai.maths.sat3.model.Singleton;

public class SimplifyCNF {

    private final CNFOrDisjunctOfSingletonsOrSingleton<?> cnfOrDisjunctOfSingletonsOrSingleton;
    private final Set<Singleton> variables;

    private SimplifyCNF(CNFOrDisjunctOfSingletonsOrSingleton<?> cnfOrDisjunctOfSingletonsOrSingleton, Set<Singleton> variables) {
        this.cnfOrDisjunctOfSingletonsOrSingleton = cnfOrDisjunctOfSingletonsOrSingleton;
        this.variables = variables;
    }

    public static SimplifyCNF simplify(CNF<?> cnf) {
        Set<Singleton> singletons = cnf.getSubClauses()
                .filter(disjunct -> disjunct instanceof Singleton)
                .map(disjunct -> (Singleton) disjunct)
                .collect(Collectors.toSet());
        Set<Singleton> variables = new HashSet<>(singletons);
        while (!singletons.isEmpty()) {
            Set<DisjunctOfSingletonsOrSingleton> disjuncts = cnf.getSubClauses()
                    .filter(disjunct -> !singletons.contains(disjunct) &&
                            disjunct.getSingletons().stream().noneMatch(singletons::contains))
                    .map(disjunct -> disjunct.getSingletons().stream().anyMatch(singleton -> singletons.contains((Singleton) ClauseBuilder.buildNegation(singleton))) ?
                            ClauseBuilder.buildDisjunctsOfSingletons(disjunct.getSingletons().stream()
                                    .filter(singleton -> singletons.contains((Singleton) ClauseBuilder.buildNegation(singleton)))
                                    .collect(Collectors.toUnmodifiableSet())) : disjunct)
                    .collect(Collectors.toUnmodifiableSet());
            if (disjuncts.stream().anyMatch(disjunct -> disjunct == DisjunctsOfSingletons.FALSE)) {
                return new SimplifyCNF(DisjunctsOfSingletons.FALSE, variables);
            }
            CNFOrDisjunctOfSingletonsOrSingleton<?> disjunctOfSingletonsOrSingleton = ClauseBuilder.buildCNF(disjuncts);
            if (disjunctOfSingletonsOrSingleton instanceof CNF) {
                singletons.clear();
                singletons.addAll(cnf.getSubClauses()
                        .filter(disjunct -> disjunct instanceof Singleton)
                        .map(disjunct -> (Singleton) disjunct)
                        .collect(Collectors.toSet()));
                variables.addAll(singletons);
            } else {
                return new SimplifyCNF(disjunctOfSingletonsOrSingleton, variables);
            }
        }
        return new SimplifyCNF(cnf, variables);
    }

    public CNFOrDisjunctOfSingletonsOrSingleton<?> getCnfOrDisjunctOfSingletonsOrSingleton() {
        return cnfOrDisjunctOfSingletonsOrSingleton;
    }

    public Set<Singleton> getVariables() {
        return variables;
    }
}
