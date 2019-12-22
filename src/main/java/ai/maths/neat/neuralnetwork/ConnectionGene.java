package ai.maths.neat.neuralnetwork;

import java.util.Objects;

class ConnectionGene implements Comparable<ConnectionGene> {

    private final int inNode;
    private final int outNode;
    private final int innovation;
    private double weight;
    private boolean enabled;

    ConnectionGene(int inNode, int outNode, double weight) {
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.innovation = NodeAndConnectionCounter.getNewInnovationForConnection(inNode, outNode);
        this.enabled = true;
    }

    int getInNode() {
        return inNode;
    }

    int getOutNode() {
        return outNode;
    }

    double getWeight() {
        return weight;
    }

    int getInnovation() {
        return innovation;
    }

    boolean isEnabled() {
        return enabled;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    void disable() {
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
