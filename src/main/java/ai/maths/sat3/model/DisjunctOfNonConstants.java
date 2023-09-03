package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfNonConstants<T extends SingletonVariableOrConjunctsOfNonConstants> extends DisjunctClause<T> implements SingletonVariableOrDisjunctsOfNonConstants {

    protected DisjunctOfNonConstants(Set<T> disjuncts) {
        super(disjuncts);
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons simplify() {
        if (disjuncts.size() == 1) {
            return disjuncts.stream().findFirst().get();
        }
        return this;
    }
}
