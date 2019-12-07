package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConstantsAndUtils;
import ai.maths.neat.utils.GenomeUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NeuralNetworks {

    private final TreeSet<Genome> population;
    private final HashSet<Species> speciesCollection;

    private int stagnation = 0;
    private double bestPerformance = 0;

    public NeuralNetworks() {
        population = new TreeSet<>();
        speciesCollection = new HashSet<>();
    }


    private void addNewGenomeToPopulation(Genome genome) {
        if (population.size() >= ConstantsAndUtils.MAX_POPULATION) {
            return;
        }
        for (Species species : speciesCollection) {
            double delta = GenomeUtils.calculateDistance(genome, species.getRepresentative());
            if (delta <= ConstantsAndUtils.SPECIES_DELTA_THRESHOLD) {
                population.add(genome);
                species.add(genome);
                return;
            }
        }
        population.add(genome);
        speciesCollection.add(new Species(genome));
    }

    public void addAllGenomesToPopulation(Collection<Genome> genomes) {
        for (Genome genome : genomes) {
            addNewGenomeToPopulation(genome);
        }
    }


    public NeuralNetworks nextGeneration(Function<Genome, Double> updateGenomeFunctionWithFitness) {
        NeuralNetworks neuralNetworks = new NeuralNetworks();
        HashMap<Species, Double> speciesDoubleHashMap = new HashMap<>();
        double totalAdjustedFitness = 0;
        //remove species with population stagnates
        if (stagnation > ConstantsAndUtils.MAX_POPULATION_STAGNATION_GENERATION) {
            List<Species> collect = speciesCollection.stream().sorted(Comparator.comparingDouble(Species::getBestPerformance)
            ).collect(Collectors.toList());
            for (int i = 0; i < collect.size() - 2; i++) {
                collect.get(i).getGenomes().clear();
            }
            stagnation = 0;
        }
        //remove stagnating species and weakest genomes
        for (Species species : speciesCollection) {
            if (species.getStagnation() > ConstantsAndUtils.MAX_SPECIES_STAGNATION_GENERATION && speciesCollection.size() > 1) {
                species.getGenomes().clear();
            } else if (species.size() > 5) {
                neuralNetworks.makeSpeciesWithRepresentative(species.getRepresentative(),
                        species.getBestPerformance(), species.getStagnation());
                neuralNetworks.addNewGenomeToPopulation(species.getGenomes().last());
                double average = (species.getGenomes().last().getFitness() - species.getGenomes().first().getFitness()) / 2;
                species.getGenomes().removeIf(genome -> genome.getFitness() < average);
            }
        }
        //calculate adjusted fitness
        for (Species species : speciesCollection) {
            double adjustedFitness = species.computeSumOfAdjustedFitness();
            speciesDoubleHashMap.put(species, adjustedFitness);
            totalAdjustedFitness += adjustedFitness;
        }
        //update species
        HashMap<Species, Double> tempSpecies = new HashMap<>();
        for (Map.Entry<Species, Double> tSpecies : speciesDoubleHashMap.entrySet()) {
            if (tSpecies.getKey().size() != 0) {
                tempSpecies.put(tSpecies.getKey(), tSpecies.getValue());
            }
        }
        speciesDoubleHashMap = tempSpecies;
        //mutate without crossover
        for (Map.Entry<Species, Double> speciesDoubleEntry : speciesDoubleHashMap.entrySet()) {
            double toReproduceNonMating = (ConstantsAndUtils.MAX_POPULATION * speciesDoubleEntry.getValue() *
                    ConstantsAndUtils.MUTATION_WITHOUT_CROSSOVER) / totalAdjustedFitness;
            Iterator<Genome> iterator = speciesDoubleEntry.getKey().getGenomes().iterator();
            for (int i = 0; i < toReproduceNonMating && iterator.hasNext(); i++) {
                Genome next = GenomeUtils.mutateGenome(iterator.next().clone());
                updateGenomeFunctionWithFitness.apply(next);
                neuralNetworks.addNewGenomeToPopulation(next);
            }
        }
        //mutate with crossover
        for (Map.Entry<Species, Double> speciesDoubleEntry : speciesDoubleHashMap.entrySet()) {
            double toReproduce = (ConstantsAndUtils.MAX_POPULATION * speciesDoubleEntry.getValue() *
                    (1 - ConstantsAndUtils.MUTATION_WITHOUT_CROSSOVER)) / totalAdjustedFitness;
            for (int i = 0; i < toReproduce; i++) {
                Genome[] genomes = speciesDoubleEntry.getKey().getGenomes().toArray(new Genome[0]);
                Genome genome1 = genomes[ConstantsAndUtils.getRandomInt(genomes.length)];
                Genome genome2 = genomes[ConstantsAndUtils.getRandomInt(genomes.length)];
                Genome crossover = GenomeUtils.mutateGenome(GenomeUtils.crossover(genome1, genome2));
                updateGenomeFunctionWithFitness.apply(crossover);
                neuralNetworks.addNewGenomeToPopulation(crossover);
            }
        }
        //Interspecies crossover
        for (int i = 0; i < ConstantsAndUtils.MAX_POPULATION * ConstantsAndUtils.INTERSPECIES_MATING_RATE; i++) {
            Species[] species = speciesDoubleHashMap.keySet().toArray(new Species[0]);
            Genome genome1 = species[ConstantsAndUtils.getRandomInt(species.length)].getGenomes().last();
            Genome genome2 = species[ConstantsAndUtils.getRandomInt(species.length)].getGenomes().last();
            Genome crossover = GenomeUtils.mutateGenome(GenomeUtils.crossover(genome1, genome2));
            updateGenomeFunctionWithFitness.apply(crossover);
            neuralNetworks.addNewGenomeToPopulation(crossover);
        }
        //update species stagnation
        for (Species species : neuralNetworks.speciesCollection) {
            if (species.getBestPerformance() < species.getGenomes().last().getFitness()) {
                species.setStagnation(0);
                species.setBestPerformance(species.getGenomes().last().getFitness());
            } else {
                species.incrementStagnation();
            }
        }
        //update population stagnation
        if (bestPerformance < neuralNetworks.getPopulation().last().getFitness()) {
            neuralNetworks.stagnation = 0;
            neuralNetworks.bestPerformance = neuralNetworks.getPopulation().last().getFitness();
        } else {
            neuralNetworks.stagnation = stagnation + 1;
            neuralNetworks.bestPerformance = bestPerformance;
        }
        return neuralNetworks;
    }

    private void makeSpeciesWithRepresentative(Genome representative, double bestPerformance, int stagnation) {
        Species species = new Species(representative);
        species.setBestPerformance(bestPerformance);
        species.setStagnation(stagnation);
        population.add(representative);
        speciesCollection.add(species);
    }

    public TreeSet<Genome> getPopulation() {
        return population;
    }
}
