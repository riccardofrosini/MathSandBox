package ai.maths.neat.functions;

public class SigmoidFunction implements NodeFunction {

    private final double constant;

    public SigmoidFunction() {
        constant = 1;
    }

    public SigmoidFunction(double constant) {
        this.constant = constant;
    }

    @Override
    public double function(double in) {
        return (1 / (1 + Math.pow(Math.E, -in * constant)));
    }
}
