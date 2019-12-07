package ai.maths.neat.functions;


@FunctionalInterface
public interface FitnessCalculator {

    double calculate(GenomeEvaluator genomeEvaluator);
}
