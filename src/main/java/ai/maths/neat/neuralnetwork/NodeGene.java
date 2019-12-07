package ai.maths.neat.neuralnetwork;

import java.util.HashSet;
import java.util.Objects;

class NodeGene {

    public enum Type {
        INPUT,
        HIDDEN,
        OUTPUT
    }

    private int inputId;
    private final int id;
    private final Type type;
    private final HashSet<ConnectionGene> backConnections;

    NodeGene(int id, Type type) {
        this.id = id;
        this.type = type;
        this.backConnections = new HashSet<>();
    }

    NodeGene(int id, int inputId) {
        this.inputId = inputId;
        this.id = id;
        this.type = Type.INPUT;
        this.backConnections = new HashSet<>(0);
    }

    //Genome will check for duplicates and disabled connections
    void addBackConnection(ConnectionGene connectionGene) {
        if (connectionGene.getOutNode().equals(this)) {
            backConnections.add(connectionGene);
        }
    }

    int getId() {
        return id;
    }

    Type getType() {
        return type;
    }

    int getInputId() {
        return inputId;
    }

    HashSet<ConnectionGene> getBackConnections() {
        return backConnections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeGene)) return false;
        NodeGene nodeGene = (NodeGene) o;
        return id == nodeGene.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
