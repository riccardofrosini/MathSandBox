package ai.maths.sat3.algebraic;

import static ai.maths.sat3.algebraic.Constant.CONSTANT_0;
import static ai.maths.sat3.algebraic.Constant.CONSTANT_1;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Products extends Formula {

    private final Set<Formula> factors;

    protected Products(Set<Formula> factors) {
        this.factors = factors;
    }

    @Override
    public Formula simplify() {
        if (factors.contains(CONSTANT_0)) {
            return CONSTANT_0;
        }
        HashSet<Formula> newFactors = new HashSet<>(factors);
        newFactors.removeIf(factor -> factor.equals(CONSTANT_1));
        if (newFactors.isEmpty()) {
            return CONSTANT_1;
        }
        if (newFactors.size() == 1) {
            return newFactors.iterator().next();

        }
        Set<Products> productsSet = newFactors.stream()
                .filter(integer -> integer instanceof Products)
                .map(integer -> (Products) integer)
                .collect(Collectors.toUnmodifiableSet());
        newFactors.removeAll(productsSet);
        if (newFactors.equals(factors)) {
            return this;
        }
        Set<Formula> finalFactors = Stream.concat(productsSet.stream().flatMap(factor -> factor.factors.stream()), newFactors.stream())
                .collect(Collectors.toUnmodifiableSet());
        return new Products(finalFactors).simplify();
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
