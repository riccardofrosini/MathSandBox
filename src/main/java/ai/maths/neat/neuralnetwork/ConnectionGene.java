package ai.maths.neat.neuralnetwork;


import ai.maths.neat.utils.ConstantsAndUtils;

import java.util.Objects;

public class ConnectionGene implements Comparable<ConnectionGene> {

    private NodeGene inNode;
    private NodeGene outNode;
    private double weight;
    private int innovation;
    private boolean enabled;

    ConnectionGene(NodeGene inNode, NodeGene outNode, double weight) {
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.innovation = ConstantsAndUtils.generateInnovation(inNode, outNode);
        this.enabled = true;
    }

    public NodeGene getInNode() {
        return inNode;
    }

    public NodeGene getOutNode() {
        return outNode;
    }

    public double getWeight() {
        return weight;
    }

    public int getInnovation() {
        return innovation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    public void disable() {
        this.enabled = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionGene)) return false;
        ConnectionGene that = (ConnectionGene) o;
        return Double.compare(that.weight, weight) == 0 &&
                innovation == that.innovation &&
                enabled == that.enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, innovation, enabled);
    }

    @Override
    public int compareTo(ConnectionGene o) {
        if (o.innovation > innovation) {
            return -1;
        }
        if (o.innovation < innovation) {
            return 1;
        }
        return equals(o) ? 0 : hashCode() - o.hashCode() < 0 ? -1 : 1;
    }
}
