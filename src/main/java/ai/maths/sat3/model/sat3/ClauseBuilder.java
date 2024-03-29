package ai.maths.sat3.model.sat3;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClauseBuilder {

    public static Variable buildVariable(String var) {
        return new Variable(var);
    }

    private static Clause<?> buildNegation(Clause<?> clause) {
        if (clause instanceof Singleton) {
            return buildNegationOfSingleton((Singleton) clause);
        }
        if (clause instanceof Disjuncts) {
            if (clause == DisjunctOfSingletons.FALSE) {
                return ConjunctOfSingletons.TRUE;
            }
            return buildConjuncts(clause.getSubClauses()
                    .map(ClauseBuilder::buildNegation)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        if (clause instanceof Conjuncts) {
            if (clause == ConjunctOfSingletons.TRUE) {
                return DisjunctOfSingletons.FALSE;
            }
            return buildDisjuncts(clause.getSubClauses()
                    .map(ClauseBuilder::buildNegation)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    private static Clause<?> buildConjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseConjuncts(clauses);
        if (clauses.contains(DisjunctOfSingletons.FALSE) || checkClashingClauses(clauses)) {
            return DisjunctOfSingletons.FALSE;
        }
        if (clauses.isEmpty()) {
            return ConjunctOfSingletons.TRUE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof DisjunctOfSingletonsOrSingleton)) {
            return buildCNF(clauses.stream()
                    .map(clause -> (DisjunctOfSingletonsOrSingleton) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    private static Clause<?> buildDisjuncts(Set<Clause<?>> clauses) {
        clauses = normaliseDisjuncts(clauses);
        if (clauses.contains(ConjunctOfSingletons.TRUE) || checkClashingClauses(clauses)) {
            return ConjunctOfSingletons.TRUE;
        }
        if (clauses.isEmpty()) {
            return DisjunctOfSingletons.FALSE;
        }
        if (clauses.size() == 1) {
            return clauses.iterator().next();
        }
        if (clauses.stream().allMatch(clause -> clause instanceof ConjunctOfSingletonsOrSingleton)) {
            return buildDNF(clauses.stream()
                    .map(clause -> (ConjunctOfSingletonsOrSingleton) clause)
                    .collect(Collectors.toUnmodifiableSet()));
        }
        throw new RuntimeException("A new class that extends clause has been added but not handled!");
    }

    public static Singleton buildNegationOfSingleton(Singleton singleton) {
        if (singleton instanceof Variable) {
            return new NegVariable((Variable) singleton);
        }
        return ((NegVariable) singleton).getNegatedClause();
    }

    public static DisjunctOfSingletonsOrSingleton buildNegationOfConjunctsOfSingletons(ConjunctOfSingletonsOrSingleton conjunctOfSingletonsOrSingleton) {
        if (conjunctOfSingletonsOrSingleton == ConjunctOfSingletons.TRUE) {
            return DisjunctOfSingletons.FALSE;
        }
        if (conjunctOfSingletonsOrSingleton instanceof Singleton) {
            return buildNegationOfSingleton((Singleton) conjunctOfSingletonsOrSingleton);
        }
        return buildDisjunctsOfSingletons(conjunctOfSingletonsOrSingleton.getSingletons().stream().map(ClauseBuilder::buildNegationOfSingleton).collect(Collectors.toUnmodifiableSet()));
    }

    public static ConjunctOfSingletonsOrSingleton buildNegationOfDisjunctOfSingletons(DisjunctOfSingletonsOrSingleton disjunctOfSingletonsOrSingleton) {
        if (disjunctOfSingletonsOrSingleton == DisjunctOfSingletons.FALSE) {
            return ConjunctOfSingletons.TRUE;
        }
        if (disjunctOfSingletonsOrSingleton instanceof Singleton) {
            return buildNegationOfSingleton((Singleton) disjunctOfSingletonsOrSingleton);
        }
        return buildConjunctsOfSingletons(disjunctOfSingletonsOrSingleton.getSingletons().stream().map(ClauseBuilder::buildNegationOfSingleton).collect(Collectors.toUnmodifiableSet()));
    }

    public static DNF<?> buildNegationOfCNF(CNF<?> cnf) {
        if (cnf instanceof DisjunctOfSingletonsOrSingleton) {
            return buildNegationOfDisjunctOfSingletons((DisjunctOfSingletonsOrSingleton) cnf);
        }
        if (cnf instanceof ConjunctOfSingletonsOrSingleton) {
            return buildNegationOfConjunctsOfSingletons((ConjunctOfSingletonsOrSingleton) cnf);
        }
        return buildDNF(cnf.getSubClauses().map(ClauseBuilder::buildNegationOfDisjunctOfSingletons).collect(Collectors.toUnmodifiableSet()));
    }

    public static CNF<?> buildNegationOfDNF(DNF<?> dnf) {
        if (dnf instanceof DisjunctOfSingletonsOrSingleton) {
            return buildNegationOfDisjunctOfSingletons((DisjunctOfSingletonsOrSingleton) dnf);
        }
        if (dnf instanceof ConjunctOfSingletonsOrSingleton) {
            return buildNegationOfConjunctsOfSingletons((ConjunctOfSingletonsOrSingleton) dnf);
        }
        return buildCNF(dnf.getSubClauses().map(ClauseBuilder::buildNegationOfConjunctsOfSingletons).collect(Collectors.toUnmodifiableSet()));
    }

    public static CNF<?> buildCNF(DisjunctOfSingletonsOrSingleton first, DisjunctOfSingletonsOrSingleton... disjuncts) {
        return buildCNF(Stream.concat(Stream.of(first), Stream.of(disjuncts)).collect(Collectors.toUnmodifiableSet()));
    }

    public static CNF<?> buildCNF(Set<DisjunctOfSingletonsOrSingleton> disjuncts) {
        if (disjuncts.contains(DisjunctOfSingletons.FALSE) || checkClashingClauses(disjuncts)) {
            return DisjunctOfSingletons.FALSE;
        }
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

    public static DNF<?> buildDNF(ConjunctOfSingletonsOrSingleton first, ConjunctOfSingletonsOrSingleton... conjuncts) {
        return buildDNF(Stream.concat(Stream.of(first), Stream.of(conjuncts)).collect(Collectors.toUnmodifiableSet()));
    }

    public static DNF<?> buildDNF(Set<ConjunctOfSingletonsOrSingleton> conjuncts) {
        if (conjuncts.contains(ConjunctOfSingletons.TRUE) || checkClashingClauses(conjuncts)) {
            return ConjunctOfSingletons.TRUE;
        }
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

    public static ConjunctOfSingletonsOrSingleton buildConjunctsOfSingletons(Singleton... singletons) {
        return buildConjunctsOfSingletons(Set.of(singletons));
    }

    public static ConjunctOfSingletonsOrSingleton buildConjunctsOfSingletons(Set<Singleton> singletons) {
        Iterator<Singleton> iterator = singletons.iterator();
        if (!iterator.hasNext()) {
            return ConjunctOfSingletons.TRUE;
        }
        if (singletons.size() == 1) {
            return iterator.next();
        }
        if (singletons.size() == 2) {
            return new Conjunct2(iterator.next(), iterator.next());
        }
        if (singletons.size() == 3) {
            return new Conjunct3(iterator.next(), iterator.next(), iterator.next());
        }
        return new ConjunctOfSingletons(singletons);
    }

    public static DisjunctOfSingletonsOrSingleton buildDisjunctsOfSingletons(Singleton... singletons) {
        return buildDisjunctsOfSingletons(Set.of(singletons));
    }

    public static DisjunctOfSingletonsOrSingleton buildDisjunctsOfSingletons(Set<Singleton> singletons) {
        Iterator<Singleton> iterator = singletons.iterator();
        if (!iterator.hasNext()) {
            return DisjunctOfSingletons.FALSE;
        }
        if (singletons.size() == 1) {
            return iterator.next();
        }
        if (singletons.size() == 2) {
            return new Disjunct2(iterator.next(), iterator.next());
        }
        if (singletons.size() == 3) {
            return new Disjunct3(iterator.next(), iterator.next(), iterator.next());
        }
        return new DisjunctOfSingletons(singletons);
    }

    private static Set<Clause<?>> normaliseDisjuncts(Set<Clause<?>> clauses) {
        return Stream.concat(clauses.stream()
                                .filter(clause -> clause instanceof Disjuncts && clause != DisjunctOfSingletons.FALSE)
                                .map(clause -> (Disjuncts<?>) clause)
                                .flatMap(Disjuncts::getSubClauses),
                        clauses.stream()
                                .filter(clause -> !(clause instanceof Disjuncts)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<Clause<?>> normaliseConjuncts(Set<Clause<?>> clauses) {
        return Stream.concat(clauses.stream()
                                .filter(clause -> clause instanceof Conjuncts && clause != ConjunctOfSingletons.TRUE)
                                .map(clause -> (Conjuncts<?>) clause)
                                .flatMap(Conjuncts::getSubClauses),
                        clauses.stream()
                                .filter(clause -> !(clause instanceof Conjuncts)))
                .collect(Collectors.toUnmodifiableSet());
    }

    public static CNF<?> simplifyCNFWithGivenSingletons(CNF<?> cnf, Set<Singleton> singletons) {
        if (cnf instanceof ConjunctOfSingletonsOrSingleton) {
            return simplifyConjunctOfSingletonsWithGivenSingletons((ConjunctOfSingletonsOrSingleton) cnf, singletons);
        }
        if (cnf instanceof DisjunctOfSingletonsOrSingleton) {
            return simplifyDisjunctOfSingletonsWithGivenSingletons((DisjunctOfSingletonsOrSingleton) cnf, singletons);
        }
        Set<ClauseOfSingletonOrSingleton> disjuncts = cnf.getSubClauses()
                .map(disjunctOfSingletonsOrSingleton -> simplifyDisjunctOfSingletonsWithGivenSingletons(disjunctOfSingletonsOrSingleton, singletons))
                .filter(clauseOfSingletonOrSingleton -> clauseOfSingletonOrSingleton != ConjunctOfSingletons.TRUE)
                .collect(Collectors.toUnmodifiableSet());
        return buildCNF(disjuncts.stream().map(clauseOfSingletonOrSingleton -> (DisjunctOfSingletonsOrSingleton) clauseOfSingletonOrSingleton).collect(Collectors.toUnmodifiableSet()));
    }

    public static DNF<?> simplifyDNFWithGivenSingletons(DNF<?> dnf, Set<Singleton> singletons) {
        if (dnf instanceof ConjunctOfSingletonsOrSingleton) {
            return simplifyConjunctOfSingletonsWithGivenSingletons((ConjunctOfSingletonsOrSingleton) dnf, singletons);
        }
        if (dnf instanceof DisjunctOfSingletonsOrSingleton) {
            return simplifyDisjunctOfSingletonsWithGivenSingletons((DisjunctOfSingletonsOrSingleton) dnf, singletons);
        }
        Set<ClauseOfSingletonOrSingleton> conjuncts = dnf.getSubClauses()
                .map(conjunctOfSingletonsOrSingleton -> simplifyConjunctOfSingletonsWithGivenSingletons(conjunctOfSingletonsOrSingleton, singletons))
                .filter(clauseOfSingletonOrSingleton -> clauseOfSingletonOrSingleton != DisjunctOfSingletons.FALSE)
                .collect(Collectors.toUnmodifiableSet());
        return buildDNF(conjuncts.stream().map(clauseOfSingletonOrSingleton -> (ConjunctOfSingletonsOrSingleton) clauseOfSingletonOrSingleton).collect(Collectors.toUnmodifiableSet()));
    }

    private static ClauseOfSingletonOrSingleton simplifyConjunctOfSingletonsWithGivenSingletons(ConjunctOfSingletonsOrSingleton conjunctsOfSingletons, Set<Singleton> singletons) {
        if (singletons.containsAll(conjunctsOfSingletons.getSingletons())) {
            return ConjunctOfSingletons.TRUE;
        }
        if (conjunctsOfSingletons.getSingletons().stream().anyMatch(singleton -> singletons.contains(buildNegationOfSingleton(singleton)))) {
            return DisjunctOfSingletons.FALSE;
        }
        return buildConjunctsOfSingletons(conjunctsOfSingletons.getSingletons().stream()
                .filter(singleton -> !singletons.contains(singleton))
                .collect(Collectors.toUnmodifiableSet()));
    }

    private static ClauseOfSingletonOrSingleton simplifyDisjunctOfSingletonsWithGivenSingletons(DisjunctOfSingletonsOrSingleton disjunctsOfSingletons, Set<Singleton> singletons) {
        if (disjunctsOfSingletons.getSingletons().stream().anyMatch(singletons::contains)) {
            return ConjunctOfSingletons.TRUE;
        }
        if (disjunctsOfSingletons.getSingletons().stream().allMatch(singleton -> singletons.contains(buildNegationOfSingleton(singleton)))) {
            return DisjunctOfSingletons.FALSE;
        }
        return buildDisjunctsOfSingletons(disjunctsOfSingletons.getSingletons().stream()
                .filter(singleton -> !singletons.contains(buildNegationOfSingleton(singleton)))
                .collect(Collectors.toUnmodifiableSet()));
    }

    private static <T extends Clause<?>> boolean checkClashingClauses(Set<T> clauses) {
        return clauses.stream().anyMatch(clause -> clauses.contains(buildNegation(clause)));
    }
}
