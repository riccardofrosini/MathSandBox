package ai.maths.sat3.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ConjunctOfNonConstants<T extends SingletonVariableOrDisjunctsOfNonConstants> extends ConjunctClause<T> implements SingletonVariableOrConjunctsOfNonConstants {

    protected ConjunctOfNonConstants(Set<T> disjuncts) {
        super(disjuncts);
    }

    public SingletonVariableOrDisjunctsConjunctsOfNonConstant getOtherConjuncts(T conjunct) {
        HashSet<T> newConjuncts = new HashSet<>(conjuncts);
        newConjuncts.remove(conjunct);
        return new ConjunctOfNonConstants<>(newConjuncts).simplify();
    }

    public SingletonOrDisjunctsConjunctsOfNonConstant makeAsDisjunct() {
        Optional<T> disjunctClauseOptional = conjuncts.stream()
                .filter(clause -> clause instanceof DisjunctOfNonConstants)
                .findFirst();
        if (disjunctClauseOptional.isEmpty()) {
            System.out.println("THE CODE SHOULD NEVER EVER ENTER HERE!");
            throw new RuntimeException();
        }
        DisjunctOfNonConstants<?> disjunctClause = (DisjunctOfNonConstants<?>) disjunctClauseOptional.get();
        SingletonOrDisjunctsConjunctsOfNonConstant otherConjuncts = getOtherConjuncts(disjunctClauseOptional.get());
        return disjunctClause.addConjunct(otherConjuncts);
    }

    @Override
    public SingletonOrDisjunctsConjunctsOfNonConstant addConjunct(SingletonOrDisjunctsConjunctsOfNonConstant conjunct) {
        HashSet<SingletonOrDisjunctsConjunctsOfNonConstant> newConjuncts = new HashSet<>(conjuncts);
        newConjuncts.add(conjunct);
        return new ConjunctClause<>(newConjuncts).simplify();
    }

    @Override
    public SingletonVariableOrDisjunctsConjunctsOfNonConstant simplify() {
        if (conjuncts.size() == 1) {
            return conjuncts.stream().findFirst().get();
        }
        return this;
    }
}
