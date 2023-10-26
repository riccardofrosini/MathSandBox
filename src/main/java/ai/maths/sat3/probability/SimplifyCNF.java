package ai.maths.sat3.probability;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.Singleton;
import ai.maths.sat3.model.Variable;

public class SimplifyCNF {

    private final CNF<?> cnfOrDisjunctOfSingletonsOrSingleton;
    private final Set<Variable> givenTrueVariables;
    private final Set<Variable> lostVariablesNotGivenTrue;

    private SimplifyCNF(CNF<?> cnfOrDisjunctOfSingletonsOrSingleton, Set<Variable> givenTrueVariables, Set<Variable> lostVariablesNotGivenTrue) {
        this.cnfOrDisjunctOfSingletonsOrSingleton = cnfOrDisjunctOfSingletonsOrSingleton;
        this.givenTrueVariables = givenTrueVariables;
        this.lostVariablesNotGivenTrue = lostVariablesNotGivenTrue;
    }

    protected static SimplifyCNF simplify(CNF<?> cnf) {
        Set<Singleton> singletons = new HashSet<>(cnf.getSubClauses()
                .filter(disjunct -> disjunct instanceof Singleton)
                .map(disjunct -> (Singleton) disjunct)
                .collect(Collectors.toUnmodifiableSet()));
        Set<Variable> givenTrueVariables = new HashSet<>();
        Set<Variable> lostVariablesNotGivenTrue = new HashSet<>(cnf.getVariables());
        while (!singletons.isEmpty()) {
            CNF<?> tempCNF = ClauseBuilder.simplifyCNFWithGivenSingletons(cnf, singletons);
            givenTrueVariables.addAll(singletons.stream().flatMap(singleton -> singleton.getVariables().stream()).collect(Collectors.toUnmodifiableSet()));
            singletons.clear();
            singletons.addAll(tempCNF.getSubClauses()
                    .filter(disjunct -> disjunct instanceof Singleton)
                    .map(disjunct -> (Singleton) disjunct)
                    .collect(Collectors.toUnmodifiableSet()));
            cnf = tempCNF;
        }
        lostVariablesNotGivenTrue.removeAll(cnf.getVariables());
        lostVariablesNotGivenTrue.removeAll(givenTrueVariables);
        return new SimplifyCNF(cnf, givenTrueVariables, lostVariablesNotGivenTrue);
    }

    public CNF<?> getCnfOrDisjunctOfSingletonsOrSingleton() {
        return cnfOrDisjunctOfSingletonsOrSingleton;
    }

    public Set<Variable> getGivenTrueVariables() {
        return givenTrueVariables;
    }

    public Set<Variable> getLostVariablesNotGivenTrue() {
        return lostVariablesNotGivenTrue;
    }
}
