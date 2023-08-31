package ai.maths.neat;

import ai.maths.neat.neuralnetwork.NeuralNetworkTrainer;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.utils.ConfigurationNetwork;
import ai.maths.neat.utils.NodeFunctionsCreator;

public class SumsTrainer {

    public static void main(String[] args) {
        ConfigurationNetwork.SPECIES_DELTA_THRESHOLD = 3;
        GenomeEvaluator genomeEvaluator = NeuralNetworkTrainer.train(2, 1, 10,
                NodeFunctionsCreator.linearUnit(), neuralNetworkEvaluator -> {

                    double[] input = {3, 4};
                    Double aDouble = neuralNetworkEvaluator.evaluateGenome(input).get(0);
                    double score = Math.abs(aDouble - (double) 7);

                    double[] input1 = {2, 1};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input1).get(0);
                    score += Math.abs(aDouble - (double) 3);

                    double[] input2 = {1, 2};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input2).get(0);
                    score += Math.abs(aDouble - (double) 3);

                    double[] input3 = {1, 1};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input3).get(0);
                    score += Math.abs(aDouble - (double) 2);

                    double[] input4 = {3, 1};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input4).get(0);
                    score += Math.abs(aDouble - (double) 4);

                    return score == 0 ? 2000000000 : 1 / score;
                });

        double[] input = {3, 4};
        System.out.println(genomeEvaluator.evaluateGenome(input).get(0));
        double[] input1 = {2, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input1).get(0));
        double[] input2 = {1, 2};
        System.out.println(genomeEvaluator.evaluateGenome(input2).get(0));
        double[] input3 = {1, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input3).get(0));
        double[] input4 = {3, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input4).get(0));

        double[] input5 = {35, 23};
        System.out.println(genomeEvaluator.evaluateGenome(input5).get(0));
    }
}
