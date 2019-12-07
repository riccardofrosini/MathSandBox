package ai.maths.neat;

import ai.maths.neat.functions.LinearFunction;
import ai.maths.neat.neuralnetwork.NeuralNetworkTrainer;

import java.util.List;
import java.util.function.Function;

public class TrainingTester {


    public static void main(String[] args) {
        LinearFunction linearFunction = new LinearFunction();
        Function<double[], List<Double>> function = NeuralNetworkTrainer.train(2, 1, 30, neuralNetworkEvaluator -> {

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
