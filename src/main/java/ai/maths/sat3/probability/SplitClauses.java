package ai.maths.sat3.probability;

import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.CNF;
import ai.maths.sat3.model.ClauseBuilder;
import ai.maths.sat3.model.ConjunctOfSingletons;
import ai.maths.sat3.model.DisjunctOfSingletons;
import ai.maths.sat3.model.DisjunctOfSingletonsOrSingleton;

public class SplitClauses {

    private final DisjunctOfSingletonsOrSingleton first;
    private final CNF<?> rest;
    private final CNF<?> disconnectedFromFirst;

    private SplitClauses(DisjunctOfSingletonsOrSingleton first, Set<DisjunctOfSingletonsOrSingleton> rest) {
        this.first = first;
        this.rest = ClauseBuilder.buildCNF(rest);
        this.disconnectedFromFirst = buildDisconnectedFromFirst();
    }

    protected static SplitClauses split(CNF<?> conjuncts) {
        DisjunctOfSingletonsOrSingleton anySubClause = conjuncts.getAnySubClause();
        return conjuncts.getSubClauses()
                .map(disjunct -> new SplitClauses(disjunct, conjuncts.getSubClauses().filter(t -> t != disjunct).collect(Collectors.toUnmodifiableSet())))
                .filter(splitClauses -> splitClauses.disconnectedFromFirst instanceof ConjunctOfSingletons ||
                        splitClauses.disconnectedFromFirst instanceof DisjunctOfSingletons ||
                        ConnectedVariables.getIndependentConnectedConjuncts(splitClauses.disconnectedFromFirst).size() == 1)
                .findAny()
                .orElse(new SplitClauses(anySubClause, conjuncts.getSubClauses().filter(t -> t != anySubClause).collect(Collectors.toUnmodifiableSet())));
    }

    private CNF<?> buildDisconnectedFromFirst() {
        return ClauseBuilder.buildNegationOfDNF(
                ClauseBuilder.simplifyDNFWithGivenSingletons(ClauseBuilder.buildNegationOfCNF(rest), ClauseBuilder.buildNegationOfDisjunctOfSingletons(first).getSingletons()));
    }

    public CNF<?> getDisconnectedFromFirst() {
        return disconnectedFromFirst;
    }

    public DisjunctOfSingletonsOrSingleton getFirst() {
        return first;
    }

    public CNF<?> getRest() {
        return rest;
    }
}
