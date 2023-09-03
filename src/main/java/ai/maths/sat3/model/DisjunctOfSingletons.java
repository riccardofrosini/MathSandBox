package ai.maths.sat3.model;

import java.util.Set;

public class DisjunctOfSingletons extends DisjunctOfNonConstants<SingletonVariable> {

    protected DisjunctOfSingletons(Set<SingletonVariable> disjuncts) {
        super(disjuncts);
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons simplify() {
        return this;
    }
}
