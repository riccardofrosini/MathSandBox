package ai.maths.sat3.model;

import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisjunctClause<T extends SingletonOrDisjunctsConjunctsOfNonConstant> implements Clause {

    protected final Set<T> disjuncts;

    protected DisjunctClause(Set<T> disjuncts) {
        this.disjuncts = Collections.unmodifiableSet(disjuncts);
    }

    public Stream<T> getDisjunctsStream() {
        return disjuncts.stream();
    }

    @Override
    public Set<Variable> getAllVariables() {
        return disjuncts.stream()
                .flatMap(t -> t.getAllVariables().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public SingletonOrDisjunctsConjunctsOfNonConstant simplify() {
        if (disjuncts.size() == 1) {
            return disjuncts.stream().findFirst().get();
        }
        Set<SingletonVariableOrConjunctsOfNonConstants> singletonOrConjunctsSet = disjuncts.stream()
                .filter(clause -> clause instanceof SingletonVariableOrConjunctsOfNonConstants)
                .map(clause -> (SingletonVariableOrConjunctsOfNonConstants) clause)
                .collect(Collectors.toSet());
        singletonOrConjunctsSet.addAll(disjuncts.stream()
                .filter(clause -> clause instanceof DisjunctOfNonConstants)
                .flatMap(clause -> ((DisjunctOfNonConstants<?>) clause).disjuncts.stream())
                .map(clause -> (SingletonVariableOrConjunctsOfNonConstants) clause)
                .collect(Collectors.toSet()));
        Set<SingletonVariable> allSingletons = Clause.getAllSingletons(singletonOrConjunctsSet);
        if (disjuncts.contains(TRUE_CONSTANT) || Clause.areThereClashingVariables(allSingletons)) {
            return TRUE_CONSTANT;
        }
        if (allSingletons.size() == singletonOrConjunctsSet.size()) {
            return new DisjunctOfSingletons(singletonOrConjunctsSet.stream()
                    .map(t -> (SingletonVariable) t).collect(Collectors.toSet())).simplify();
        }
        return new DisjunctOfNonConstants<>(singletonOrConjunctsSet).simplify();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisjunctClause<?> that = (DisjunctClause<?>) o;
        return disjuncts.equals(that.disjuncts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disjuncts);
    }

    @Override
    public String toString() {
        return disjuncts.stream().map(Object::toString).collect(Collectors.joining("∨", "(", ")"));
    }
}
