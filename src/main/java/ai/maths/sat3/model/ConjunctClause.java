package ai.maths.sat3.model;

import static ai.maths.sat3.model.BooleanConstant.FALSE_CONSTANT;
import static ai.maths.sat3.model.BooleanConstant.TRUE_CONSTANT;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConjunctClause<T extends Clause> extends Clause {

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

    public Clause getOtherConjuncts(T conjunct) {
        HashSet<T> newConjuncts = new HashSet<>(this.conjuncts);
        newConjuncts.remove(conjunct);
        return new ConjunctClause<>(newConjuncts).simplify();
    }

    @Override
    public Clause addConjunct(Clause conjunct) {
        HashSet<Clause> newConjuncts = new HashSet<>(this.conjuncts);
        newConjuncts.add(conjunct);
        return new ConjunctClause<>(newConjuncts).simplify();
    }

    public Clause makeAsDisjunct() {
        Optional<? extends T> disjunctClauseOptional = conjuncts.stream()
                .filter(t -> t instanceof DisjunctClause).findFirst();
        if (disjunctClauseOptional.isEmpty()) {
            System.out.println("THE CODE SHOULD NEVER EVER ENTER HERE!");
            return this;
        }
        DisjunctClause<?> disjunctClause = (DisjunctClause<?>) disjunctClauseOptional.get();
        Clause otherConjuncts = getOtherConjuncts(disjunctClauseOptional.get());
        return disjunctClause.addConjunct(otherConjuncts);
    }

    @Override
    public Clause simplify() {
        if (this.conjuncts.size() == 1) {
            return this.conjuncts.iterator().next();
        }
        Set<Clause> conjuncts = this.conjuncts.stream()
                .filter(t -> !(t instanceof ConjunctClause) && !t.equals(TRUE_CONSTANT))
                .collect(Collectors.toSet());
        conjuncts.addAll(this.conjuncts.stream()
                .filter(t -> t instanceof ConjunctClause)
                .flatMap(t -> ((ConjunctClause<?>) t).conjuncts.stream())
                .collect(Collectors.toSet()));
        Set<SingletonVariable> allSingletons = Clause.getAllSingletons(conjuncts);
        if (conjuncts.contains(FALSE_CONSTANT) || Clause.areThereClashingVariables(allSingletons)) {
            return FALSE_CONSTANT;
        }
        if (allSingletons.size() == conjuncts.size()) {
            return new ConjunctOfSingletons(conjuncts.stream()
                    .map(t -> (SingletonVariable) t).collect(Collectors.toSet()));
        }
        if (conjuncts.equals(this.conjuncts)) {
            return this;
        }
        return new ConjunctClause<>(conjuncts).simplify();
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
