package ai.maths.sat3.model;

import java.util.Set;

public class ConjunctOfNonConstants<T extends SingletonVariableOrDisjunctsOfNonConstants> extends ConjunctClause<T> implements SingletonVariableOrConjunctsOfNonConstants {

    protected ConjunctOfNonConstants(Set<T> disjuncts) {
        super(disjuncts);
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons simplify() {
        if (conjuncts.size() == 1) {
            return conjuncts.stream().findFirst().get();
        }
        return this;
    }
}
