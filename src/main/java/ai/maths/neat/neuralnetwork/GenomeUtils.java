package ai.maths.neat.neuralnetwork;

import ai.maths.neat.neuralnetwork.functions.FitnessCalculator;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.neuralnetwork.functions.NodeFunction;
import ai.maths.neat.utils.ConfigurationNetwork;
import ai.maths.neat.utils.RandomUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class GenomeUtils {


    static Genome crossoverAndMutateGenome(Genome thisGenome, Genome otherGenome, Consumer<Genome> updateGenomeFunctionWithFitness) {
        Genome genome = mutateGenome(crossover(thisGenome, otherGenome));
        updateGenomeFunctionWithFitness.accept(genome);
        return genome;
    }

    static Genome copyAndMutateGenome(Genome genome, Consumer<Genome> updateGenomeFunctionWithFitness) {
        Genome newGenome = mutateGenome(cloneGenome(genome));
        updateGenomeFunctionWithFitness.accept(newGenome);
        return newGenome;
    }

    private static Genome crossover(Genome thisGenome, Genome otherGenome) {
        Genome crossover = new Genome();
        thisGenome.copyNodesTo(crossover);
        otherGenome.copyNodesTo(crossover);

        Iterator<ConnectionGene> thisIterator = thisGenome.getConnectionsCollection().iterator();
        Iterator<ConnectionGene> otherIterator = otherGenome.getConnectionsCollection().iterator();
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
                    if (thisGenome.getFitness() < otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && RandomUtils.getRandomBoolean())) {
                        mergeConnectionToCrossover(crossover, otherConnection);
                    }
                    nextThis = false;
                    nextOther = true;
                } else if (thisConnection.getInnovation() < otherConnection.getInnovation()) {
                    if (thisGenome.getFitness() > otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && RandomUtils.getRandomBoolean())) {
                        mergeConnectionToCrossover(crossover, thisConnection);
                    }
                    nextThis = true;
                    nextOther = false;
                }
            } else if (thisIterator.hasNext() || !nextThis) {
                thisConnection = nextThis ? thisIterator.next() : thisConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    addMatchingConnectionToCrossover(crossover, thisConnection, otherConnection);
                } else if (thisGenome.getFitness() > otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && RandomUtils.getRandomBoolean())) {
                    mergeConnectionToCrossover(crossover, thisConnection);
                }
                nextThis = true;
            } else if (otherIterator.hasNext() || !nextOther) {
                otherConnection = nextOther ? otherIterator.next() : otherConnection;
                if (thisConnection.getInnovation() == otherConnection.getInnovation()) {
                    addMatchingConnectionToCrossover(crossover, thisConnection, otherConnection);
                } else if (thisGenome.getFitness() < otherGenome.getFitness() || (thisGenome.getFitness() == otherGenome.getFitness() && RandomUtils.getRandomBoolean())) {
                    mergeConnectionToCrossover(crossover, otherConnection);
                }
                nextOther = true;
            }
        }

        int sizeNodes;
        int sizeConnections;
        do {
            sizeNodes = crossover.getNodesCollection().size();
            sizeConnections = crossover.getNumberOfConnections();
            crossover.getNodesCollection().removeIf(nodeGene -> nodeGene.getType() != NodeGene.Type.INPUT
                    && nodeGene.getType() != NodeGene.Type.OUTPUT && nodeGene.getBackConnections().isEmpty());
            crossover.getConnectionsCollection().removeIf(connectionGene -> {
                if (crossover.getNodeWithId(connectionGene.getInNode()) == null) {
                    NodeGene nodeWithId = crossover.getNodeWithId(connectionGene.getOutNode());
                    if (nodeWithId != null) {
                        nodeWithId.getBackConnections().remove(connectionGene);
                    }
                    return true;
                }
                return false;
            });
        } while (sizeNodes != crossover.getNodesCollection().size() || sizeConnections != crossover.getNumberOfConnections());
        return crossover;
    }

    //Should replace replaceOrMakeNewConnection with makeNewConnection is it possible that the connection is already present?
    private static void addMatchingConnectionToCrossover(Genome crossover, ConnectionGene thisConnection, ConnectionGene otherConnection) {
        boolean connectionMade = crossover.replaceOrMakeNewConnection(thisConnection.getInNode(),
                thisConnection.getOutNode(),
                RandomUtils.getRandomBoolean() ? thisConnection.getWeight() : otherConnection.getWeight());
        if (connectionMade && (((thisConnection.isEnabled() ^ otherConnection.isEnabled()) &&
                (RandomUtils.getRandom() <= ConfigurationNetwork.DISABLE_CONNECTION_CROSSOVER_PROBABILITY)) ||
                (!thisConnection.isEnabled() && !otherConnection.isEnabled()))) {
            crossover.getConnectionWithInnovationNumber(NodeAndConnectionCounter.getNewInnovationForConnection(thisConnection.getInNode(),
                    thisConnection.getOutNode())).disable();
        }
    }

    //Should replace replaceOrMakeNewConnection with makeNewConnection is it possible that the connection is already present?
    private static void mergeConnectionToCrossover(Genome crossover, ConnectionGene connection) {
        boolean connectionMade = crossover.replaceOrMakeNewConnection(connection.getInNode(),
                connection.getOutNode(), connection.getWeight());
        if (!connection.isEnabled() && connectionMade) {
            crossover.getConnectionWithInnovationNumber(NodeAndConnectionCounter.getNewInnovationForConnection(connection.getInNode(),
                    connection.getOutNode())).disable();
        }
    }

    private static double[] getExcesses_Disjoints_AverageWeightDifferences_Normalisation(Genome thisGenome, Genome otherGenome) {
        int excesses = 0;
        int disjoints = 0;
        double averageWeights = 0;
        int matching = 0;
        Iterator<ConnectionGene> thisIterator = thisGenome.getConnectionsCollection().iterator();
        Iterator<ConnectionGene> otherIterator = otherGenome.getConnectionsCollection().iterator();
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
        int thisSize = thisGenome.getNumberOfConnections();
        int otherSize = otherGenome.getNumberOfConnections();
        return new double[]{excesses, disjoints, averageWeights, thisSize < 20 && otherSize < 20 ? 1 : Math.max(thisSize, otherSize)};
    }

    static double calculateDistance(Genome thisGenome, Genome otherGenome) {
        double[] excessDisjointsAverageWeightDifferenceNormalisation = getExcesses_Disjoints_AverageWeightDifferences_Normalisation(thisGenome, otherGenome);
        return (excessDisjointsAverageWeightDifferenceNormalisation[0] * ConfigurationNetwork.EXCESS_CONSTANT
                + excessDisjointsAverageWeightDifferenceNormalisation[1] * ConfigurationNetwork.DISJOINT_CONSTANT) / excessDisjointsAverageWeightDifferenceNormalisation[3]
                + excessDisjointsAverageWeightDifferenceNormalisation[2] * ConfigurationNetwork.WEIGHT_AVERAGE_CONSTANT;
    }


    private static Genome cloneGenome(Genome genome) {
        Genome clone = new Genome();
        genome.copyNodesTo(clone);
        for (ConnectionGene connection : genome.getConnectionsCollection()) {
            clone.addConnection(connection.getInNode(), connection.getOutNode(), connection.getWeight());
            ConnectionGene connectionGene = clone.getConnectionWithInnovationNumber(connection.getInnovation());
            if (!connection.isEnabled()) {
                connectionGene.disable();
            }
        }
        return clone;
    }

    static HashSet<Genome> makeRandomTopologyGenomes(int inputNodes, int outPutNodes, Consumer<Genome> updateGenomeFunctionWithFitness) {
        Genome genome = new Genome();
        for (int i = 0; i < inputNodes; i++) {
            genome.addInputNode(i);
        }
        for (int i = 0; i < outPutNodes; i++) {
            genome.addOutputNode();
        }
        HashSet<Genome> genomes = new HashSet<>();
        NodeGene[] nodeGenes = genome.getNodesCollection().toArray(new NodeGene[0]);
        for (int i = 0; i < ConfigurationNetwork.MAX_POPULATION; i++) {
            Genome newGenome = cloneGenome(genome);

            for (NodeGene nodeGene : newGenome.getNodesCollection()) {
                boolean endLoop = false;
                for (int j = 0; j < 10000 && !endLoop; j++) {
                    NodeGene inOutNode = nodeGenes[RandomUtils.getRandomInt(nodeGenes.length)];
                    if (nodeGene.getType() == NodeGene.Type.INPUT) {
                        endLoop = newGenome.replaceOrMakeNewConnection(nodeGene.getId(), inOutNode.getId(),
                                RandomUtils.getRandomWeight());
                    } else {
                        endLoop = newGenome.replaceOrMakeNewConnection(inOutNode.getId(), nodeGene.getId(),
                                RandomUtils.getRandomWeight());
                    }
                }
            }
            updateGenomeFunctionWithFitness.accept(newGenome);
            genomes.add(newGenome);
        }
        return genomes;
    }

    private static Genome mutateGenome(Genome genome) {
        if (RandomUtils.getRandom() <= ConfigurationNetwork.MUTATE_WEIGHTS_PROBABILITY) {
            genome.mutateConnectionWeights();
        }
        if (RandomUtils.getRandom() <= ConfigurationNetwork.MUTATE_CONNECTION_BY_ADDING_NODE_PROBABILITY) {
            genome.mutateConnectionByAddingNode();
        }
        if (RandomUtils.getRandom() <= ConfigurationNetwork.MUTATE_ADDING_CONNECTION_PROBABILITY) {
            genome.mutateWithNewConnection();
        }
        return genome;
    }

    private static List<Double> genomeEvaluate(Genome genome, double[] inputs, NodeFunction nodeFunction) {
        Collection<NodeGene> nodes = genome.getNodesCollection();
        HashMap<Integer, Double> nodeToEvaluation = new HashMap<>();
        while (nodeToEvaluation.size() != nodes.size()) {
            for (NodeGene node : nodes) {
                int nodeId = node.getId();
                if (!nodeToEvaluation.containsKey(nodeId)) {
                    if (node.getType() == NodeGene.Type.INPUT) {
                        nodeToEvaluation.put(nodeId, nodeFunction.function(inputs[node.getInputId()]));
                    } else {
                        addEvaluationToMap(nodeFunction, nodeToEvaluation, node);
                    }
                }
            }
        }
        return genome.getOutputNodes().stream().map(nodeGene -> nodeToEvaluation.get(nodeGene.getId())).collect(Collectors.toList());
    }

    private static void addEvaluationToMap(NodeFunction
                                                   nodeFunction, HashMap<Integer, Double> nodeToEvaluation, NodeGene node) {
        double value = 0;
        for (ConnectionGene backConnection : node.getBackConnections()) {
            if (backConnection.isEnabled()) {
                if (nodeToEvaluation.containsKey(backConnection.getInNode())) {
                    value = value + nodeToEvaluation.get(backConnection.getInNode()) * backConnection.getWeight();
                } else {
                    return;
                }
            }
        }
        nodeToEvaluation.put(node.getId(), nodeFunction.function(value));
    }

    static GenomeEvaluator getGenomeEvaluator(Genome genome, NodeFunction nodeFunction) {
        return inputs -> genomeEvaluate(genome, inputs, nodeFunction);
    }

    static Consumer<Genome> makeGenomeFunctionToUpdateFitness(FitnessCalculator fitnessCalculator, NodeFunction
            nodeFunction) {
        return genome -> genome.setFitness(fitnessCalculator.calculate(getGenomeEvaluator(genome, nodeFunction)));
    }
}
