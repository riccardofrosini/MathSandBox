package ai.maths.neat.neuralnetwork;

import java.util.Objects;
import java.util.TreeSet;

class NodeGene {

    enum Type {
        INPUT,
        HIDDEN,
        OUTPUT
    }

    private int inputId;
    private final int id;
    private final Type type;
    private final TreeSet<ConnectionGene> backConnections;

    NodeGene(int id, Type type) {
        this.id = id;
        this.type = type;
        this.backConnections = new TreeSet<>();
    }

    NodeGene(int id, int inputId) {
        this.inputId = inputId;
        this.id = id;
        this.type = Type.INPUT;
        this.backConnections = new TreeSet<>();
    }

    //Genome will check for duplicates and disabled connections
    void addBackConnection(ConnectionGene connectionGene) {
        if (id == connectionGene.getOutNode()) {
            backConnections.add(connectionGene);
        } else {
            throw new RuntimeException("Adding connection with wrong outNodeId");
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

    TreeSet<ConnectionGene> getBackConnections() {
        return backConnections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NodeGene)) {
            return false;
        }
        NodeGene nodeGene = (NodeGene) o;
        return id == nodeGene.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
