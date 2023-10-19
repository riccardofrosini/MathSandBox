package ai.maths.sat3.model;

public class VariableNegation extends Negation<Variable> implements Singleton {

    protected VariableNegation(Variable variable) {
        super(variable);
    }
}
