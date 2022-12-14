package ai.maths.neat.neuralnetwork;

import java.util.HashMap;

import ai.maths.neat.utils.ConfigurationNetwork;

class NodeAndConnectionCounter {

    private static int idNode = 0;
    private static int idConnection = 0;

    private static HashMap<Integer, Integer> innovationsNodeHistory = new HashMap<>();
    private static HashMap<Integer, Integer> innovationsConnectionHistory = new HashMap<>();

    static int getNewIdForInputOutputNode() {
        idNode++;
        return idNode;
    }

    static int getIdForHiddenNode(int innovation) {
        Integer id = innovationsNodeHistory.get(innovation);
        if (id != null) {
            return id;
        } else {
            idNode++;
            innovationsNodeHistory.put(innovation, idNode);
            return idNode;
        }
    }

    static int getInnovationForConnection(int inNode, int outNode) {
        int nodesConnectionId = inNode + outNode * ConfigurationNetwork.MAX_NODES;
        Integer id = innovationsConnectionHistory.get(nodesConnectionId);
        if (id != null) {
            return id;
        } else {
            idConnection++;
            innovationsConnectionHistory.put(nodesConnectionId, idConnection);
            return idConnection;
        }
    }

    static void resetCounter() {
        idNode = 0;
        idConnection = 0;
        innovationsNodeHistory = new HashMap<>();
        innovationsConnectionHistory = new HashMap<>();
    }

}
