package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConstantsAndUtils;

import java.util.Objects;
import java.util.TreeSet;

class Species implements Comparable<Species> {

    private final Genome representative;
    private final TreeSet<Genome> genomes;
    private int stagnation = 0;
    private double bestPerformance = 0;

    Species(Genome representative) {
        this.representative = representative;
        genomes = new TreeSet<>();
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

    void add(Genome tGenome) {
        genomes.add(tGenome);
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

    @Override
    public int compareTo(Species o) {
        if (o.bestPerformance > bestPerformance) {
            return -1;
        }
        if (o.bestPerformance < bestPerformance) {
            return 1;
        }
        return equals(o) ? 0 : hashCode() - o.hashCode() < 0 ? -1 : 1;
    }
}

