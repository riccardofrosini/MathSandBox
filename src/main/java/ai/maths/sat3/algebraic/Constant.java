package ai.maths.sat3.algebraic;

import java.util.Objects;

public class Constant extends Formula {

    private final Integer constant;

    public static final Constant CONSTANT_0 = new Constant(0);
    public static final Constant CONSTANT_1 = new Constant(1);

    protected Constant(Integer constant) {
        this.constant = constant;
    }

    @Override
    public Formula simplify() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Constant constant1 = (Constant) o;
        return constant.equals(constant1.constant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant);
    }

    @Override
    public String toString() {
        return constant.toString();
    }
}
