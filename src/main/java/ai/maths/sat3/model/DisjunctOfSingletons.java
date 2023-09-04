package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfSingletons extends DisjunctOfNonConstants<SingletonVariable> implements SingletonVariableOrDisjunctsOfSingletons {

    protected DisjunctOfSingletons(Set<SingletonVariable> disjuncts) {
        super(disjuncts);
    }

    @Override
    public SingletonVariableOrDisjunctsOfSingletons simplify() {
        if (disjuncts.size() == 1) {
            return disjuncts.stream().findFirst().get();
        }
        return this;
    }
}
