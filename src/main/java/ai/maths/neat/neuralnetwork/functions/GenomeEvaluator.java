package ai.maths.neat.neuralnetwork.functions;

import java.util.List;

@FunctionalInterface
public interface GenomeEvaluator {
    List<Double> evaluateGenome(double[] input);
}
