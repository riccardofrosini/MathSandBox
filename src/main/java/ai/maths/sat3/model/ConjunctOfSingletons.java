package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctOfSingletons extends ConjunctOfNonConstants<SingletonVariable> {

    protected ConjunctOfSingletons(Set<SingletonVariable> conjuncts) {
        super(conjuncts);
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons simplify() {
        return this;
    }
}
