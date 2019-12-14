package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConfigurationNetwork;
import ai.maths.neat.utils.RandomUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class NeuralNetworks {

    private final TreeSet<Genome> population;
    private final HashSet<Species> speciesCollection;

    private int stagnation;
    private double bestPerformance;

    private NeuralNetworks() {
        population = new TreeSet<>();
        speciesCollection = new HashSet<>();
        stagnation = 0;
    }

    //First initialization stagnation set to 0 bestPerformance set to best performing genome's fitness
    NeuralNetworks(Collection<Genome> genomes) {
        this();
        addAllGenomesToPopulation(genomes);
        bestPerformance = population.last().getFitness();
    }


    private void addNewGenomeToPopulation(Genome genome) {
        if (population.size() >= ConfigurationNetwork.MAX_POPULATION) {
            return;
        }
        for (Species species : speciesCollection) {
            double delta = GenomeUtils.calculateDistance(genome, species.getRepresentative());
            if (delta <= ConfigurationNetwork.SPECIES_DELTA_THRESHOLD) {
                population.add(genome);
                species.add(genome);
                return;
            }
        }
        population.add(genome);
        Species newSpecies = new Species(genome);
        newSpecies.add(genome);
        speciesCollection.add(newSpecies);
    }

    private void addAllGenomesToPopulation(Collection<Genome> genomes) {
        for (Genome genome : genomes) {
            addNewGenomeToPopulation(genome);
        }
    }


    NeuralNetworks nextGeneration(Consumer<Genome> updateGenomeFunctionWithFitness) {
        NeuralNetworks neuralNetworks = new NeuralNetworks();
        //remove species with population stagnates
        if (stagnation > ConfigurationNetwork.MAX_POPULATION_STAGNATION_GENERATION) {
            List<Species> collect = speciesCollection.stream().sorted(Comparator.comparingDouble(Species::getBestPerformance)).collect(Collectors.toList());
            for (int i = 0; i < collect.size() - 2; i++) {
                collect.get(i).getGenomes().clear();
            }
            stagnation = 0;
        }
        //remove stagnating species and weakest genomes. Copy species with best performing
        for (Species species : speciesCollection) {
            if (species.getStagnation() > ConfigurationNetwork.MAX_SPECIES_STAGNATION_GENERATION && speciesCollection.size() > 1) {
                species.getGenomes().clear();
            } else if (species.size() > 5) {
                neuralNetworks.copySpeciesWithBestPerforming(species, species.getGenomes().last());
                double average = (species.getGenomes().last().getFitness() + species.getGenomes().first().getFitness()) / 2;
                species.getGenomes().removeIf(genome -> genome.getFitness() < average);
            }
        }
        //calculate adjusted fitness
        HashMap<Species, Double> speciesToAdjustedFitness = new HashMap<>();
        double totalAdjustedFitness = 0;
        for (Species species : speciesCollection) {
            double adjustedFitness = species.computeSumOfAdjustedFitness();
            speciesToAdjustedFitness.put(species, adjustedFitness);
            totalAdjustedFitness += adjustedFitness;
        }
        //update speciesToAdjustedFitness
        HashMap<Species, Double> tempSpecies = new HashMap<>();
        for (Map.Entry<Species, Double> tSpecies : speciesToAdjustedFitness.entrySet()) {
            if (tSpecies.getKey().size() != 0) {
                tempSpecies.put(tSpecies.getKey(), tSpecies.getValue());
            }
        }
        speciesToAdjustedFitness = tempSpecies;
        //mutate without crossover
        for (Map.Entry<Species, Double> speciesDoubleEntry : speciesToAdjustedFitness.entrySet()) {
            double toReproduceNonMating = (ConfigurationNetwork.MAX_POPULATION * speciesDoubleEntry.getValue() *
                    ConfigurationNetwork.MUTATION_WITHOUT_CROSSOVER) / totalAdjustedFitness;
            Iterator<Genome> iterator = speciesDoubleEntry.getKey().getGenomes().iterator();
            for (int i = 0; i < toReproduceNonMating && iterator.hasNext(); i++) {
                Genome next = GenomeUtils.copyAndMutateGenome(iterator.next(), updateGenomeFunctionWithFitness);
                neuralNetworks.addNewGenomeToPopulation(next);
            }
        }
        //mutate with crossover
        for (Map.Entry<Species, Double> speciesDoubleEntry : speciesToAdjustedFitness.entrySet()) {
            double toReproduce = (ConfigurationNetwork.MAX_POPULATION * speciesDoubleEntry.getValue() *
                    (1 - ConfigurationNetwork.MUTATION_WITHOUT_CROSSOVER)) / totalAdjustedFitness;
            for (int i = 0; i < toReproduce; i++) {
                Genome[] genomes = speciesDoubleEntry.getKey().getGenomes().toArray(new Genome[0]);
                Genome genome1 = genomes[RandomUtils.getRandomInt(genomes.length)];
                Genome genome2 = genomes[RandomUtils.getRandomInt(genomes.length)];
                Genome crossover = GenomeUtils.crossoverAndMutateGenome(genome1, genome2, updateGenomeFunctionWithFitness);
                neuralNetworks.addNewGenomeToPopulation(crossover);
            }
        }
        //Interspecies crossover
        for (int i = 0; i < ConfigurationNetwork.MAX_POPULATION * ConfigurationNetwork.INTERSPECIES_MATING_RATE; i++) {
            Species[] species = speciesToAdjustedFitness.keySet().toArray(new Species[0]);
            Genome genome1 = species[RandomUtils.getRandomInt(species.length)].getGenomes().last();
            Genome genome2 = species[RandomUtils.getRandomInt(species.length)].getGenomes().last();
            Genome crossover = GenomeUtils.crossoverAndMutateGenome(genome1, genome2, updateGenomeFunctionWithFitness);
            neuralNetworks.addNewGenomeToPopulation(crossover);
        }
        //update species stagnation
        for (Species species : neuralNetworks.speciesCollection) {
            double bestPerformanceOfSpecies = species.getGenomes().last().getFitness();
            if (species.getBestPerformance() < bestPerformanceOfSpecies) {
                species.setStagnation(0);
                species.setBestPerformance(bestPerformanceOfSpecies);
            } else {
                species.incrementStagnation();
            }
        }
        //Update population stagnation
        double bestPerformanceOfNextGenerationPopulation = neuralNetworks.population.last().getFitness();
        if (bestPerformance < bestPerformanceOfNextGenerationPopulation) {
            neuralNetworks.bestPerformance = bestPerformanceOfNextGenerationPopulation;
        } else {
            neuralNetworks.stagnation = stagnation + 1;
            neuralNetworks.bestPerformance = bestPerformance;
        }
        return neuralNetworks;
    }

    private void copySpeciesWithBestPerforming(Species species, Genome bestPerforming) {
        Species newSpecies = new Species(species.getRepresentative());
        newSpecies.setBestPerformance(species.getBestPerformance());
        newSpecies.setStagnation(species.getStagnation());
        newSpecies.add(bestPerforming);
        population.add(bestPerforming);
        speciesCollection.add(newSpecies);
    }

    TreeSet<Genome> getPopulation() {
        return population;
    }

    int numberOfSpecies() {
        return speciesCollection.size();
    }
}
