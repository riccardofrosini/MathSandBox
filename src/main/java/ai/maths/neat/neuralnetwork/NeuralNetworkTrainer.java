package ai.maths.neat.neuralnetwork;

import ai.maths.neat.functions.FitnessCalculator;
import ai.maths.neat.functions.GenomeEvaluator;
import ai.maths.neat.functions.NodeFunction;

import java.util.function.Consumer;

public class NeuralNetworkTrainer {

    public static GenomeEvaluator train(int inputs, int outputs, int generations,
                                        FitnessCalculator fitnessCalculator, NodeFunction nodeFunction) {

        Consumer<Genome> updateGenomeFunctionWithFitness = GenomeUtils.makeGenomeFunctionToUpdateFitness(fitnessCalculator, nodeFunction);

        NeuralNetworks neuralNetworks = new NeuralNetworks(GenomeUtils.makeRandomTopologyGenomes(inputs, outputs, updateGenomeFunctionWithFitness));
        Genome bestGenome = neuralNetworks.getPopulation().last();
        System.out.println("Generation 0: best genome fitness " + bestGenome.getFitness());
        System.out.println("              number of species " + neuralNetworks.numberOfSpecies());
        for (int i = 0; i < generations; i++) {
            neuralNetworks = neuralNetworks.nextGeneration(updateGenomeFunctionWithFitness);
            Genome thisGenerationBestGenome = neuralNetworks.getPopulation().last();
            System.out.println("Generation " + (i + 1) + ": best genome fitness " + thisGenerationBestGenome.getFitness());
            System.out.println("             number of species " + neuralNetworks.numberOfSpecies());
            if (thisGenerationBestGenome.getFitness() > bestGenome.getFitness()) {
                bestGenome = thisGenerationBestGenome;
            }
        }
        NodeCounter.resetCounter();
        return GenomeUtils.getGenomeEvaluator(bestGenome, nodeFunction);
    }
}
