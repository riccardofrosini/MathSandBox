package ai.maths.neat.neuralnetwork;

import ai.maths.neat.neuralnetwork.functions.FitnessCalculator;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.neuralnetwork.functions.NodeFunction;

import javax.swing.*;
import java.awt.*;
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
        GenomeGraphics genomeGraphics = new GenomeGraphics(bestGenome);
        for (int i = 0; i < generations; i++) {
            neuralNetworks = neuralNetworks.nextGeneration(updateGenomeFunctionWithFitness);
            Genome thisGenerationBestGenome = neuralNetworks.getBestPerformingFromPopulation();
            System.out.println("Generation " + (i + 1));
            System.out.println(neuralNetworks);
            System.out.println(GenomeSerializerDeserializer.toJson(thisGenerationBestGenome));
            genomeGraphics.setGenome(thisGenerationBestGenome);
            if (thisGenerationBestGenome.getFitness() > bestGenome.getFitness()) {
                bestGenome = thisGenerationBestGenome;
            }
        }
        NodeAndConnectionCounter.resetCounter();
        System.out.println(GenomeSerializerDeserializer.toJson(bestGenome));
        return GenomeUtils.getGenomeEvaluator(bestGenome, nodeFunction);
    }
}

class GenomeGraphics extends JFrame {
    private Panel panel;
    private Genome genome;

    void setGenome(Genome genome) {
        this.genome = genome;
        paintGraphicsWithGenome(panel.getGraphics());
    }

    private void paintGraphicsWithGenome(Graphics graphics) {
        GenomeSerializerDeserializer.drawGenomeOnGraphics(genome, 800, 800, graphics);
    }

    GenomeGraphics(Genome genome) {
        super();
        this.genome = genome;
        setPreferredSize(new Dimension(1000, 1000));
        panel = new Panel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                paintGraphicsWithGenome(g);
            }
        };
        panel.setPreferredSize(new Dimension(1000, 1000));
        setContentPane(panel);
        pack();
        setVisible(true);
    }
}
