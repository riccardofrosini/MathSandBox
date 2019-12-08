package ai.maths.neat.neuralnetwork;

import java.util.HashMap;

class NodeCounter {

    private static int id = 0;

    private static HashMap<Integer, Integer> innovationsHistory = new HashMap<>();

    static int geNewIdForInputOutput() {
        id++;
        return id;
    }

    static int geNewIdForHidden(int innovation) {
        if (innovationsHistory.containsKey(innovation)) {
            return innovationsHistory.get(innovation);
        } else {
            id++;
            innovationsHistory.put(innovation, id);
            return id;
        }
    }

    static void resetCounter() {
        id = 0;
        innovationsHistory = new HashMap<>();
    }

}
