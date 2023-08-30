package ai.maths.sat3.model;

import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisjunctClause<T extends Clause> implements Clause {

    protected final Set<T> disjuncts;

    protected DisjunctClause(Set<T> disjuncts) {
        this.disjuncts = disjuncts;
    }

    public Stream<T> getDisjunctsStream() {
        return disjuncts.stream();
    }

    @Override
    public Set<VariableOrBoolean> getAllVariablesAndConstants() {
        return disjuncts.stream()
                .flatMap(t -> t.getAllVariablesAndConstants().stream())
                .collect(Collectors.toSet());
    }

    public DisjunctClause<ConjunctClause<?>> addConjunct(Clause conjunct) {
        return new DisjunctClause<>(disjuncts.stream()
                .map(t -> new ConjunctClause<>(Set.of(t, conjunct)))
                .collect(Collectors.toSet()));
    }

    public DisjunctClause<T> getOtherDisjuncts(T disjunct) {
        HashSet<T> newDisjuncts = new HashSet<>(this.disjuncts);
        newDisjuncts.remove(disjunct);
        return new DisjunctClause<T>(newDisjuncts);
    }

    @Override
    public Clause simplify() {
        if (this.disjuncts.size() == 1) {
            return this.disjuncts.iterator().next().simplify();
        }
        Set<Clause> disjuncts = this.disjuncts.stream()
                .filter(t -> !(t instanceof DisjunctClause))
                .collect(Collectors.toSet());
        disjuncts.addAll(this.disjuncts.stream()
                .filter(t -> t instanceof DisjunctClause)
                .flatMap(t -> ((DisjunctClause<?>) t).disjuncts.stream())
                .collect(Collectors.toSet()));

        Set<SingletonClause<?>> allSingletons = Clause.getAllSingletons(disjuncts);
        if (allSingletons.contains(TRUE_CONSTANT) || Clause.areThereClashingVariables(allSingletons)) {
            return TRUE_CONSTANT;
        }
        if (allSingletons.size() == disjuncts.size()) {
            return new DisjunctOfSingletons(disjuncts.stream()
                    .map(t -> (SingletonClause<?>) t).toArray(SingletonClause<?>[]::new));
        }
        if (disjuncts.size() == this.disjuncts.size()) {
            return this;
        }
        return new ConjunctClause<>(disjuncts);
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
