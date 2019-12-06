package ai.maths.neat.functions;

public class SigmoidFunction implements NodeFunction {

    private float constant;

    public SigmoidFunction() {
        constant = 1;
    }

    public SigmoidFunction(float constant) {
        this.constant = constant;
    }

    @Override
    public float function(float in) {
        return (float) (1 / (1 + Math.pow(Math.E, -in * constant)));
    }
}
