package ai.maths.neat.utils;

public class ConfigurationNetwork {

    private final static int MAX_NODES_DEFAULT = 10000;
    private final static int MAX_POPULATION_DEFAULT = 1000;
    private final static double MUTATE_WEIGHTS_PROBABILITY_DEFAULT = 0.8;
    private final static double WEIGHT_PERTURBATION_PROBABILITY_DEFAULT = 0.9;
    private final static double MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY_DEFAULT = 0.03;
    private final static double MUTATE_ADDING_CONNECTION_PROBABILITY_DEFAULT = 0.3;
    private final static double MUTATION_WITHOUT_CROSSOVER_DEFAULT = 0.25;
    private final static double DISABLE_CONNECTION_CROSSOVER_PROBABILITY_DEFAULT = 0.75;
    private final static double EXCESS_CONSTANT_DEFAULT = 1;
    private final static double DISJOINT_CONSTANT_DEFAULT = 1;
    private final static double WEIGHT_AVERAGE_CONSTANT_DEFAULT = 0.4;
    private final static double SPECIES_DELTA_THRESHOLD_DEFAULT = 3;
    private final static int MAX_SPECIES_STAGNATION_GENERATION_DEFAULT = 15;
    private final static int MAX_POPULATION_STAGNATION_GENERATION_DEFAULT = 20;
    private final static double INTERSPECIES_MATING_RATE_DEFAULT = 0.001;
    private final static double PERTURBATION_PERCENTAGE_DEFAULT = 0.05;


    public static int MAX_NODES = MAX_NODES_DEFAULT;
    public static int MAX_POPULATION = MAX_POPULATION_DEFAULT;
    public static double MUTATE_WEIGHTS_PROBABILITY = MUTATE_WEIGHTS_PROBABILITY_DEFAULT;
    public static double WEIGHT_PERTURBATION_PROBABILITY = WEIGHT_PERTURBATION_PROBABILITY_DEFAULT;
    public static double MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY = MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY_DEFAULT;
    public static double MUTATE_ADDING_CONNECTION_PROBABILITY = MUTATE_ADDING_CONNECTION_PROBABILITY_DEFAULT;
    public static double MUTATION_WITHOUT_CROSSOVER = MUTATION_WITHOUT_CROSSOVER_DEFAULT;
    public static double DISABLE_CONNECTION_CROSSOVER_PROBABILITY = DISABLE_CONNECTION_CROSSOVER_PROBABILITY_DEFAULT;
    public static double EXCESS_CONSTANT = EXCESS_CONSTANT_DEFAULT;
    public static double DISJOINT_CONSTANT = DISJOINT_CONSTANT_DEFAULT;
    public static double WEIGHT_AVERAGE_CONSTANT = WEIGHT_AVERAGE_CONSTANT_DEFAULT;
    public static double SPECIES_DELTA_THRESHOLD = SPECIES_DELTA_THRESHOLD_DEFAULT;
    public static int MAX_SPECIES_STAGNATION_GENERATION = MAX_SPECIES_STAGNATION_GENERATION_DEFAULT;
    public static int MAX_POPULATION_STAGNATION_GENERATION = MAX_POPULATION_STAGNATION_GENERATION_DEFAULT;
    public static double INTERSPECIES_MATING_RATE = INTERSPECIES_MATING_RATE_DEFAULT;
    static double PERTURBATION_PERCENTAGE = PERTURBATION_PERCENTAGE_DEFAULT;

    public static void setMaxNodes(int maxNodes) {
        MAX_NODES = maxNodes;
    }

    public static void setMaxPopulation(int maxPopulation) {
        MAX_POPULATION = maxPopulation;
    }

    public static void setMutateWeightsProbability(double mutateWeightsProbability) {
        MUTATE_WEIGHTS_PROBABILITY = mutateWeightsProbability;
    }

    public static void setWeightPerturbationProbability(double weightPerturbationProbability) {
        WEIGHT_PERTURBATION_PROBABILITY = weightPerturbationProbability;
    }

    public static void setMutateConnectionByAddingNodeProbability(double mutateConnectionByAddingNodeProbability) {
        MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY = mutateConnectionByAddingNodeProbability;
    }

    public static void setMutateAddingConnectionProbability(double mutateAddingConnectionProbability) {
        MUTATE_ADDING_CONNECTION_PROBABILITY = mutateAddingConnectionProbability;
    }

    public static void setMutationWithoutCrossover(double mutationWithoutCrossover) {
        MUTATION_WITHOUT_CROSSOVER = mutationWithoutCrossover;
    }

    public static void setDisableConnectionCrossoverProbability(double disableConnectionCrossoverProbability) {
        DISABLE_CONNECTION_CROSSOVER_PROBABILITY = disableConnectionCrossoverProbability;
    }

    public static void setExcessConstant(double excessConstant) {
        EXCESS_CONSTANT = excessConstant;
    }

    public static void setDisjointConstant(double disjointConstant) {
        DISJOINT_CONSTANT = disjointConstant;
    }

    public static void setWeightAverageConstant(double weightAverageConstant) {
        WEIGHT_AVERAGE_CONSTANT = weightAverageConstant;
    }

    public static void setSpeciesDeltaThreshold(double speciesDeltaThreshold) {
        SPECIES_DELTA_THRESHOLD = speciesDeltaThreshold;
    }

    public static void setMaxSpeciesStagnationGeneration(int maxSpeciesStagnationGeneration) {
        MAX_SPECIES_STAGNATION_GENERATION = maxSpeciesStagnationGeneration;
    }

    public static void setMaxPopulationStagnationGeneration(int maxPopulationStagnationGeneration) {
        MAX_POPULATION_STAGNATION_GENERATION = maxPopulationStagnationGeneration;
    }

    public static void setInterspeciesMatingRate(double interspeciesMatingRate) {
        INTERSPECIES_MATING_RATE = interspeciesMatingRate;
    }

    public static void setPerturbationPercentage(double perturbationPercentage) {
        PERTURBATION_PERCENTAGE = perturbationPercentage;
    }
}
