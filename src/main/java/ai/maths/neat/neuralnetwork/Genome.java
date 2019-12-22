package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConfigurationNetwork;
import ai.maths.neat.utils.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

class Genome implements Comparable<Genome> {

    private final SortedMap<Integer, ConnectionGene> connections;
    private final HashMap<Integer, NodeGene> nodes;
    private final ArrayList<NodeGene> outputNodes;
    private double fitness;

    Genome() {
        outputNodes = new ArrayList<>();
        connections = new TreeMap<>();
        nodes = new HashMap<>();
    }

    void addConnection(int inNodeId, int outNodeId, double weight) {
        if (!nodes.containsKey(outNodeId) || !nodes.containsKey(inNodeId)) {
            throw new RuntimeException("Missing nodes when adding connections.");
        }
        ConnectionGene connectionGene = new ConnectionGene(inNodeId, outNodeId, weight);
        nodes.get(outNodeId).addBackConnection(connectionGene);
        connections.put(connectionGene.getInnovation(), connectionGene);
    }

    void mutateConnectionByAddingNode() {
        if (nodes.size() >= ConfigurationNetwork.MAX_NODES) {
            return;
        }
        if (!connections.isEmpty()) {
            ConnectionGene[] allConnections = connections.values().toArray(new ConnectionGene[0]);
            ConnectionGene connectionGene = allConnections[RandomUtils.getRandomInt(allConnections.length)];
            int newIdForHiddenNode = NodeAndConnectionCounter.getNewIdForHiddenNode(connectionGene.getInnovation());
            for (int i = 0; i < 10000 && (!connectionGene.isEnabled() ||
                    nodes.containsKey(newIdForHiddenNode)); i++) {
                connectionGene = allConnections[RandomUtils.getRandomInt(allConnections.length)];
                newIdForHiddenNode = NodeAndConnectionCounter.getNewIdForHiddenNode(connectionGene.getInnovation());
            }
            if (connectionGene.isEnabled() && !nodes.containsKey(newIdForHiddenNode)) {
                connectionGene.disable();
                NodeGene nodeGene = new NodeGene(newIdForHiddenNode, NodeGene.Type.HIDDEN);
                nodes.put(nodeGene.getId(), nodeGene);
                addConnection(connectionGene.getInNode(), nodeGene.getId(), 1);
                addConnection(nodeGene.getId(), connectionGene.getOutNode(), connectionGene.getWeight());
            }
        }
    }

    void mutateWithNewConnection() {
        NodeGene[] nodeGenes = nodes.values().toArray(new NodeGene[0]);
        NodeGene inNode = nodeGenes[RandomUtils.getRandomInt(nodeGenes.length)];
        NodeGene outNode = nodeGenes[RandomUtils.getRandomInt(nodeGenes.length)];
        // max loops as this could go on forever.
        for (int i = 0; i < 10000 && !makeNewConnection(inNode.getId(), outNode.getId(), RandomUtils.getRandomWeight()); i++) {
            inNode = nodeGenes[RandomUtils.getRandomInt(nodeGenes.length)];
            outNode = nodeGenes[RandomUtils.getRandomInt(nodeGenes.length)];
        }
    }

    void mutateConnectionWeights() {
        for (ConnectionGene connectionGene : connections.values()) {
            if (RandomUtils.getRandom() <= ConfigurationNetwork.WEIGHT_PERTURBATION_PROBABILITY) {
                double newWeight = connectionGene.getWeight() + RandomUtils.getRandomPerturbation();
                connectionGene.setWeight(Math.min(Math.max(newWeight, -1), 1));
            } else {
                connectionGene.setWeight(RandomUtils.getRandomWeight());
            }
        }
    }

    private boolean makeNewConnection(int inNodeId, int outNodeId, double weight) {
        if (connections.containsKey(NodeAndConnectionCounter.getNewInnovationForConnection(inNodeId, outNodeId))) {
            return false;
        }
        return replaceOrMakeNewConnection(inNodeId, outNodeId, weight);
    }

    boolean replaceOrMakeNewConnection(int inNodeId, int outNodeId, double weight) {
        NodeGene inNode = nodes.get(inNodeId);
        NodeGene outNode = nodes.get(outNodeId);
        if (inNodeId != outNodeId &&
                outNode.getType() != NodeGene.Type.INPUT &&
                inNode.getType() != NodeGene.Type.OUTPUT && !containsBackwardConnection(inNode, outNode)) {
            outNode.getBackConnections().removeIf(connectionGene -> connectionGene.getInNode() == inNodeId);
            addConnection(inNodeId, outNodeId, weight);
            return true;
        }
        return false;
    }

    void addInputNode(int inputId) {
        NodeGene nodeGene = new NodeGene(NodeAndConnectionCounter.getNewIdForInputOutputNode(), inputId);
        nodes.put(nodeGene.getId(), nodeGene);
    }

    void addOutputNode() {
        NodeGene nodeGene = new NodeGene(NodeAndConnectionCounter.getNewIdForInputOutputNode(), NodeGene.Type.OUTPUT);
        nodes.put(nodeGene.getId(), nodeGene);
        outputNodes.add(nodeGene);
    }

    private boolean containsBackwardConnection(NodeGene inNode, NodeGene outNode) {
        Set<Integer> backConnections = inNode.getBackConnections().stream().map(ConnectionGene::getInNode).collect(Collectors.toSet());
        while (!backConnections.isEmpty()) {
            if (backConnections.contains(outNode.getId())) {
                return true;
            }
            HashSet<Integer> tempNodeGenes = new HashSet<>();
            for (Integer backConnection : backConnections) {
                tempNodeGenes.addAll(nodes.get(backConnection).getBackConnections().stream().map(ConnectionGene::getInNode).collect(Collectors.toSet()));
            }
            backConnections = tempNodeGenes;
        }
        return false;
    }

    double getFitness() {
        return fitness;
    }

    void setFitness(double fitness) {
        this.fitness = fitness;
    }

    Collection<ConnectionGene> getConnectionsCollection() {
        return connections.values();
    }

    int getNumberOfConnections() {
        return connections.size();
    }

    ConnectionGene getConnectionWithInnovationNumber(int innovation) {
        return connections.get(innovation);
    }

    NodeGene getNodeWithId(int nodeId) {
        return nodes.get(nodeId);
    }

    Collection<NodeGene> getNodesCollection() {
        return nodes.values();
    }

    ArrayList<NodeGene> getOutputNodes() {
        return outputNodes;
    }

    void copyNodesTo(Genome clone) {
        for (NodeGene node : nodes.values()) {
            copyNodeTo(node, clone);
        }
    }

    static void copyNodeTo(NodeGene node, Genome clone) {
        NodeGene nodeGene = node.getType() == NodeGene.Type.INPUT ?
                new NodeGene(node.getId(), node.getInputId()) :
                new NodeGene(node.getId(), node.getType());
        clone.nodes.put(node.getId(), nodeGene);
        if (node.getType() == NodeGene.Type.OUTPUT && !clone.outputNodes.contains(nodeGene)) {
            clone.outputNodes.add(nodeGene);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genome)) return false;
        Genome genome = (Genome) o;
        return connections.equals(genome.connections) &&
                nodes.equals(genome.nodes) &&
                outputNodes.equals(genome.outputNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connections, nodes, outputNodes);
    }

    @Override
    public int compareTo(Genome o) {
        if (o.fitness > fitness) {
            return -1;
        }
        if (o.fitness < fitness) {
            return 1;
        }
        return equals(o) ? 0 : hashCode() - o.hashCode() < 0 ? -1 : 1;
    }
}