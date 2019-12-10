package ai.maths.neat.utils;

import ai.maths.neat.neuralnetwork.functions.NodeFunction;

public class NodeFunctionsCreator {

    public static NodeFunction getSigmoid(double constant) {
        return in -> (1 / (1 + Math.pow(Math.E, -in * constant)));
    }

    public static NodeFunction getSimpleSigmoid() {
        return getSigmoid(1);
    }

    public static NodeFunction rectifiedLinearUnit() {
        return in -> in < 0 ? 0 : in;
    }

    public static NodeFunction linear(double constant) {
        return in -> in * constant;
    }

    public static NodeFunction linearUnit() {
        return linear(1);
    }
}
