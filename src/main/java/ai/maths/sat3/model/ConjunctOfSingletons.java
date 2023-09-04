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

    public SingletonOrDisjunctsConjunctsOfNonConstant makeAsDisjunct() {
        System.out.println("THE CODE SHOULD NEVER EVER ENTER HERE!");
        throw new RuntimeException();

    }
}
