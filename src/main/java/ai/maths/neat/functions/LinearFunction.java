package ai.maths.neat.functions;

public class LinearFunction implements NodeFunction {
    @Override
    public double function(double in) {
        return in < 0 ? 0 : in;
    }
}
