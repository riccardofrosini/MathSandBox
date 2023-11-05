package ai.maths.sat3.model.probability;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.sat3.model.sat3.ConjunctOfSingletons;
import ai.maths.sat3.model.sat3.ConjunctOfSingletonsOrSingleton;
import ai.maths.sat3.model.sat3.DisjunctOfSingletons;
import ai.maths.sat3.model.sat3.DisjunctOfSingletonsOrSingleton;

public class ProbabilityFormulaBuilder {

    public static ProbabilityFormulaOfConjunctOfSingletonsOrSingleton buildProbabilityFormulaOfConjunctOfSingletonOrSingleton(ConjunctOfSingletonsOrSingleton conjunctOfSingletonsOrSingleton) {
        if (conjunctOfSingletonsOrSingleton == ConjunctOfSingletons.TRUE) {
            return ProbabilityFormulaOfConjunctOfSingletonsOrSingleton.TRUE;
        }
        return new ProbabilityFormulaOfConjunctOfSingletonsOrSingleton(conjunctOfSingletonsOrSingleton);
    }

    public static ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton buildProbabilityFormulaOfDisjunctOfSingletonOrSingleton(DisjunctOfSingletonsOrSingleton disjunctOfSingletonsOrSingleton) {
        if (disjunctOfSingletonsOrSingleton == DisjunctOfSingletons.FALSE) {
            return ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton.FALSE;
        }
        return new ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton(disjunctOfSingletonsOrSingleton);
    }

    public static ProbabilityFormulaOfCNF buildProductOfProbability(Set<ProbabilityFormulaOfCNF> probabilityFormulaOfCNFS) {
        if (probabilityFormulaOfCNFS.contains(ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton.FALSE)) {
            return ProbabilityFormulaOfDisjunctOfSingletonsOrSingleton.FALSE;
        }
        return probabilityFormulaOfCNFS.stream()
                .filter(probabilityFormulaOfCNF -> probabilityFormulaOfCNF != ProbabilityFormulaOfConjunctOfSingletonsOrSingleton.TRUE)
                .reduce((probabilityOfCNF1, probabilityOfCNF2) -> new ProbabilityFormulaOfCNF(probabilityOfCNF1.getSumsOfMultiplications()
                        .flatMap(setLongEntry1 -> probabilityOfCNF2.getSumsOfMultiplications()
                                .flatMap(setLongEntry2 -> {
                                    Set<ProbabilityFormulaOfCNF> probabilityOfCNFS = new HashSet<>(setLongEntry1.getKey());
                                    probabilityOfCNFS.addAll(setLongEntry2.getKey());
                                    if (probabilityOfCNFS.size() == 1) {
                                        return Map.of(probabilityOfCNFS, setLongEntry1.getValue() * setLongEntry2.getValue()).entrySet().stream();
                                    }
                                    return Map.of(probabilityOfCNFS.stream()
                                            .filter(probabilityOfCNF -> probabilityOfCNF != ProbabilityFormulaOfConjunctOfSingletonsOrSingleton.TRUE)
                                            .collect(Collectors.toUnmodifiableSet()), setLongEntry1.getValue() * setLongEntry2.getValue()).entrySet().stream();
                                }))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Long::sum)))).get();
    }

    public static ProbabilityFormulaOfCNF buildSumOfProbability(Map<Set<ProbabilityFormulaOfCNF>, Long> probabilityFormulaOfCNFS) {
        probabilityFormulaOfCNFS = probabilityFormulaOfCNFS.entrySet().stream()
                .flatMap(probabilityOfCNFEntry -> buildProductOfProbability(probabilityOfCNFEntry.getKey()).getSumsOfMultiplications()
                        .map(setLongEntry -> Map.of(setLongEntry.getKey(), setLongEntry.getValue() * probabilityOfCNFEntry.getValue()).entrySet().iterator().next()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Long::sum))
                .entrySet().stream()
                .filter(setLongEntry -> setLongEntry.getValue() != 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new ProbabilityFormulaOfCNF(probabilityFormulaOfCNFS);
    }
}
