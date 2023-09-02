package ai.maths.sat3.model;

import static ai.maths.sat3.model.BooleanConstant.FALSE_CONSTANT;
import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisjunctClause<T extends Clause> implements Clause {

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
    public Clause addConjunct(Clause conjunct) {
        return new DisjunctClause<>(disjuncts.stream()
                .map(t -> new ConjunctClause<>(Stream.of(t, conjunct).collect(Collectors.toSet())).simplify())
                .collect(Collectors.toSet())).simplify();
    }

    public Clause getOtherDisjuncts(T disjunct) {
        HashSet<T> newDisjuncts = new HashSet<>(this.disjuncts);
        newDisjuncts.remove(disjunct);
        return new DisjunctClause<>(newDisjuncts).simplify();
    }

    @Override
    public Clause simplify() {
        if (this.disjuncts.size() == 1) {
            return this.disjuncts.iterator().next();
        }
        Set<Clause> disjuncts = this.disjuncts.stream()
                .filter(t -> !(t instanceof DisjunctClause) && !t.equals(FALSE_CONSTANT))
                .collect(Collectors.toSet());
        disjuncts.addAll(this.disjuncts.stream()
                .filter(t -> t instanceof DisjunctClause)
                .flatMap(t -> ((DisjunctClause<?>) t).disjuncts.stream())
                .collect(Collectors.toSet()));
        Set<NonBoolean> allSingletons = Clause.getAllSingletons(disjuncts);
        if (disjuncts.contains(TRUE_CONSTANT) || Clause.areThereClashingVariables(allSingletons)) {
            return TRUE_CONSTANT;
        }
        if (allSingletons.size() == disjuncts.size()) {
            return new DisjunctOfSingletons(disjuncts.stream()
                    .map(t -> (NonBoolean) t).collect(Collectors.toSet()));
        }
        if (disjuncts.equals(this.disjuncts)) {
            return this;
        }
        return new DisjunctClause<>(disjuncts).simplify();
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
