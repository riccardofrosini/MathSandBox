package ai.maths.neat.utils;

import ai.maths.neat.neuralnetwork.NodeGene;

import java.util.Random;

public class ConstantsAndUtils {

    private final static Random RANDOM = new Random();

    public final static int MAX_NODES = 10000;
    public final static int MAX_POPULATION = 1000;
    final static double MUTATE_WEIGHTS_PROBABILITY = 0.8;
    public final static double WEIGHT_PERTURBATION_PROBABILITY = 0.9;
    final static double MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY = 0.03;
    final static double MUTATE_ADDING_CONNECTION_PROBABILITY = 0.3;
    public final static double MUTATION_WITHOUT_CROSSOVER = 0.25;
    final static double DISABLE_CONNECTION_CROSSOVER_PROBABILITY = 0.75;
    final static double EXCESS_CONSTANT = 1;
    final static double DISJOINT_CONSTANT = 1;
    final static double WEIGHT_AVERAGE_CONSTANT = 0.4;
    public final static double SPECIES_DELTA_THRESHOLD = 0.1;//3;
    public final static int MAX_SPECIES_STAGNATION_GENERATION = 15;
    public final static int MAX_POPULATION_STAGNATION_GENERATION = 20;
    public final static double INTERSPECIES_MATING_RATE = 0.001;

    public static double getRandomWeight() {
        return (RANDOM.nextDouble() * 2) - 1;
    }

    public static double getRandomPerturbation() {
        return ((RANDOM.nextDouble() * 2) - 1) / 20;
    }

    public static double getRandom() {
        return RANDOM.nextDouble();
    }

    public static int getRandomInt(int maxExclusive) {
        return RANDOM.nextInt(maxExclusive);
    }

    static boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }

    public static int generateInnovation(NodeGene inNode, NodeGene outNode) {
        return inNode.getId() + outNode.getId() * ConstantsAndUtils.MAX_NODES;
    }

}
