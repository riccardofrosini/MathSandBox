package ai.maths.sat3.model;

public class NegVariable extends Negation<Variable> implements Singleton {

    protected NegVariable(Variable variable) {
        super(variable);
    }
}
