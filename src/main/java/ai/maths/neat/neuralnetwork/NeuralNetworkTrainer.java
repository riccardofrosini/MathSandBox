package ai.maths.neat.neuralnetwork;

import ai.maths.neat.neuralnetwork.functions.FitnessCalculator;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.neuralnetwork.functions.NodeFunction;

import java.util.function.Consumer;

public class NeuralNetworkTrainer {

    public static GenomeEvaluator train(int inputs, int outputs, int generations,
                                        NodeFunction nodeFunction, FitnessCalculator fitnessCalculator) {

        Consumer<Genome> updateGenomeFunctionWithFitness = GenomeUtils.makeGenomeFunctionToUpdateFitness(fitnessCalculator, nodeFunction);

        NeuralNetworks neuralNetworks = new NeuralNetworks(GenomeUtils.makeRandomTopologyGenomes(inputs, outputs, updateGenomeFunctionWithFitness));
        Genome bestGenome = neuralNetworks.getBestPerformingFromPopulation();
        System.out.println("Generation 0");
        System.out.println(neuralNetworks);
        System.out.println(GenomeSerializerDeserializer.toJson(bestGenome));
        for (int i = 0; i < generations; i++) {
            neuralNetworks = neuralNetworks.nextGeneration(updateGenomeFunctionWithFitness);
            Genome thisGenerationBestGenome = neuralNetworks.getBestPerformingFromPopulation();
            System.out.println("Generation " + (i + 1));
            System.out.println(neuralNetworks);
            System.out.println(GenomeSerializerDeserializer.toJson(thisGenerationBestGenome));
            if (thisGenerationBestGenome.getFitness() > bestGenome.getFitness()) {
                bestGenome = thisGenerationBestGenome;
            }
        }
        NodeAndConnectionCounter.resetCounter();
        System.out.println(GenomeSerializerDeserializer.toJson(bestGenome));
        return GenomeUtils.getGenomeEvaluator(bestGenome, nodeFunction);
    }
}
