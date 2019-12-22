package ai.maths.neat.neuralnetwork;

import ai.maths.neat.utils.ConfigurationNetwork;

import java.util.HashMap;

class NodeAndConnectionCounter {

    private static int idNode = 0;
    private static int idConnection = 0;

    private static HashMap<Integer, Integer> innovationsNodeHistory = new HashMap<>();
    private static HashMap<Integer, Integer> innovationsConnectionHistory = new HashMap<>();

    static int getNewIdForInputOutputNode() {
        idNode++;
        return idNode;
    }

    static int getNewIdForHiddenNode(int innovation) {
        if (innovationsNodeHistory.containsKey(innovation)) {
            return innovationsNodeHistory.get(innovation);
        } else {
            idNode++;
            innovationsNodeHistory.put(innovation, idNode);
            return idNode;
        }
    }

    static int getNewInnovationForConnection(int inNode, int outNode) {
        int innovation = inNode + outNode * ConfigurationNetwork.MAX_NODES;
        if (innovationsConnectionHistory.containsKey(innovation)) {
            return innovationsConnectionHistory.get(innovation);
        } else {
            idConnection++;
            innovationsConnectionHistory.put(innovation, idConnection);
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
