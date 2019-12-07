package ai.maths.neat.utils;

import ai.maths.neat.functions.LinearFunction;
import ai.maths.neat.functions.NodeFunction;
import ai.maths.neat.neuralnetwork.Genome;
import ai.maths.neat.neuralnetwork.GenomeUtils;
import ai.maths.neat.neuralnetwork.NeuralNetworks;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Training {

    private static Function<double[], List<Double>> train(int inputs, int outputs, int generations,
                                                          Function<Function<double[], List<Double>>, Double> evaluationFunction, NodeFunction nodeFunction) {

        Consumer<Genome> updateGenomeFunctionWithFitness = GenomeUtils.makeGenomeFunctionToUpdateFitness(evaluationFunction, nodeFunction);

        NeuralNetworks neuralNetworks = new NeuralNetworks(GenomeUtils.makeRandomTopologyGenomes(inputs, outputs, updateGenomeFunctionWithFitness));
        Genome bestGenome = neuralNetworks.getPopulation().last();
        for (int i = 0; i < generations; i++) {
            neuralNetworks = neuralNetworks.nextGeneration(updateGenomeFunctionWithFitness);
            Genome thisGenerationBestGenome = neuralNetworks.getPopulation().last();
            if (thisGenerationBestGenome.getFitness() > bestGenome.getFitness()) {
                bestGenome = thisGenerationBestGenome;
            }
        }

        return GenomeUtils.getGenomeEvaluator(neuralNetworks.getPopulation().last(), nodeFunction);
    }

    public static void main(String[] args) {
        LinearFunction linearFunction = new LinearFunction();
        Function<double[], List<Double>> function = train(2, 1, 30, neuralNetworkEvaluator -> {

            double[] input = {3, 4};
            List<Double> evaluate = neuralNetworkEvaluator.apply(input);
            Double aDouble = evaluate.get(0);
            double score = Math.abs(aDouble - (double) 7);

            double[] input1 = {2, 1};
            evaluate = neuralNetworkEvaluator.apply(input1);
            aDouble = evaluate.get(0);
            score += Math.abs(aDouble - (double) 3);

            double[] input2 = {1, 2};
            evaluate = neuralNetworkEvaluator.apply(input2);
            aDouble = evaluate.get(0);
            score += Math.abs(aDouble - (double) 3);

            return score == 0 ? 2000000000 : 1 / score;
        }, linearFunction);


        double[] input = {3, 4};
        System.out.println(function.apply(input).get(0));
        double[] input1 = {2, 1};
        System.out.println(function.apply(input1).get(0));
        double[] input2 = {1, 2};
        System.out.println(function.apply(input2).get(0));

    }
}
