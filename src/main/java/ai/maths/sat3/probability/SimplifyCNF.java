package ai.maths.sat3.probability;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.Singleton;

public class SimplifyCNF {

    private final CNF<?> cnfOrDisjunctOfSingletonsOrSingleton;
    private final Set<Singleton> variables;

    private SimplifyCNF(CNF<?> cnfOrDisjunctOfSingletonsOrSingleton, Set<Singleton> variables) {
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
            CNF<?> tempCNF = ClauseBuilder.simplifyCNFWithGivenSingletons(cnf, singletons);
            singletons.clear();
            singletons.addAll(tempCNF.getSubClauses()
                    .filter(disjunct -> disjunct instanceof Singleton)
                    .map(disjunct -> (Singleton) disjunct)
                    .collect(Collectors.toUnmodifiableSet()));
            variables.addAll(singletons);
            cnf = tempCNF;
        }
        return new SimplifyCNF(cnf, variables);
    }

    public CNF<?> getCnfOrDisjunctOfSingletonsOrSingleton() {
        return cnfOrDisjunctOfSingletonsOrSingleton;
    }

    public Set<Singleton> getVariables() {
        return variables;
    }
}
