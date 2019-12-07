package ai.maths.neat.utils;

import java.util.HashMap;

public class NodeCounter {

    private static int id = 0;

    private static final HashMap<Integer, Integer> innovationsHistory = new HashMap<>();

    public static int geNewIdForInputOutput() {
        id++;
        return id;
    }

    public static int geNewIdForHidden(int innovation) {
        if (innovationsHistory.containsKey(innovation)) {
            return innovationsHistory.get(innovation);
        } else {
            id++;
            innovationsHistory.put(innovation, id);
            return id;
        }

    }

}
