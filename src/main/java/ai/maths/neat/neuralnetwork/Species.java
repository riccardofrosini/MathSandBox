package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConstantsAndUtils;
import ai.maths.neat.utils.GenomeUtils;

import java.util.Objects;
import java.util.TreeSet;

public class Species {

    private Genome representative;
    private TreeSet<Genome> genomes;
    private int stagnation = 0;
    private float bestPerformance = 0;

    Species(Genome representative) {
        this.representative = representative;
        genomes = new TreeSet<>();
        genomes.add(representative);
    }

    int getStagnation() {
        return stagnation;
    }

    void incrementStagnation() {
        stagnation++;
    }

    void setStagnation(int stagnation) {
        this.stagnation = stagnation;
    }

    float getBestPerformance() {
        return bestPerformance;
    }

    void setBestPerformance(float bestPerformance) {
        this.bestPerformance = bestPerformance;
    }

    Genome getRepresentative() {
        return representative;
    }

    boolean add(Genome tGenome) {
        return genomes.add(tGenome);
    }

    TreeSet<Genome> getGenomes() {
        return genomes;
    }

    int size() {
        return genomes.size();
    }

    float computeSumOfAdjustedFitness() {
        return (float) genomes.stream().mapToDouble(genome -> genome.getFitness() /
                genomes.stream().mapToInt(value -> GenomeUtils.calculateDistance(genome, value) <= ConstantsAndUtils.SPECIES_DELTA_THRESHOLD ? 1 : 0).sum()).sum();

    }

    void removedUnfittestGenome(int toRemove) {
        for (int i = 0; i < toRemove; i++) {
            genomes.pollFirst();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Species)) return false;
        Species species1 = (Species) o;
        return representative.equals(species1.representative) &&
                genomes.equals(species1.genomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(representative, genomes);
    }
}
