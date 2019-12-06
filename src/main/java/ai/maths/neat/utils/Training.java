package ai.maths.neat.utils;

import ai.maths.neat.functions.NodeFunction;
import ai.maths.neat.functions.SigmoidFunction;
import ai.maths.neat.neuralnetwork.Genome;
import ai.maths.neat.neuralnetwork.NeuralNetworks;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;

public class Training {

    public static TreeSet<Genome> train(int inputs, int outputs, int generations,
                                        Function<Genome, Float> fun, NodeFunction sigmoid) {
        HashSet<Genome> genomes = GenomeUtils.makeRandomTopologyGenomes(inputs, outputs);
        for (Genome genome : genomes) {
            Float evaluate = fun.apply(genome);
            genome.setFitness(evaluate);
        }
        NeuralNetworks neuralNetworks = new NeuralNetworks();
        neuralNetworks.addAllGenomesToPopulation(genomes);

        for (int i = 0; i < generations; i++) {
            System.out.println("Iteration " + i);

            Genome fittest = neuralNetworks.getPopulation().last();
            System.out.println(fittest.getFitness());
            System.out.println(neuralNetworks.getPopulation().first().getFitness());

            System.out.println(neuralNetworks.getSpeciesCollection().size());

            float[] input = {3, 4};
            System.out.println(1 / GenomeUtils.genomeEvaluate(fittest, input, sigmoid).get(0));
            float[] input1 = {2, 1};
            System.out.println(1 / GenomeUtils.genomeEvaluate(fittest, input1, sigmoid).get(0));


            neuralNetworks = neuralNetworks.nextGeneration(fun);
        }

        return neuralNetworks.getPopulation();
    }

    public static void main(String[] args) {
        SigmoidFunction sigmoidFunction = new SigmoidFunction(2);
        TreeSet<Genome> train = train(2, 1, 1000, genome -> {

            float[] input = {3, 4};
            List<Float> evaluate = GenomeUtils.genomeEvaluate(genome, input, sigmoidFunction);
            Float aFloat = evaluate.get(0);
            float score = (float) -Math.log10(Math.abs(aFloat - ((float) 1 / 7)));

            float[] input1 = {2, 1};
            evaluate = GenomeUtils.genomeEvaluate(genome, input1, sigmoidFunction);
            aFloat = evaluate.get(0);
            score += (float) -Math.log10(Math.abs(aFloat - ((float) 1 / 3)));

            return score == 0 ? 10000000f : score;
        }, sigmoidFunction);

        Genome fittest = train.last();
        System.out.println(fittest.getFitness());
        System.out.println(train.first().getFitness());

        float[] input = {3, 4};
        System.out.println(1 / GenomeUtils.genomeEvaluate(fittest, input, sigmoidFunction).get(0));
        float[] input1 = {2, 1};
        System.out.println(1 / GenomeUtils.genomeEvaluate(fittest, input1, sigmoidFunction).get(0));

    }
}
