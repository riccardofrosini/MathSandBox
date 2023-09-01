package ai.maths.sat3.algebraic;

import java.util.Objects;

public class Constant extends Formula {

    private final Integer constant;

    public Constant(Integer constant) {
        this.constant = constant;
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
