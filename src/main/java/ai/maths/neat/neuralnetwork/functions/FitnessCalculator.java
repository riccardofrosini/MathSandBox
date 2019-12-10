package ai.maths.neat.neuralnetwork.functions;


@FunctionalInterface
public interface FitnessCalculator {

    double calculate(GenomeEvaluator genomeEvaluator);
}
