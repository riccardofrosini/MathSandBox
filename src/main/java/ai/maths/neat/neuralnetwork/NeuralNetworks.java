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
        //remove species with stagnating population
        if (stagnation > ConfigurationNetwork.MAX_POPULATION_STAGNATION_GENERATION) {
            List<Species> collect = speciesCollection.stream().sorted(Comparator.comparingDouble(Species::getBestPerformance)).collect(Collectors.toList());
            for (int i = 0; i < collect.size() - 2; i++) {
                collect.get(i).getGenomes().clear();
            }
            stagnation = 0;
        } else {
            //remove stagnating species
            for (Species species : speciesCollection) {
                if (species.getStagnation() > ConfigurationNetwork.MAX_SPECIES_STAGNATION_GENERATION && speciesCollection.size() > 1) {
                    species.getGenomes().clear();
                }
            }
        }

        //Remove weakest genomes. Copy species with best performing genome
        for (Species species : speciesCollection) {
            if (species.size() != 0) {
                neuralNetworks.copySpeciesWithRandomRepresentative(species);
                if (species.size() > 5) {
                    for (int i = 0; i < species.size() * 0.2; i++) {
                        species.getGenomes().pollFirst();
                    }
                    neuralNetworks.addNewGenomeToPopulation(species.getGenomes().last());
                }
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

        int remainingToAdd = ConfigurationNetwork.MAX_POPULATION - neuralNetworks.getPopulation().size();

        //mutate without crossover
        for (Map.Entry<Species, Double> speciesDoubleEntry : speciesToAdjustedFitness.entrySet()) {
            double toReproduceNonMating = (remainingToAdd * speciesDoubleEntry.getValue() *
                    ConfigurationNetwork.MUTATION_WITHOUT_CROSSOVER) /
                    totalAdjustedFitness;
            Iterator<Genome> iterator = speciesDoubleEntry.getKey().getGenomes().descendingIterator();
            for (int i = 0; i < toReproduceNonMating; i++) {
                if (!iterator.hasNext()) {
                    iterator = speciesDoubleEntry.getKey().getGenomes().descendingIterator();
                }
                Genome next = GenomeUtils.copyAndMutateGenome(iterator.next(), updateGenomeFunctionWithFitness);
                neuralNetworks.addNewGenomeToPopulation(next);

            }
        }

        for (Map.Entry<Species, Double> speciesDoubleEntry : speciesToAdjustedFitness.entrySet()) {
            double toReproduce = (remainingToAdd * speciesDoubleEntry.getValue() *
                    (1 - ConfigurationNetwork.MUTATION_WITHOUT_CROSSOVER)) /
                    totalAdjustedFitness;
            for (int i = 0; i < toReproduce; i++) {
                if (RandomUtils.getRandom() <= ConfigurationNetwork.INTERSPECIES_MATING_RATE) {
                    //Interspecies crossover
                    Species[] species = speciesToAdjustedFitness.keySet().toArray(new Species[0]);
                    Genome genome1 = species[RandomUtils.getRandomInt(species.length)].getGenomes().last();
                    Genome genome2 = species[RandomUtils.getRandomInt(species.length)].getGenomes().last();
                    Genome crossover = GenomeUtils.crossoverAndMutateGenome(genome1, genome2, updateGenomeFunctionWithFitness);
                    neuralNetworks.addNewGenomeToPopulation(crossover);
                } else {
                    //mutate with crossover
                    Genome[] genomes = speciesDoubleEntry.getKey().getGenomes().toArray(new Genome[0]);
                    Genome genome1 = genomes[RandomUtils.getRandomInt(genomes.length)];
                    Genome genome2 = genomes[RandomUtils.getRandomInt(genomes.length)];
                    Genome crossover = GenomeUtils.crossoverAndMutateGenome(genome1, genome2, updateGenomeFunctionWithFitness);
                    neuralNetworks.addNewGenomeToPopulation(crossover);
                }
            }
        }
        //update species stagnation
        for (Species species : neuralNetworks.speciesCollection) {
            if (species.size() != 0) {
                double bestPerformanceOfSpecies = species.getGenomes().last().getFitness();
                if (species.getBestPerformance() < bestPerformanceOfSpecies) {
                    species.setStagnation(0);
                    species.setBestPerformance(bestPerformanceOfSpecies);
                } else {
                    species.incrementStagnation();
                }
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

    private void copySpeciesWithRandomRepresentative(Species species) {
        Genome[] genomes = species.getGenomes().toArray(new Genome[0]);
        Species newSpecies = new Species(genomes[RandomUtils.getRandomInt(genomes.length)]);
        newSpecies.setBestPerformance(species.getBestPerformance());
        newSpecies.setStagnation(species.getStagnation());
        speciesCollection.add(newSpecies);
    }

    TreeSet<Genome> getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("               Best genome fitness %10.2f", population.last().getFitness()));
        str.append("\n");
        str.append(String.format("               Number of species   %10d", speciesCollection.size()));
        str.append("\n");
        str.append(String.format("               Population size     %10d", population.size()));
        str.append("\n");
        str.append(String.format("               Stagnation          %10d", stagnation));
        str.append("\n");
        List<Species> collect = speciesCollection.stream().sorted((o1, o2) -> -Double.compare(o1.getBestPerformance(), o2.getBestPerformance())).collect(Collectors.toList());
        str.append("               Size                ");
        for (Species species : collect) {
            str.append(String.format("%10d ", species.size()));
        }
        str.append("\n");
        str.append("               Best Performance    ");
        for (Species species : collect) {
            str.append(String.format("%10.2f ", species.getBestPerformance()));

        }
        str.append("\n");
        str.append("               Stagnation          ");
        for (Species species : collect) {
            str.append(String.format("%10d ", species.getStagnation()));

        }
        str.append("\n");
        str.append("               Best Fitness        ");
        for (Species species : collect) {
            if (species.size() != 0) {
                str.append(String.format("%10.2f ", species.getGenomes().last().getFitness()));
            } else {
                str.append(String.format("%10.2f ", 0.0));
            }
        }
        return str.toString();
    }
}
