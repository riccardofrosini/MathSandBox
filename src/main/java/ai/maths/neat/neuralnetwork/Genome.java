package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConstantsAndUtils;
import ai.maths.neat.utils.NodeCounter;

import java.util.*;
import java.util.stream.Collectors;

public class Genome implements Comparable<Genome> {


    private SortedMap<Integer, ConnectionGene> connections;
    private HashMap<Integer, NodeGene> nodes;
    private ArrayList<NodeGene> outputNodes;
    private float fitness;

    public Genome() {
        outputNodes = new ArrayList<>();
        connections = new TreeMap<>();
        nodes = new HashMap<>();
    }

    private void addConnection(NodeGene inNode, NodeGene outNode, float weight) {
        ConnectionGene connectionGene = new ConnectionGene(inNode, outNode, weight);
        outNode.addBackConnection(connectionGene);
        connections.put(connectionGene.getInnovation(), connectionGene);
    }

    public void mutateConnectionByAddingNode() {
        if (nodes.size() >= ConstantsAndUtils.MAX_NODES) {
            return;
        }
        if (!connections.isEmpty()) {
            ConnectionGene[] allConnections = connections.values().toArray(new ConnectionGene[0]);
            ConnectionGene connectionGene = allConnections[ConstantsAndUtils.getRandomInt(allConnections.length)];
            boolean mutate = false;
            for (int i = 0; i < 10000 && !connectionGene.isEnabled(); i++) {
                connectionGene = allConnections[ConstantsAndUtils.getRandomInt(allConnections.length)];
                mutate = true;
            }
            if (mutate) {
                connectionGene.disable();
                NodeGene nodeGene = new NodeGene(NodeCounter.geNewIdForHidden(connectionGene.getInnovation()), NodeGene.Type.HIDDEN);
                nodes.put(nodeGene.getId(), nodeGene);
                addConnection(connectionGene.getInNode(), nodeGene, 1);
                addConnection(nodeGene, connectionGene.getOutNode(), connectionGene.getWeight());
            }
        }
    }

    public void mutateWithNewConnection() {
        NodeGene[] nodeGenes = nodes.values().toArray(new NodeGene[0]);
        NodeGene inNode = nodeGenes[ConstantsAndUtils.getRandomInt(nodeGenes.length)];
        NodeGene outNode = nodeGenes[ConstantsAndUtils.getRandomInt(nodeGenes.length)];
        // max loops as this could go on forever.
        for (int i = 0; i < 10000 && !makeConnection(outNode, inNode, ConstantsAndUtils.getRandomWeight()); i++) {
            inNode = nodeGenes[ConstantsAndUtils.getRandomInt(nodeGenes.length)];
            outNode = nodeGenes[ConstantsAndUtils.getRandomInt(nodeGenes.length)];
        }
    }

    public void mutateConnectionWeights() {
        for (ConnectionGene connectionGene : connections.values()) {
            if (ConstantsAndUtils.getRandom() <= ConstantsAndUtils.WEIGHT_PERTURBATION_PROBABILITY) {
                float newWeight = connectionGene.getWeight() + ConstantsAndUtils.getRandomPerturbation();
                connectionGene.setWeight(Math.min(Math.max(newWeight, -1), 1));
            } else {
                connectionGene.setWeight(ConstantsAndUtils.getRandomWeight());
            }
        }
    }

    public boolean makeConnection(NodeGene inNode, NodeGene outNode, float weight) {
        if (!inNode.equals(outNode) && !connections.containsKey(ConstantsAndUtils.generateInnovation(inNode, outNode)) &&
                outNode.getType() != NodeGene.Type.INPUT &&
                inNode.getType() != NodeGene.Type.OUTPUT && !containsBackwardConnection(inNode, outNode)) {
            addConnection(inNode, outNode, weight);
            return true;
        }
        return false;
    }

    public void addInputNode(int inputId) {
        NodeGene nodeGene = new NodeGene(NodeCounter.geNewIdForInputOutput(), inputId);
        nodes.put(nodeGene.getId(), nodeGene);
    }

    public void addOutputNode() {
        NodeGene nodeGene = new NodeGene(NodeCounter.geNewIdForInputOutput(), NodeGene.Type.OUTPUT);
        nodes.put(nodeGene.getId(), nodeGene);
        outputNodes.add(nodeGene);
    }

    private boolean containsBackwardConnection(NodeGene inNode, NodeGene outNode) {
        Set<NodeGene> backConnections = inNode.getBackConnections().stream().map(ConnectionGene::getInNode).collect(Collectors.toSet());
        HashSet<NodeGene> nodeGenes = new HashSet<>();
        while (!backConnections.isEmpty()) {
            if (backConnections.contains(outNode)) {
                return true;
            }
            for (NodeGene backConnection : backConnections) {
                nodeGenes.addAll(backConnection.getBackConnections().stream().map(ConnectionGene::getInNode).collect(Collectors.toSet()));
            }
            backConnections = nodeGenes;
            nodeGenes = new HashSet<>();
        }
        return false;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public SortedMap<Integer, ConnectionGene> getConnections() {
        return connections;
    }

    public HashMap<Integer, NodeGene> getNodes() {
        return nodes;
    }

    @Override
    public Genome clone() {
        Genome clone = new Genome();
        copyNodesTo(clone);
        for (ConnectionGene connection : connections.values()) {
            clone.addConnection(clone.nodes.get(connection.getInNode().getId()), clone.nodes.get(connection.getOutNode().getId()), connection.getWeight());
        }
        return clone;
    }

    public void copyNodesTo(Genome clone) {
        for (NodeGene node : nodes.values()) {
            NodeGene nodeGene = node.getType() == NodeGene.Type.INPUT ?
                    new NodeGene(node.getId(), node.getInputId()) :
                    new NodeGene(node.getId(), node.getType());
            clone.nodes.put(node.getId(), nodeGene);
            if (node.getType() == NodeGene.Type.OUTPUT && !clone.outputNodes.contains(nodeGene)) {
                clone.outputNodes.add(nodeGene);
            }
        }
    }

    public ArrayList<NodeGene> getOutputNodes() {
        return outputNodes;
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