package ai.maths.neat.utils;

import ai.maths.neat.functions.NodeFunction;
import ai.maths.neat.neuralnetwork.ConnectionGene;
import ai.maths.neat.neuralnetwork.Genome;
import ai.maths.neat.neuralnetwork.NodeGene;

import java.util.*;

public class GenomeUtils {

    public static Genome crossover(Genome thisGenome, Genome otherGenome) {
        Genome crossover = new Genome();
        thisGenome.copyNodesTo(crossover);
        otherGenome.copyNodesTo(crossover);

        Iterator<ConnectionGene> thisIterator = thisGenome.getConnections().values().iterator();
        Iterator<ConnectionGene> otherIterator = otherGenome.getConnections().values().iterator();
        boolean nextThis = true;
        boolean nextOther = true;
        ConnectionGene thisConnection = null;
        ConnectionGene otherConnection = null;
        while (thisIterator.hasNext() || otherIterator.hasNext()) {
            if ((thisIterator.hasNext() && otherIterator.hasNext()) || (thisIterator.hasNext() && !nextOther) || (otherIterator.hasNext() && !nextThis)) {
                thisConnection = nextThis ? thisIterator.next() : thisConnection;
                otherConnection = nextOther ? otherIterator.next() : otherConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    addMatchingConnectionToCrossover(crossover, thisConnection, otherConnection);
                    nextThis = true;
                    nextOther = true;
                } else if (thisConnection.getInnovation() > otherConnection.getInnovation()) {
                    if (thisGenome.getFitness() < otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && ConstantsAndUtils.getRandomBoolean())) {
                        mergeConnectionToCrossover(crossover, otherConnection);
                    }
                    nextThis = false;
                    nextOther = true;
                } else if (thisConnection.getInnovation() < otherConnection.getInnovation()) {
                    if (thisGenome.getFitness() > otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && ConstantsAndUtils.getRandomBoolean())) {
                        mergeConnectionToCrossover(crossover, thisConnection);
                    }
                    nextThis = true;
                    nextOther = false;
                }
            } else if (thisIterator.hasNext() || !nextThis) {
                thisConnection = nextThis ? thisIterator.next() : thisConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    addMatchingConnectionToCrossover(crossover, thisConnection, otherConnection);
                } else if (thisGenome.getFitness() > otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && ConstantsAndUtils.getRandomBoolean())) {
                    mergeConnectionToCrossover(crossover, thisConnection);
                }
                nextThis = true;
            } else if (otherIterator.hasNext() || !nextOther) {
                otherConnection = nextOther ? otherIterator.next() : otherConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    addMatchingConnectionToCrossover(crossover, thisConnection, otherConnection);
                } else if (thisGenome.getFitness() < otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && ConstantsAndUtils.getRandomBoolean())) {
                    mergeConnectionToCrossover(crossover, otherConnection);
                }
                nextOther = true;
            }
        }
        return crossover;
    }

    private static void addMatchingConnectionToCrossover(Genome crossover, ConnectionGene thisConnection, ConnectionGene otherConnection) {
        crossover.makeConnection(crossover.getNodes().get(thisConnection.getInNode().getId()),
                crossover.getNodes().get(thisConnection.getOutNode().getId()),
                ConstantsAndUtils.getRandomBoolean() ? thisConnection.getWeight() : otherConnection.getWeight());
        if (((thisConnection.isEnabled() ^ otherConnection.isEnabled()) &&
                (ConstantsAndUtils.getRandom() <= ConstantsAndUtils.DISABLE_CONNECTION_CROSSOVER_PROBABILITY)) ||
                (!thisConnection.isEnabled() && !otherConnection.isEnabled())) {
            crossover.getConnections().get(ConstantsAndUtils.generateInnovation(thisConnection.getInNode(),
                    thisConnection.getOutNode())).disable();
        }
    }

    private static void mergeConnectionToCrossover(Genome crossover, ConnectionGene connection) {
        crossover.makeConnection(crossover.getNodes().get(connection.getInNode().getId()),
                crossover.getNodes().get(connection.getOutNode().getId()), connection.getWeight());
        if (!connection.isEnabled()) {
            crossover.getConnections().get(ConstantsAndUtils.generateInnovation(connection.getInNode(),
                    connection.getOutNode())).disable();
        }
    }

    private static double[] getExcesses_Disjoints_AverageWeightDifferences_Normalisation(Genome thisGenome, Genome otherGenome) {
        int excesses = 0;
        int disjoints = 0;
        double averageWeights = 0;
        int matching = 0;
        Iterator<ConnectionGene> thisIterator = thisGenome.getConnections().values().iterator();
        Iterator<ConnectionGene> otherIterator = otherGenome.getConnections().values().iterator();
        boolean nextThis = true;
        boolean nextOther = true;
        ConnectionGene thisConnection = null;
        ConnectionGene otherConnection = null;
        while (thisIterator.hasNext() || otherIterator.hasNext()) {
            if ((thisIterator.hasNext() && otherIterator.hasNext()) || (thisIterator.hasNext() && !nextOther) || (otherIterator.hasNext() && !nextThis)) {
                thisConnection = nextThis ? thisIterator.next() : thisConnection;
                otherConnection = nextOther ? otherIterator.next() : otherConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    averageWeights = averageWeights + Math.abs(thisConnection.getWeight() - otherConnection.getWeight());
                    matching++;
                    nextThis = true;
                    nextOther = true;
                } else if (thisConnection.getInnovation() > otherConnection.getInnovation()) {
                    disjoints++;
                    nextThis = false;
                    nextOther = true;
                } else if (thisConnection.getInnovation() < otherConnection.getInnovation()) {
                    disjoints++;
                    nextThis = true;
                    nextOther = false;
                }
            } else if (thisIterator.hasNext() || !nextThis) {
                thisConnection = nextThis ? thisIterator.next() : thisConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    averageWeights = averageWeights + Math.abs(thisConnection.getWeight() - otherConnection.getWeight());
                    matching++;
                } else if (thisConnection.getInnovation() < otherConnection.getInnovation()) {
                    disjoints++;
                } else if (thisConnection.getInnovation() > otherConnection.getInnovation()) {
                    excesses++;
                }
                nextThis = true;
            } else if (otherIterator.hasNext() || !nextOther) {
                otherConnection = nextOther ? otherIterator.next() : otherConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    averageWeights = averageWeights + Math.abs(thisConnection.getWeight() - otherConnection.getWeight());
                    matching++;
                } else if (thisConnection.getInnovation() > otherConnection.getInnovation()) {
                    disjoints++;
                } else if (thisConnection.getInnovation() < otherConnection.getInnovation()) {
                    excesses++;
                }
                nextOther = true;
            }
        }
        averageWeights = averageWeights / matching;
        int thisSize = thisGenome.getConnections().size();
        int otherSize = otherGenome.getConnections().size();
        return new double[]{excesses, disjoints, averageWeights, thisSize < 20 && otherSize < 20 ? 1 : Math.max(thisSize, otherSize)};
    }

    public static double calculateDistance(Genome thisGenome, Genome otherGenome) {
        double[] edwn = getExcesses_Disjoints_AverageWeightDifferences_Normalisation(thisGenome, otherGenome);
        return (edwn[0] * ConstantsAndUtils.EXCESS_CONSTANT + edwn[1] * ConstantsAndUtils.DISJOINT_CONSTANT) / edwn[3]
                + edwn[2] * ConstantsAndUtils.WEIGHT_AVERAGE_CONSTANT;
    }

    static HashSet<Genome> makeRandomTopologyGenomes(int inputNodes, int outPutNodes) {
        Genome genome = new Genome();
        for (int i = 0; i < inputNodes; i++) {
            genome.addInputNode(i);
        }
        for (int i = 0; i < outPutNodes; i++) {
            genome.addOutputNode();
        }
        HashSet<Genome> genomes = new HashSet<>();
        for (int i = 0; i < ConstantsAndUtils.MAX_POPULATION; i++) {
            Genome newGenome = genome.clone();
            for (int j = 0; j < Math.max(inputNodes, outPutNodes); j++) {
                newGenome.mutateWithNewConnection();
            }
            if (ConstantsAndUtils.getRandomBoolean()) {
                newGenome.mutateConnectionByAddingNode();
            }
            genomes.add(newGenome);
        }
        return genomes;
    }

    public static Genome mutateGenome(Genome genome) {
        if (ConstantsAndUtils.getRandom() <= ConstantsAndUtils.MUTATE_WEIGHTS_PROBABILITY) {
            genome.mutateConnectionWeights();
        }
        if (ConstantsAndUtils.getRandom() <= ConstantsAndUtils.MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY) {
            genome.mutateConnectionByAddingNode();
        }
        if (ConstantsAndUtils.getRandom() <= ConstantsAndUtils.MUTATE_ADDING_CONNECTION_PROBABILITY) {
            genome.mutateWithNewConnection();
        }
        return genome;
    }

    public static List<Double> genomeEvaluate(Genome genome, double[] inputs, NodeFunction nodeFunction) {
        HashMap<Integer, NodeGene> nodes = genome.getNodes();
        HashMap<NodeGene, Double> nodeGeneDoubleHashMap = new HashMap<>();
        while (!nodeGeneDoubleHashMap.keySet().containsAll(nodes.values())) {
            c:
            for (NodeGene node : nodes.values()) {
                if (!nodeGeneDoubleHashMap.containsKey(node)) {
                    if (node.getType() == NodeGene.Type.INPUT) {
                        nodeGeneDoubleHashMap.put(node, nodeFunction.function(inputs[node.getInputId()]));
                    } else {
                        double value = 0;
                        for (ConnectionGene backConnection : node.getBackConnections()) {
                            if (backConnection.isEnabled()) {
                                if (nodeGeneDoubleHashMap.containsKey(backConnection.getInNode())) {
                                    value = value + nodeGeneDoubleHashMap.get(backConnection.getInNode()) * backConnection.getWeight();
                                } else if (nodes.values().contains(backConnection.getInNode())) {
                                    continue c;
                                }
                            }
                        }
                        nodeGeneDoubleHashMap.put(node, nodeFunction.function(value));
                    }
                }
            }
        }
        ArrayList<NodeGene> outputNodes = genome.getOutputNodes();
        List<Double> results = new ArrayList<>(outputNodes.size());
        for (NodeGene outputNode : outputNodes) {
            results.add(nodeGeneDoubleHashMap.get(outputNode));
        }
        return results;
    }
}
