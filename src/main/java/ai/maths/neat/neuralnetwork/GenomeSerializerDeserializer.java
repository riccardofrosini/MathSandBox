package ai.maths.neat.neuralnetwork;

import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.neuralnetwork.functions.NodeFunction;
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

    private static Genome loadJson(String json) {
        List<NodeGene> genomeDeserialization = GSON.fromJson(json, new TypeToken<List<NodeGene>>() {
        }.getType());
        Genome genome = new Genome();
        for (NodeGene node : genomeDeserialization) {
            Genome.copyNodeTo(node, genome);
        }
        for (ConnectionGene connection : genomeDeserialization.stream().map(NodeGene::getBackConnections).reduce((accumulator, connectionGenes) -> {
            accumulator.addAll(connectionGenes);
            return accumulator;
        }).orElse(new TreeSet<>())) {
            genome.addConnection(connection);
            if (!connection.isEnabled()) {
                genome.getConnectionWithInnovationNumber(connection.getInnovation()).disable();
            }
        }
        return genome;
    }

    public static GenomeEvaluator getEvaluatorFromJson(String json, NodeFunction nodeFunction) {
        Genome genome = loadJson(json);
        return GenomeUtils.getGenomeEvaluator(genome, nodeFunction);

    }
}
