package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConstantsAndUtils;
import ai.maths.neat.utils.GenomeUtils;

import java.util.Objects;
import java.util.TreeSet;

class Species {

    private final Genome representative;
    private final TreeSet<Genome> genomes;
    private int stagnation = 0;
    private double bestPerformance = 0;

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

    double getBestPerformance() {
        return bestPerformance;
    }

    void setBestPerformance(double bestPerformance) {
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

    double computeSumOfAdjustedFitness() {
        return genomes.stream().mapToDouble(genome -> genome.getFitness() /
                genomes.stream().mapToInt(value -> GenomeUtils.calculateDistance(genome, value) <= ConstantsAndUtils.SPECIES_DELTA_THRESHOLD ? 1 : 0).sum()).sum();

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
