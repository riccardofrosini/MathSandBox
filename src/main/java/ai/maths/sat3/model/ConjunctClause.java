package ai.maths.sat3.model;

import static ai.maths.sat3.model.BooleanConstant.FALSE_CONSTANT;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConjunctClause<T extends DisjunctsConjunctsOfNonConstantAndSingletons> implements Clause {

    protected final Set<T> conjuncts;

    protected ConjunctClause(Set<T> conjuncts) {
        this.conjuncts = Collections.unmodifiableSet(conjuncts);
    }

    public Stream<T> getConjunctsStream() {
        return conjuncts.stream();
    }

    @Override
    public Set<Variable> getAllVariables() {
        return conjuncts.stream()
                .flatMap(t -> t.getAllVariables().stream())
                .collect(Collectors.toSet());
    }

    public DisjunctsConjunctsOfNonConstantAndSingletons getOtherConjuncts(T conjunct) {
        HashSet<T> newConjuncts = new HashSet<>(this.conjuncts);
        newConjuncts.remove(conjunct);
        return new ConjunctClause<>(newConjuncts).simplify();
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons addConjunct(DisjunctsConjunctsOfNonConstantAndSingletons conjunct) {
        HashSet<DisjunctsConjunctsOfNonConstantAndSingletons> newConjuncts = new HashSet<>(this.conjuncts);
        newConjuncts.add(conjunct);
        return new ConjunctClause<>(newConjuncts).simplify();
    }

    public DisjunctsConjunctsOfNonConstantAndSingletons makeAsDisjunct() {
        Optional<T> disjunctClauseOptional = conjuncts.stream()
                .filter(clause -> clause instanceof DisjunctClause)
                .findFirst();
        if (disjunctClauseOptional.isEmpty()) {
            System.out.println("THE CODE SHOULD NEVER EVER ENTER HERE!");
            throw new RuntimeException();
        }
        DisjunctClause<?> disjunctClause = (DisjunctClause<?>) disjunctClauseOptional.get();
        DisjunctsConjunctsOfNonConstantAndSingletons otherConjuncts = getOtherConjuncts(disjunctClauseOptional.get());
        return disjunctClause.addConjunct(otherConjuncts);
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons simplify() {
        if (conjuncts.size() == 1) {
            return conjuncts.iterator().next();
        }
        Set<SingletonVariableOrDisjunctsOfNonConstants> singletonOrDisjunctsSet = conjuncts.stream()
                .filter(clause -> clause instanceof SingletonVariableOrDisjunctsOfNonConstants)
                .map(clause -> (SingletonVariableOrDisjunctsOfNonConstants) clause)
                .collect(Collectors.toSet());
        singletonOrDisjunctsSet.addAll(conjuncts.stream()
                .filter(clause -> clause instanceof ConjunctClause)
                .flatMap(clause -> ((ConjunctClause<?>) clause).conjuncts.stream())
                .map(clause -> (SingletonVariableOrDisjunctsOfNonConstants) clause)
                .collect(Collectors.toSet()));
        Set<SingletonVariable> allSingletons = Clause.getAllSingletons(singletonOrDisjunctsSet);
        if (conjuncts.contains(FALSE_CONSTANT) || Clause.areThereClashingVariables(allSingletons)) {
            return FALSE_CONSTANT;
        }
        if (allSingletons.size() == singletonOrDisjunctsSet.size()) {
            return new ConjunctOfSingletons(singletonOrDisjunctsSet.stream()
                    .map(t -> (SingletonVariable) t).collect(Collectors.toSet()));
        }
        return new ConjunctOfNonConstants<>(singletonOrDisjunctsSet).simplify();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConjunctClause<?> that = (ConjunctClause<?>) o;
        return conjuncts.equals(that.conjuncts);
    }

    @Override
    public int hashCode() {
        return -Objects.hash(conjuncts);
    }

    @Override
    public String toString() {
        return conjuncts.stream().map(Object::toString).collect(Collectors.joining("∧", "(", ")"));
    }
}
