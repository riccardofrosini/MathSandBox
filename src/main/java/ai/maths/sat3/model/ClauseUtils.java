package ai.maths.sat3.model;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClauseUtils {

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
            if (clause.equals(Disjuncts.TRUE)) {
                return Conjuncts.FALSE;
            }
            return buildConjuncts(clause.getSubClauses()
                    .map(ClauseUtils::buildNegation)
                    .collect(Collectors.toSet()));
        }
        if (clause instanceof Conjuncts) {
            if (clause.equals(Conjuncts.FALSE)) {
                return Disjuncts.TRUE;
            }
            return buildDisjuncts(clause.getSubClauses()
                    .map(ClauseUtils::buildNegation)
                    .collect(Collectors.toSet()));
        }
        return new Negation<>(clause);
    }

    public static Clause<?> buildConjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseConjuncts(clauses);
        if (clauses.isEmpty() || clauses.contains(Conjuncts.FALSE) || checkClashingClauses(clauses)) {
            return Conjuncts.FALSE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof Singleton)) {
            Iterator<Singleton> iterator = clauses.stream().map(clause -> (Singleton) clause).collect(Collectors.toSet()).iterator();
            if (clauses.size() == 3) {
                return new Conjuncts3(iterator.next(), iterator.next(), iterator.next());
            }
            if (clauses.size() == 2) {
                return new Conjuncts2(iterator.next(), iterator.next());
            }
            return new ConjunctsOfSingletons(clauses.stream().map(clause -> (Singleton) clause).collect(Collectors.toSet()));
        }
        if (clauses.stream().allMatch(clause -> clause instanceof ThreeDisjunctOfSingletonsOrSingleton)) {
            return new ThreeSatConjuncts(clauses.stream()
                    .map(clause -> (ThreeDisjunctOfSingletonsOrSingleton<?>) clause)
                    .collect(Collectors.toSet()));
        }
        return new Conjuncts<>(clauses);
    }

    public static Clause<?> buildDisjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseDisjuncts(clauses);
        if (clauses.isEmpty() || clauses.contains(Disjuncts.TRUE) || checkClashingClauses(clauses)) {
            return Disjuncts.TRUE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof Singleton)) {
            Iterator<Singleton> iterator = clauses.stream().map(clause -> (Singleton) clause).collect(Collectors.toSet()).iterator();
            if (clauses.size() == 3) {
                return new Disjuncts3(iterator.next(), iterator.next(), iterator.next());
            }
            if (clauses.size() == 2) {
                return new Disjuncts2(iterator.next(), iterator.next());
            }
            return new DisjunctsOfSingletons(clauses.stream().map(clause -> (Singleton) clause).collect(Collectors.toSet()));
        }
        if (clauses.stream().allMatch(clause -> clause instanceof ThreeConjunctOfSingletonsOrSingleton)) {
            return new NegThreeSatDisjuncts(clauses.stream()
                    .map(clause -> (ThreeConjunctOfSingletonsOrSingleton<?>) clause)
                    .collect(Collectors.toSet()));
        }
        return new Disjuncts<>(clauses);
    }

    private static Set<Clause<?>> normaliseDisjuncts(Set<Clause<?>> clauses) {
        Set<Clause<?>> disjunctsNormalised = clauses.stream()
                .filter(clause -> clause instanceof Disjuncts)
                .map(clause -> (Disjuncts<?>) clause)
                .flatMap(Disjuncts::getSubClauses)
                .collect(Collectors.toSet());
        Set<Clause<?>> rest = clauses.stream()
                .filter(clause -> !(clause instanceof Disjuncts) && !clause.equals(Conjuncts.FALSE))
                .collect(Collectors.toSet());
        disjunctsNormalised.addAll(rest);
        return disjunctsNormalised;
    }

    private static Set<Clause<?>> normaliseConjuncts(Set<Clause<?>> clauses) {
        Set<Clause<?>> conjunctsNormalised = clauses.stream()
                .filter(clause -> clause instanceof Conjuncts)
                .map(clause -> (Conjuncts<?>) clause)
                .flatMap(Conjuncts::getSubClauses)
                .collect(Collectors.toSet());
        Set<Clause<?>> rest = clauses.stream()
                .filter(clause -> !(clause instanceof Conjuncts) && !clause.equals(Disjuncts.TRUE))
                .collect(Collectors.toSet());
        conjunctsNormalised.addAll(rest);
        return conjunctsNormalised;
    }

    private static boolean checkClashingClauses(Set<Clause<?>> clauses) {
        return clauses.stream().anyMatch(clause -> clauses.contains(buildNegation(clause)));
    }

    public static Set<Variable> getAllVariables(Clause<?> clause) {
        return getAllVariablesStream(clause).collect(Collectors.toSet());
    }

    private static Stream<Variable> getAllVariablesStream(Clause<?> clause) {
        if (clause instanceof Variable) {
            return ((Variable) clause).getSubClauses();
        }
        return clause.getSubClauses().flatMap(Clause::getSubClauses)
                .flatMap(ClauseUtils::getAllVariablesStream);
    }
}
