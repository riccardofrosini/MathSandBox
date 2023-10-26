package ai.maths.sat3.probability;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.Singleton;

public class SimplifyCNF {

    private final CNF<?> cnfOrDisjunctOfSingletonsOrSingleton;
    private final Set<Singleton> givenTrueVariables;
    private final Set<Singleton> lostVariablesNotGivenTrue;

    private SimplifyCNF(CNF<?> cnfOrDisjunctOfSingletonsOrSingleton, Set<Singleton> givenTrueVariables, Set<Singleton> lostVariablesNotGivenTrue) {
        this.cnfOrDisjunctOfSingletonsOrSingleton = cnfOrDisjunctOfSingletonsOrSingleton;
        this.givenTrueVariables = givenTrueVariables;
        this.lostVariablesNotGivenTrue = lostVariablesNotGivenTrue;
    }

    protected static SimplifyCNF simplify(CNF<?> cnf) {//TODO fix
        Set<Singleton> singletons = new HashSet<>(cnf.getSubClauses()
                .filter(disjunct -> disjunct instanceof Singleton)
                .map(disjunct -> (Singleton) disjunct)
                .collect(Collectors.toUnmodifiableSet()));
        Set<Singleton> givenTrueVariables = new HashSet<>();
        while (!singletons.isEmpty()) {
            CNF<?> tempCNF = ClauseBuilder.simplifyCNFWithGivenSingletons(cnf, singletons);
            givenTrueVariables.addAll(singletons);
            singletons.clear();
            singletons.addAll(tempCNF.getSubClauses()
                    .filter(disjunct -> disjunct instanceof Singleton)
                    .map(disjunct -> (Singleton) disjunct)
                    .collect(Collectors.toUnmodifiableSet()));
            cnf = tempCNF;
        }
        return new SimplifyCNF(cnf, givenTrueVariables, Collections.emptySet());
    }

    public CNF<?> getCnfOrDisjunctOfSingletonsOrSingleton() {
        return cnfOrDisjunctOfSingletonsOrSingleton;
    }

    public Set<Singleton> getGivenTrueVariables() {
        return givenTrueVariables;
    }

    public Set<Singleton> getLostVariablesNotGivenTrue() {
        return lostVariablesNotGivenTrue;
    }
}
