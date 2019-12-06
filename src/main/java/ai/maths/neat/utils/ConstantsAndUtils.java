package ai.maths.neat.utils;

import ai.maths.neat.neuralnetwork.NodeGene;

import java.util.Random;

public class ConstantsAndUtils {

    private final static Random RANDOM = new Random();

    public final static int MAX_NODES = 10000;
    public final static int MAX_POPULATION = 1000;
    final static float MUTATE_WEIGHTS_PROBABILITY = 0.8f;
    public final static float WEIGHT_PERTURBATION_PROBABILITY = 0.9f;
    final static float MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY = 0.03f;
    final static float MUTATE_ADDING_CONNECTION_PROBABILITY = 0.3f;
    public final static float MUTATION_WITHOUT_CROSSOVER = 0.25f;
    final static float DISABLE_CONNECTION_CROSSOVER_PROBABILITY = 0.75f;
    final static float EXCESS_CONSTANT = 1;
    final static float DISJOINT_CONSTANT = 1;
    final static float WEIGHT_AVERAGE_CONSTANT = 0.4f;
    public final static float SPECIES_DELTA_THRESHOLD = 0.1f;//3;
    public final static int MAX_SPECIES_STAGNATION_GENERATION = 15;
    public final static int MAX_POPULATION_STAGNATION_GENERATION = 20;

    public static float getRandomWeight() {
        return (RANDOM.nextFloat() * 2) - 1;
    }

    public static float getRandomPerturbation() {
        return ((RANDOM.nextFloat() * 2) - 1) / 20;
    }

    public static float getRandom() {
        return RANDOM.nextFloat();
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
