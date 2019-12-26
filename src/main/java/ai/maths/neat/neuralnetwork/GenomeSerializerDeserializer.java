package ai.maths.neat.neuralnetwork;

import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.neuralnetwork.functions.NodeFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

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
        genomeDeserialization.stream().map(NodeGene::getBackConnections).reduce((accumulator, connectionGenes) -> {
            accumulator.addAll(connectionGenes);
            return accumulator;
        }).orElse(new TreeSet<>()).forEach(connection -> {
            genome.addConnection(connection);
            if (!connection.isEnabled()) {
                genome.getConnectionWithInnovationNumber(connection.getInnovation()).disable();
            }
        });
        return genome;
    }

    public static GenomeEvaluator getEvaluatorFromJson(String json, NodeFunction nodeFunction) {
        Genome genome = loadJson(json);
        return GenomeUtils.getGenomeEvaluator(genome, nodeFunction);
    }


    public static void drawNetworkToFile(String json, File file) throws IOException {
        ImageIO.write(getBufferedImageFromGenome(loadJson(json)), "jpg", file);
    }

    static BufferedImage getBufferedImageFromGenome(Genome genome) {
        Collection<NodeGene> nodes = genome.getNodesCollection();
        HashMap<Integer, Integer> nodeToLayer = new HashMap<>();
        int maxLayer = 0;
        while (!nodeToLayer.keySet().containsAll(nodes)) {
            for (NodeGene node : nodes) {
                int nodeId = node.getId();
                if (!nodeToLayer.containsKey(nodeId)) {
                    if (node.getType() != NodeGene.Type.HIDDEN) {
                        nodeToLayer.put(nodeId, 0); //output nodes will be set later
                    } else {
                        maxLayer = getMaxLayer(nodeToLayer, maxLayer, node);
                    }
                }
            }
        }
        for (NodeGene outputNode : genome.getOutputNodes()) {
            nodeToLayer.put(outputNode.getId(), maxLayer);
        }
        TreeMap<Integer, HashSet<Integer>> layerToNode = new TreeMap<>();
        nodeToLayer.forEach((key, value) -> layerToNode.computeIfAbsent(value, k -> new HashSet<>()).add(key));

        int height = 10000;
        int width = 10000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        HashMap<Integer, Point> nodeToPoint = new HashMap<>();
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(Color.BLACK);
        Font currentFont = graphics.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 5f);
        graphics.setFont(newFont);

        int positionX = 50;
        for (Integer layer : layerToNode.keySet()) {
            int positionY = 50;
            for (Integer nodeGene : layerToNode.get(layer)) {
                graphics.fillOval(positionX, positionY, 100, 100);
                graphics.drawString(Integer.toString(nodeGene), positionX, positionY);
                nodeToPoint.put(nodeGene, new Point(positionX, positionY));
                positionY += height / layerToNode.get(layer).size();
            }
            positionX += width / layerToNode.size();
        }

        for (NodeGene node : nodes) {
            for (ConnectionGene connectionGene : node.getBackConnections()) {
                Integer backNodeId = connectionGene.getInNode();
                if (!connectionGene.isEnabled()) {
                    graphics.setColor(Color.RED);
                }
                Point point1 = nodeToPoint.get(node.getId());
                Point point2 = nodeToPoint.get(backNodeId);
                ((Graphics2D) graphics).setStroke(new BasicStroke(10));
                graphics.drawLine(point1.x + 50, point1.y + 50, point2.x + 50, point2.y + 50);
                graphics.setColor(Color.BLACK);
                graphics.drawString(String.format("%6.4f", connectionGene.getWeight()), Math.min(point1.x, point2.x) + 50 + Math.abs(point2.x - point1.x) / 2, Math.min(point1.y, point2.y) + 50 + Math.abs(point2.y - point1.y) / 2);
            }
        }
        return image;
    }

    private static int getMaxLayer(HashMap<Integer, Integer> nodeToLayer, int maxLayer, NodeGene node) {
        int layer = 0;
        for (ConnectionGene backConnection : node.getBackConnections()) {
            if (nodeToLayer.containsKey(backConnection.getInNode())) {
                layer = Math.max(nodeToLayer.get(backConnection.getInNode()) + 1, layer);
            } else {
                return maxLayer;
            }
        }
        maxLayer = Math.max(layer, maxLayer);
        nodeToLayer.put(node.getId(), layer);
        return maxLayer;
    }

}
