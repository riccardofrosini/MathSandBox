package ai.maths.sat3.probability;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.Singleton;

public class SimplifyCNF {

    private final CNF<?> cnfOrDisjunctOfSingletonsOrSingleton;
    private final Set<Singleton> lostVariables;
    private final Set<Singleton> lostVariablesNotGivenAsTrue;

    private SimplifyCNF(CNF<?> cnfOrDisjunctOfSingletonsOrSingleton, Set<Singleton> lostVariables, Set<Singleton> lostVariablesNotGivenAsTrue) {
        this.cnfOrDisjunctOfSingletonsOrSingleton = cnfOrDisjunctOfSingletonsOrSingleton;
        this.lostVariables = lostVariables;
        this.lostVariablesNotGivenAsTrue = lostVariablesNotGivenAsTrue;
    }

    protected static SimplifyCNF simplify(CNF<?> cnf) {//TODO fix
        Set<Singleton> singletons = new HashSet<>(cnf.getSubClauses()
                .filter(disjunct -> disjunct instanceof Singleton)
                .map(disjunct -> (Singleton) disjunct)
                .collect(Collectors.toUnmodifiableSet()));
        Set<Singleton> lostVariables = new HashSet<>();
        Set<Singleton> lostVariablesNotGivenAsTrue = new HashSet<>();
        while (!singletons.isEmpty()) {
            CNF<?> tempCNF = ClauseBuilder.simplifyCNFWithGivenSingletons(cnf, singletons);
            lostVariables.addAll(cnf.getVariables().stream()
                    .filter(variable -> !tempCNF.getVariables().contains(variable))
                    .collect(Collectors.toUnmodifiableSet()));
            lostVariablesNotGivenAsTrue.addAll(cnf.getVariables().stream()
                    .filter(variable -> !tempCNF.getVariables().contains(variable) &&
                            !singletons.contains(variable)).collect(Collectors.toUnmodifiableSet()));
            singletons.clear();
            singletons.addAll(tempCNF.getSubClauses()
                    .filter(disjunct -> disjunct instanceof Singleton)
                    .map(disjunct -> (Singleton) disjunct)
                    .collect(Collectors.toUnmodifiableSet()));
            cnf = tempCNF;
        }
        return new SimplifyCNF(cnf, lostVariables, lostVariablesNotGivenAsTrue);
    }

    public CNF<?> getCnfOrDisjunctOfSingletonsOrSingleton() {
        return cnfOrDisjunctOfSingletonsOrSingleton;
    }

    public Set<Singleton> getLostVariables() {
        return lostVariables;
    }

    public Set<Singleton> getLostVariablesNotGivenAsTrue() {
        return lostVariablesNotGivenAsTrue;
    }
}
