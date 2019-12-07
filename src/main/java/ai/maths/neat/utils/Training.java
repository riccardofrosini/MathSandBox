package ai.maths.neat.utils;

import ai.maths.neat.functions.LinearFunction;
import ai.maths.neat.functions.NodeFunction;
import ai.maths.neat.neuralnetwork.Genome;
import ai.maths.neat.neuralnetwork.NeuralNetworks;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;

public class Training {

    private static TreeSet<Genome> train(int inputs, int outputs, int generations,
                                         Function<Function<double[], List<Double>>, Double> evaluationFunction, NodeFunction nodeFunction) {

        Function<Genome, Double> updateGenomeFunctionWithFitness = genome -> {
            Double fitness = evaluationFunction.apply(input -> GenomeUtils.genomeEvaluate(genome, input, nodeFunction));
            genome.setFitness(fitness);
            return fitness;
        };

        HashSet<Genome> genomes = GenomeUtils.makeRandomTopologyGenomes(inputs, outputs);
        for (Genome genome : genomes) {
            updateGenomeFunctionWithFitness.apply(genome);
        }
        NeuralNetworks neuralNetworks = new NeuralNetworks();
        neuralNetworks.addAllGenomesToPopulation(genomes);

        for (int i = 0; i < generations; i++) {
            neuralNetworks = neuralNetworks.nextGeneration(updateGenomeFunctionWithFitness);
        }

        return neuralNetworks.getPopulation();
    }

    public static void main(String[] args) {
        LinearFunction linearFunction = new LinearFunction();
        TreeSet<Genome> train = train(2, 1, 30, neuralNetworkEvaluator -> {

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

        Genome fittest = train.last();
        System.out.println(fittest.getFitness());
        System.out.println(train.first().getFitness());

        double[] input = {3, 4};
        System.out.println(GenomeUtils.genomeEvaluate(fittest, input, linearFunction).get(0));
        double[] input1 = {2, 1};
        System.out.println(GenomeUtils.genomeEvaluate(fittest, input1, linearFunction).get(0));
        double[] input2 = {1, 2};
        System.out.println(GenomeUtils.genomeEvaluate(fittest, input2, linearFunction).get(0));

    }
}
