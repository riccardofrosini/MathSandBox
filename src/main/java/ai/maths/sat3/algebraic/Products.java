package ai.maths.sat3.algebraic;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Products extends Formula {

    private final Set<Formula> factors;

    public Products(Set<Formula> factors) {
        this.factors = factors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Products products = (Products) o;
        return factors.equals(products.factors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factors);
    }

    @Override
    public String toString() {
        return factors.stream()
                .map(Object::toString)
                .collect(Collectors.joining("*"));
    }
}
