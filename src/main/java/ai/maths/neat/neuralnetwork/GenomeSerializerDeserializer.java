package ai.maths.neat.neuralnetwork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import java.util.HashSet;
import java.util.List;

public class GenomeSerializerDeserializer {

    public static String toJson(Genome genome) {
        JsonSerializer<NodeGene> nodeGeneJsonSerializer = (nodeGene, type, jsonSerializationContext) -> {
            JsonObject jsonElement = new JsonObject();
            jsonElement.addProperty("id", nodeGene.getId());
            jsonElement.addProperty("inputId", nodeGene.getInputId());
            jsonElement.addProperty("type", nodeGene.getType().name());
            return jsonElement;
        };
        String nodeGeneJason = new GsonBuilder().registerTypeAdapter(NodeGene.class, nodeGeneJsonSerializer).create().toJson(genome.getNodes().values());

        JsonSerializer<ConnectionGene> connectionGeneJsonSerializer = (connectionGene, type, jsonSerializationContext) -> {
            JsonObject jsonElement = new JsonObject();
            jsonElement.addProperty("innovation", connectionGene.getInnovation());
            jsonElement.addProperty("weight", connectionGene.getWeight());
            jsonElement.addProperty("enabled", connectionGene.isEnabled());
            jsonElement.addProperty("inNode", connectionGene.getInNode().getId());
            jsonElement.addProperty("outNode", connectionGene.getOutNode().getId());
            return jsonElement;
        };
        String connectionGeneJson = new GsonBuilder().registerTypeAdapter(ConnectionGene.class, connectionGeneJsonSerializer).create().toJson(genome.getConnections().values());

        return "{\"connections\":" + connectionGeneJson + ",\"nodes\":" + nodeGeneJason + "}";
    }

    public static Genome loadJson(String json) {
        GenomeDeserialization genomeDeserialization = new Gson().fromJson(json, GenomeDeserialization.class);
        Genome genome = new Genome();
        for (NodeGeneDeserialization node : genomeDeserialization.nodes) {
            Genome.copyNodeTo(node.type == NodeGene.Type.INPUT ? new NodeGene(node.id, node.inputId) : new NodeGene(node.id, node.type), genome);
        }
        for (ConnectionGeneDeserialization connection : genomeDeserialization.connections) {
            genome.addConnection(genome.getNodes().get(connection.inNode), genome.getNodes().get(connection.outNode), connection.weight);
            if (!connection.enabled) {
                genome.getConnections().get(connection.innovation).disable();
            }
        }
        return genome;
    }

    public static void main(String[] args) {
        HashSet<Genome> genomeRandom = GenomeUtils.makeRandomTopologyGenomes(2, 3, genome -> genome.setFitness(1));
        for (Genome genome : genomeRandom) {
            String json = toJson(genome);
            System.out.println(json);
            Genome genome1 = loadJson(json);
            System.out.println(toJson(genome1));
        }

    }

    private class GenomeDeserialization {
        private List<NodeGeneDeserialization> nodes;
        private List<ConnectionGeneDeserialization> connections;
    }

    private class ConnectionGeneDeserialization {
        private int inNode;
        private int outNode;
        private int innovation;
        private double weight;
        private boolean enabled;
    }

    private class NodeGeneDeserialization {
        private int inputId;
        private int id;
        private NodeGene.Type type;
    }
}
