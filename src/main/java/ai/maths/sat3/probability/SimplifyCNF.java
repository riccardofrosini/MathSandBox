package ai.maths.sat3.probability;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.sat3.CNF;
import ai.maths.sat3.model.sat3.ClauseBuilder;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.Singleton;
import ai.maths.sat3.model.sat3.Variable;

public class SimplifyCNF {

    private final CNF<?> simplifiedCnf;
    private final Set<Variable> givenVariables;
    private final Set<Variable> lostVariablesNotGiven;
    private final ConjunctOfSingletonsOrSingleton givenSingletons;

    private SimplifyCNF(CNF<?> simplifiedCnf, Set<Variable> givenVariables, Set<Variable> lostVariablesNotGiven, ConjunctOfSingletonsOrSingleton givenSingletons) {
        this.simplifiedCnf = simplifiedCnf;
        this.givenVariables = givenVariables;
        this.lostVariablesNotGiven = lostVariablesNotGiven;
        this.givenSingletons = givenSingletons;
    }

    protected static SimplifyCNF simplify(CNF<?> cnf) {
        Set<Singleton> singletons = new HashSet<>(cnf.getSubClauses()
                .filter(disjunct -> disjunct instanceof Singleton)
                .map(disjunct -> (Singleton) disjunct)
                .collect(Collectors.toUnmodifiableSet()));
        Set<Variable> givenTrueVariables = new HashSet<>();
        Set<Variable> lostVariablesNotGivenTrue = new HashSet<>(cnf.getVariables());
        Set<Singleton> allGivenSingletons = new HashSet<>();
        while (!singletons.isEmpty()) {
            allGivenSingletons.addAll(singletons);
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
        return new SimplifyCNF(cnf, givenTrueVariables, lostVariablesNotGivenTrue, ClauseBuilder.buildConjunctsOfSingletons(allGivenSingletons));
    }

    public CNF<?> getSimplifiedCnf() {
        return simplifiedCnf;
    }

    public Set<Variable> getGivenVariables() {
        return givenVariables;
    }

    public Set<Variable> getLostVariablesNotGiven() {
        return lostVariablesNotGiven;
    }

    public ConjunctOfSingletonsOrSingleton getGivenSingletons() {
        return givenSingletons;
    }
}
