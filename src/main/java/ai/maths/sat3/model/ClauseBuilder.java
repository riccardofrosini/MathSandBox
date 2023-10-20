package ai.maths.sat3.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClauseBuilder {

    public static Variable buildVariable(String var) {
        return new Variable(var);
    }

    public static Clause<?> buildNegation(Clause<?> clause) {
        if (clause instanceof Variable) {
            return new NegVariable((Variable) clause);
        }
        if (clause instanceof Negation) {
            return clause.getAnySubClause();
        }
        if (clause instanceof Disjuncts) {
            if (clause == Disjuncts.FALSE) {
                return Conjuncts.TRUE;
            }
            return buildConjuncts(clause.getSubClauses()
                    .map(ClauseBuilder::buildNegation)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        if (clause instanceof Conjuncts) {
            if (clause == Conjuncts.TRUE) {
                return Disjuncts.FALSE;
            }
            return buildDisjuncts(clause.getSubClauses()
                    .map(ClauseBuilder::buildNegation)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new Negation<>(clause);
    }

    public static Clause<?> buildConjuncts(Clause<?>... clauses) {
        return buildConjuncts(Arrays.stream(clauses).collect(Collectors.toUnmodifiableSet()));
    }

    private static Clause<?> buildConjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseConjuncts(clauses);
        if (clauses.isEmpty() || clauses.contains(Disjuncts.FALSE) || checkClashingClauses(clauses)) {
            return Disjuncts.FALSE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof Singleton)) {
            Iterator<Singleton> iterator = clauses.stream().map(clause -> (Singleton) clause).iterator();
            if (clauses.size() == 3) {
                return new Conjuncts3(iterator.next(), iterator.next(), iterator.next());
            }
            if (clauses.size() == 2) {
                return new Conjuncts2(iterator.next(), iterator.next());
            }
            return new ConjunctsOfSingletons(clauses.stream().map(clause -> (Singleton) clause).collect(Collectors.toUnmodifiableSet()));
        }
        if (clauses.stream().allMatch(clause -> clause instanceof ThreeDisjunctOfSingletonsOrSingleton)) {
            return new ThreeSatConjuncts(clauses.stream()
                    .map(clause -> (ThreeDisjunctOfSingletonsOrSingleton<?>) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new Conjuncts<>(clauses);
    }

    public static Clause<?> buildDisjuncts(Clause<?>... clauses) {
        return buildDisjuncts(Arrays.stream(clauses).collect(Collectors.toUnmodifiableSet()));
    }

    private static Clause<?> buildDisjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseDisjuncts(clauses);
        if (clauses.isEmpty() || clauses.contains(Conjuncts.TRUE) || checkClashingClauses(clauses)) {
            return Conjuncts.TRUE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof Singleton)) {
            Iterator<Singleton> iterator = clauses.stream().map(clause -> (Singleton) clause).iterator();
            if (clauses.size() == 3) {
                return new Disjuncts3(iterator.next(), iterator.next(), iterator.next());
            }
            if (clauses.size() == 2) {
                return new Disjuncts2(iterator.next(), iterator.next());
            }
            return new DisjunctsOfSingletons(clauses.stream().map(clause -> (Singleton) clause).collect(Collectors.toUnmodifiableSet()));
        }
        if (clauses.stream().allMatch(clause -> clause instanceof ThreeConjunctOfSingletonsOrSingleton)) {
            return new NegThreeSatDisjuncts(clauses.stream()
                    .map(clause -> (ThreeConjunctOfSingletonsOrSingleton<?>) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new Disjuncts<>(clauses);
    }

    private static Set<Clause<?>> normaliseDisjuncts(Set<Clause<?>> clauses) {
        return Stream.concat(clauses.stream()
                                .filter(clause -> clause instanceof Disjuncts && clause != Disjuncts.FALSE)
                                .map(clause -> (Disjuncts<?>) clause)
                                .flatMap(Disjuncts::getSubClauses),
                        clauses.stream()
                                .filter(clause -> !(clause instanceof Disjuncts)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<Clause<?>> normaliseConjuncts(Set<Clause<?>> clauses) {
        return Stream.concat(clauses.stream()
                                .filter(clause -> clause instanceof Conjuncts && clause != Conjuncts.TRUE)
                                .map(clause -> (Conjuncts<?>) clause)
                                .flatMap(Conjuncts::getSubClauses),
                        clauses.stream()
                                .filter(clause -> !(clause instanceof Conjuncts)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static boolean checkClashingClauses(Set<Clause<?>> clauses) {
        return clauses.stream().anyMatch(clause -> clauses.contains(buildNegation(clause)));
    }
}
