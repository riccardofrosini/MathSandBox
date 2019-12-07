package ai.maths.neat;

import ai.maths.neat.functions.GenomeEvaluator;
import ai.maths.neat.functions.LinearFunction;
import ai.maths.neat.neuralnetwork.NeuralNetworkTrainer;

import java.util.List;

public class TrainingTester {


    public static void main(String[] args) {
        LinearFunction linearFunction = new LinearFunction();
        GenomeEvaluator genomeEvaluator = NeuralNetworkTrainer.train(2, 1, 30, neuralNetworkEvaluator -> {

            double[] input = {3, 4};
            List<Double> evaluate = neuralNetworkEvaluator.evaluateGenome(input);
            Double aDouble = evaluate.get(0);
            double score = Math.abs(aDouble - (double) 7);

            double[] input1 = {2, 1};
            evaluate = neuralNetworkEvaluator.evaluateGenome(input1);
            aDouble = evaluate.get(0);
            score += Math.abs(aDouble - (double) 3);

            double[] input2 = {1, 2};
            evaluate = neuralNetworkEvaluator.evaluateGenome(input2);
            aDouble = evaluate.get(0);
            score += Math.abs(aDouble - (double) 3);

            return score == 0 ? 2000000000 : 1 / score;
        }, linearFunction);


        double[] input = {3, 4};
        System.out.println(genomeEvaluator.evaluateGenome(input).get(0));
        double[] input1 = {2, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input1).get(0));
        double[] input2 = {1, 2};
        System.out.println(genomeEvaluator.evaluateGenome(input2).get(0));

    }
}
