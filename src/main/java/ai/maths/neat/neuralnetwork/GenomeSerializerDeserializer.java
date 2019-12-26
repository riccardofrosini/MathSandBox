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
        int height = 10000;
        int width = 10000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        drawGenomeOnGraphics(genome, height, width, image.getGraphics());
        return image;
    }

    public static void drawGenomeOnGraphics(Genome genome, int width, int height, Graphics graphics) {
        Collection<NodeGene> nodes = genome.getNodesCollection();
        HashMap<Integer, Integer> nodeToLayer = new HashMap<>();
        int maxLayer = 0;
        while (nodeToLayer.size() != nodes.size()) {
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
            nodeToLayer.put(outputNode.getId(), maxLayer + 1);
        }
        TreeMap<Integer, HashSet<Integer>> layerToNode = new TreeMap<>();
        nodeToLayer.forEach((key, value) -> layerToNode.computeIfAbsent(value, k -> new HashSet<>()).add(key));

        HashMap<Integer, Point> nodeToPoint = new HashMap<>();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        Font currentFont = graphics.getFont();
        Font newFont = currentFont.deriveFont((float) currentFont.getSize() * width / 500);
        graphics.setFont(newFont);

        int positionX = width / 50;
        for (Integer layer : layerToNode.keySet()) {
            int positionY = height / 50;
            for (Integer nodeGene : layerToNode.get(layer)) {
                graphics.fillOval(positionX, positionY, width / 100, width / 100);
                graphics.drawString(Integer.toString(nodeGene), positionX, positionY);
                nodeToPoint.put(nodeGene, new Point(positionX, positionY));
                positionY += height / layerToNode.get(layer).size();
            }
            positionX += width / layerToNode.size();
        }


        ((Graphics2D) graphics).setStroke(new BasicStroke(width / 500));
        for (NodeGene node : nodes) {
            for (ConnectionGene connectionGene : node.getBackConnections()) {
                Integer backNodeId = connectionGene.getInNode();
                if (!connectionGene.isEnabled()) {
                    graphics.setColor(Color.RED);
                }
                Point point1 = nodeToPoint.get(node.getId());
                Point point2 = nodeToPoint.get(backNodeId);
                graphics.drawLine(point1.x + width / 200, point1.y + height / 200, point2.x + width / 200, point2.y + height / 200);
                graphics.setColor(Color.BLACK);
                graphics.drawString(String.format("%6.4f", connectionGene.getWeight()), Math.min(point1.x, point2.x) + width / 50 + Math.abs(point2.x - point1.x) / 2, Math.min(point1.y, point2.y) + height / 50 + Math.abs(point2.y - point1.y) / 2);
            }
        }
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
