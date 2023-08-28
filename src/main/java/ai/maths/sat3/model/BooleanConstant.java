package ai.maths.sat3.model;

import java.util.Objects;

public final class BooleanConstant extends VariableOrBoolean {

    public static final BooleanConstant TRUE_CONSTANT = new BooleanConstant(true);
    public static final BooleanConstant FALSE_CONSTANT = new BooleanConstant(false);

    private final boolean constant;

    private BooleanConstant(boolean constant) {
        this.constant = constant;
    }

    @Override
    public BooleanConstant getVariableOrBoolean() {
        return this;
    }

    @Override
    public boolean isEqualNegated(SingletonClause other) {
        return (other instanceof BooleanConstant) && constant != ((BooleanConstant) other).constant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BooleanConstant that = (BooleanConstant) o;
        return constant == that.constant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant);
    }

    @Override
    public String toString() {
        return String.valueOf(constant).toUpperCase();
    }
}
