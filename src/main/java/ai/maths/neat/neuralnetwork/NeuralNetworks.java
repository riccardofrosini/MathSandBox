package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConstantsAndUtils;
import ai.maths.neat.utils.GenomeUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NeuralNetworks {

    private TreeSet<Genome> population;
    private HashSet<Species> speciesCollection;

    private int stagnation = 0;
    private float bestPerformance = 0;

    public NeuralNetworks() {
        population = new TreeSet<>();
        speciesCollection = new HashSet<>();
    }


    private void addNewGenomeToPopulation(Genome genome) {
        if (population.size() >= ConstantsAndUtils.MAX_POPULATION) {
            return;
        }
        for (Species species : speciesCollection) {
            float delta = GenomeUtils.calculateDistance(genome, species.getRepresentative());
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


    public NeuralNetworks nextGeneration(Function<Genome, Float> fun) {
        NeuralNetworks neuralNetworks = new NeuralNetworks();
        HashMap<Species, Float> speciesFloatHashMap = new HashMap<>();
        float totalAdjustedFitness = 0;
        if (stagnation > ConstantsAndUtils.MAX_POPULATION_STAGNATION_GENERATION) {
            List<Species> collect = speciesCollection.stream().sorted((o1, o2) ->
                    Float.compare(o1.getBestPerformance(), o2.getBestPerformance())
            ).collect(Collectors.toList());
            for (int i = 0; i < collect.size() - 2; i++) {
                collect.get(i).getGenomes().clear();
            }
            stagnation = 0;
        }
        for (Species species : speciesCollection) {
            if (species.getStagnation() > ConstantsAndUtils.MAX_SPECIES_STAGNATION_GENERATION && speciesCollection.size() != 1) {
                species.getGenomes().clear();
            } else if (species.size() > 5) {
                neuralNetworks.makeSpeciesWithRepresentative(species.getRepresentative(),
                        species.getBestPerformance(), species.getStagnation());
                neuralNetworks.addNewGenomeToPopulation(species.getGenomes().last());
                float average = (species.getGenomes().last().getFitness() - species.getGenomes().first().getFitness()) / 2;
                species.getGenomes().removeIf(genome -> genome.getFitness() < average);
            }
        }

        for (Species species : speciesCollection) {
            float adjustedFitness = species.computeSumOfAdjustedFitness();
            speciesFloatHashMap.put(species, adjustedFitness);
            totalAdjustedFitness += adjustedFitness;
        }

        HashMap<Species, Float> tempSpecies = new HashMap<>();
        for (Map.Entry<Species, Float> tSpecies : speciesFloatHashMap.entrySet()) {
            if (tSpecies.getKey().size() != 0) {
                tempSpecies.put(tSpecies.getKey(), tSpecies.getValue());
            }
        }
        speciesFloatHashMap = tempSpecies;

        for (Map.Entry<Species, Float> speciesFloatEntry : speciesFloatHashMap.entrySet()) {
            float toReproduceNonMating = (ConstantsAndUtils.MAX_POPULATION * speciesFloatEntry.getValue() *
                    ConstantsAndUtils.MUTATION_WITHOUT_CROSSOVER) / totalAdjustedFitness;
            Iterator<Genome> iterator = speciesFloatEntry.getKey().getGenomes().iterator();
            for (int i = 0; i < toReproduceNonMating && iterator.hasNext(); i++) {
                Genome next = GenomeUtils.mutateGenome(iterator.next().clone());
                next.setFitness(fun.apply(next));
                neuralNetworks.addNewGenomeToPopulation(GenomeUtils.mutateGenome(next));
            }
        }

        for (Map.Entry<Species, Float> speciesFloatEntry : speciesFloatHashMap.entrySet()) {
            float toReproduce = (ConstantsAndUtils.MAX_POPULATION * speciesFloatEntry.getValue() *
                    (1 - ConstantsAndUtils.MUTATION_WITHOUT_CROSSOVER)) / totalAdjustedFitness;
            for (int i = 0; i < toReproduce; i++) {
                ArrayList<Genome> genomes = new ArrayList<>(speciesFloatEntry.getKey().getGenomes());
                Genome genome1 = genomes.get(ConstantsAndUtils.getRandomInt(genomes.size()));
                Genome genome2 = genomes.get(ConstantsAndUtils.getRandomInt(genomes.size()));
                Genome crossover = GenomeUtils.mutateGenome(GenomeUtils.crossover(genome1, genome2));
                crossover.setFitness(fun.apply(crossover));
                neuralNetworks.addNewGenomeToPopulation(crossover);
            }
        }
        for (Species species : neuralNetworks.getSpeciesCollection()) {
            if (species.getBestPerformance() < species.getGenomes().last().getFitness()) {
                species.setStagnation(0);
                species.setBestPerformance(species.getGenomes().last().getFitness());
            } else {
                species.incrementStagnation();
            }
        }

        if (bestPerformance < neuralNetworks.getPopulation().last().getFitness()) {
            neuralNetworks.stagnation = 0;
            neuralNetworks.bestPerformance = neuralNetworks.getPopulation().last().getFitness();
        } else {
            neuralNetworks.stagnation = stagnation + 1;
            neuralNetworks.bestPerformance = bestPerformance;
        }
        return neuralNetworks;
    }

    private void makeSpeciesWithRepresentative(Genome representative, float bestPerformance, int stagnation) {
        Species species = new Species(representative);
        species.setBestPerformance(bestPerformance);
        species.setStagnation(stagnation);
        population.add(representative);
        speciesCollection.add(species);
    }

    public TreeSet<Genome> getPopulation() {
        return population;
    }

    public HashSet<Species> getSpeciesCollection() {
        return speciesCollection;
    }
}
