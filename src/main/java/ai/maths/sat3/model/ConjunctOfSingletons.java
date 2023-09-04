package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctOfSingletons extends ConjunctOfNonConstants<SingletonVariable> {

    protected ConjunctOfSingletons(Set<SingletonVariable> conjuncts) {
        super(conjuncts);
    }

    @Override
    public SingletonVariableOrConjunctsOfNonConstants simplify() {
        if (conjuncts.size() == 1) {
            return conjuncts.stream().findFirst().get();
        }
        return this;
    }
}
