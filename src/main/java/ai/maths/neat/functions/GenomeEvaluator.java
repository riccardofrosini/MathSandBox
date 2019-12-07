package ai.maths.neat.functions;

import java.util.List;

@FunctionalInterface
public interface GenomeEvaluator {

    List<Double> evaluateGenome(double[] input);
}
