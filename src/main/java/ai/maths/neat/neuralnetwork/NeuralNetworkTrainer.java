package ai.maths.neat.neuralnetwork;

import ai.maths.neat.functions.NodeFunction;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class NeuralNetworkTrainer {

    public static Function<double[], List<Double>> train(int inputs, int outputs, int generations,
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
}
