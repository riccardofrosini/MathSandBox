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
            if (clause == DisjunctsOfSingletons.FALSE) {
                return ConjunctsOfSingletons.TRUE;
            }
            return buildConjuncts(clause.getSubClauses()
                    .map(ClauseBuilder::buildNegation)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        if (clause instanceof Conjuncts) {
            if (clause == ConjunctsOfSingletons.TRUE) {
                return DisjunctsOfSingletons.FALSE;
            }
            return buildDisjuncts(clause.getSubClauses()
                    .map(ClauseBuilder::buildNegation)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    private static Clause<?> buildConjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseConjuncts(clauses);
        if (clauses.contains(DisjunctsOfSingletons.FALSE) || checkClashingClauses(clauses)) {
            return DisjunctsOfSingletons.FALSE;
        }
        if (clauses.isEmpty()) {
            return ConjunctsOfSingletons.TRUE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof DisjunctOfSingletonsOrSingleton)) {
            return buildCNF(clauses.stream()
                    .map(clause -> (DisjunctOfSingletonsOrSingleton) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new Conjuncts<>(clauses);
    }

    private static Clause<?> buildDisjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseDisjuncts(clauses);
        if (clauses.contains(ConjunctsOfSingletons.TRUE) || checkClashingClauses(clauses)) {
            return ConjunctsOfSingletons.TRUE;
        }
        if (clauses.isEmpty()) {
            return DisjunctsOfSingletons.FALSE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof ConjunctOfSingletonsOrSingleton)) {
            return buildDNF(clauses.stream()
                    .map(clause -> (ConjunctOfSingletonsOrSingleton) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new Disjuncts<>(clauses);
    }

    public static DNFOrConjunctOfSingletonsOrSingleton<?> buildDNF(ConjunctOfSingletonsOrSingleton first, ConjunctOfSingletonsOrSingleton... rest) {
        return buildDNF(Stream.concat(Stream.of(first), Stream.of(rest)).collect(Collectors.toUnmodifiableSet()));
    }

    public static DNFOrConjunctOfSingletonsOrSingleton<?> buildDNF(Set<ConjunctOfSingletonsOrSingleton> conjuncts) {
        if (conjuncts.size() == 1) {
            return conjuncts.iterator().next();
        }
        if (conjuncts.stream().allMatch(clause -> clause instanceof Singleton)) {
            return buildDisjunctsOfSingletons(conjuncts.stream().map(singleton -> (Singleton) singleton).collect(Collectors.toUnmodifiableSet()));
        }
        if (conjuncts.stream().allMatch(clause -> clause instanceof ThreeConjunctOfSingletonsOrSingleton)) {
            return new ThreeSatDisjuncts(conjuncts.stream()
                    .map(clause -> (ThreeConjunctOfSingletonsOrSingleton) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new TwoSatDisjuncts(conjuncts.stream()
                .map(clause -> (TwoConjunctOfSingletonsOrSingleton) clause)
                .collect(Collectors.toUnmodifiableSet()));
    }

    public static DisjunctOfSingletonsOrSingleton buildDisjunctsOfSingletons(Singleton first, Singleton... rest) {
        return buildDisjunctsOfSingletons(Stream.concat(Stream.of(first), Arrays.stream(rest)).collect(Collectors.toUnmodifiableSet()));
    }

    public static DisjunctOfSingletonsOrSingleton buildDisjunctsOfSingletons(Set<Singleton> singletons) {
        Iterator<Singleton> iterator = singletons.iterator();
        if (!iterator.hasNext()) {
            return DisjunctsOfSingletons.FALSE;
        }
        if (singletons.size() == 1) {
            return iterator.next();
        }
        if (singletons.size() == 2) {
            return new Disjuncts2(iterator.next(), iterator.next());
        }
        if (singletons.size() == 3) {
            return new Disjuncts3(iterator.next(), iterator.next(), iterator.next());
        }
        return new DisjunctsOfSingletons(singletons);
    }

    public static CNFOrDisjunctOfSingletonsOrSingleton<?> buildCNF(DisjunctOfSingletonsOrSingleton first, DisjunctOfSingletonsOrSingleton... disjuncts) {
        return buildCNF(Stream.concat(Stream.of(first), Stream.of(disjuncts)).collect(Collectors.toUnmodifiableSet()));
    }

    public static CNFOrDisjunctOfSingletonsOrSingleton<?> buildCNF(Set<DisjunctOfSingletonsOrSingleton> disjuncts) {
        if (disjuncts.size() == 1) {
            return disjuncts.iterator().next();
        }
        if (disjuncts.stream().allMatch(clause -> clause instanceof Singleton)) {
            return buildConjunctsOfSingletons(disjuncts.stream()
                    .map(singleton -> (Singleton) singleton)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        if (disjuncts.stream().allMatch(clause -> clause instanceof ThreeDisjunctOfSingletonsOrSingleton)) {
            return new ThreeSatConjuncts(disjuncts.stream()
                    .map(clause -> (ThreeDisjunctOfSingletonsOrSingleton) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        return new TwoSatConjuncts(disjuncts.stream()
                .map(clause -> (TwoDisjunctOfSingletonsOrSingleton) clause)
                .collect(Collectors.toUnmodifiableSet()));
    }

    public static ConjunctOfSingletonsOrSingleton buildConjunctsOfSingletons(Singleton first, Singleton... rest) {
        return buildConjunctsOfSingletons(Stream.concat(Stream.of(first), Arrays.stream(rest)).collect(Collectors.toUnmodifiableSet()));
    }

    public static ConjunctOfSingletonsOrSingleton buildConjunctsOfSingletons(Set<Singleton> singletons) {
        Iterator<Singleton> iterator = singletons.iterator();
        if (!iterator.hasNext()) {
            return ConjunctsOfSingletons.TRUE;
        }
        if (singletons.size() == 1) {
            return iterator.next();
        }
        if (singletons.size() == 2) {
            return new Conjuncts2(iterator.next(), iterator.next());
        }
        if (singletons.size() == 3) {
            return new Conjuncts3(iterator.next(), iterator.next(), iterator.next());
        }
        return new ConjunctsOfSingletons(singletons);
    }

    private static Set<Clause<?>> normaliseDisjuncts(Set<Clause<?>> clauses) {
        return Stream.concat(clauses.stream()
                                .filter(clause -> clause instanceof Disjuncts && clause != DisjunctsOfSingletons.FALSE)
                                .map(clause -> (Disjuncts<?>) clause)
                                .flatMap(Disjuncts::getSubClauses),
                        clauses.stream()
                                .filter(clause -> !(clause instanceof Disjuncts)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<Clause<?>> normaliseConjuncts(Set<Clause<?>> clauses) {
        return Stream.concat(clauses.stream()
                                .filter(clause -> clause instanceof Conjuncts && clause != ConjunctsOfSingletons.TRUE)
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
