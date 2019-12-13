package ai.maths.neat.neuralnetwork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.TreeSet;

public class GenomeSerializerDeserializer {


    private static final Gson GSON = new GsonBuilder().create();

    static String toJson(Genome genome) {
        return GSON.toJson(genome.getNodesCollection());
    }

    static Genome loadJson(String json) {
        List<NodeGene> genomeDeserialization = GSON.fromJson(json, new TypeToken<List<NodeGene>>() {
        }.getType());
        Genome genome = new Genome();
        for (NodeGene node : genomeDeserialization) {
            Genome.copyNodeTo(node.getType() == NodeGene.Type.INPUT ? new NodeGene(node.getId(), node.getInputId()) : new NodeGene(node.getId(), node.getType()), genome);
        }
        for (ConnectionGene connection : genomeDeserialization.stream().map(NodeGene::getBackConnections).reduce((accumulator, connectionGenes) -> {
            accumulator.addAll(connectionGenes);
            return accumulator;
        }).orElse(new TreeSet<>())) {
            genome.addConnection(connection.getInNode(), connection.getOutNode(), connection.getWeight());
            if (!connection.isEnabled()) {
                genome.getConnectionWithInnovationNumber(connection.getInnovation()).disable();
            }
        }
        return genome;
    }
}
