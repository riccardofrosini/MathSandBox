package ai.maths.sat3.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisjunctOfNonConstants<T extends SingletonVariableOrConjunctsOfNonConstants> extends DisjunctClause<T> implements SingletonVariableOrDisjunctsOfNonConstants {

    protected DisjunctOfNonConstants(Set<T> disjuncts) {
        super(disjuncts);
    }

    public SingletonVariableOrDisjunctsConjunctsOfNonConstant getOtherDisjuncts(T disjunct) {
        HashSet<T> newDisjuncts = new HashSet<>(disjuncts);
        newDisjuncts.remove(disjunct);
        return new DisjunctOfNonConstants<>(newDisjuncts).simplify();
    }

    @Override
    public SingletonOrDisjunctsConjunctsOfNonConstant addConjunct(SingletonOrDisjunctsConjunctsOfNonConstant conjunct) {
        return new DisjunctClause<>(disjuncts.stream()
                .map(clause -> new ConjunctClause<>(Stream.of(clause, conjunct).collect(Collectors.toSet())).simplify())
                .collect(Collectors.toSet())).simplify();
    }

    @Override
    public SingletonVariableOrDisjunctsConjunctsOfNonConstant simplify() {
        if (disjuncts.size() == 1) {
            return disjuncts.stream().findFirst().get();
        }
        return this;
    }
}
