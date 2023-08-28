package ai.maths.sat3.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ThreeSatDisjunctClause extends DisjunctClause<SingletonClause> {

    public ThreeSatDisjunctClause(SingletonClause singletonClause1, SingletonClause singletonClause2, SingletonClause singletonClause3) {
        super(Set.of(singletonClause1, singletonClause2, singletonClause3));
    }

    public ThreeSatDisjunctClause(SingletonClause... singletonClauses) {
        super(Set.of(singletonClauses));
    }

    public Clause simplify() {
        Collection<List<SingletonClause>> singletonClausesGrouped = disjuncts.stream()
                .collect(Collectors.groupingBy(SingletonClause::getVariableOrBoolean))
                .values();
        boolean tautology =
                singletonClausesGrouped.stream().map(variableListEntry -> variableListEntry.stream()
                                .map(singletonClause -> singletonClause instanceof NegateVariable)
                                .collect(Collectors.toSet()))
                        .anyMatch(booleans -> booleans.size() > 1);
        if (tautology) {
            return BooleanConstant.TRUE_CONSTANT;
        }
        SingletonClause[] singletonClausesArray = singletonClausesGrouped.stream()
                .map(singletonClauses -> singletonClauses.get(0))
                .toArray(SingletonClause[]::new);
        if (singletonClausesArray.length == 1) {
            return singletonClausesArray[0];
        }
        return new ThreeSatDisjunctClause(singletonClausesArray);
    }
}
