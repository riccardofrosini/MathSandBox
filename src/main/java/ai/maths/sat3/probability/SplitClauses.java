package ai.maths.sat3.probability;

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

    private SplitClauses(DisjunctOfSingletonsOrSingleton first, CNF<?> cnf) {
        this.first = first;
        this.rest = ClauseBuilder.buildCNF(cnf.getSubClauses().filter(t -> t != first).collect(Collectors.toUnmodifiableSet()));
        this.disconnectedFromFirst = buildDisconnectedFromFirst();
    }

    protected static SplitClauses split(CNF<?> cnf) {
        DisjunctOfSingletonsOrSingleton anySubClause = cnf.getAnySubClause();
        return cnf.getSubClauses()
                .map(disjunct -> new SplitClauses(disjunct, cnf))
                .filter(splitClauses -> splitClauses.disconnectedFromFirst instanceof ConjunctOfSingletons ||
                        splitClauses.disconnectedFromFirst instanceof DisjunctOfSingletons ||
                        ConnectedVariables.getIndependentConnectedConjuncts(splitClauses.disconnectedFromFirst).size() == 1)
                .findAny()
                .orElse(new SplitClauses(anySubClause, cnf));
    }

    private CNF<?> buildDisconnectedFromFirst() {
        return ClauseBuilder.simplifyCNFWithGivenSingletons(rest, ClauseBuilder.buildNegationOfDisjunctOfSingletons(first).getSingletons());
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
