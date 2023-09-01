package ai.maths.sat3.algebraic;

import static ai.maths.sat3.algebraic.Constant.CONSTANT_0;
import static ai.maths.sat3.algebraic.Constant.CONSTANT_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Products extends Formula {

    private final List<Formula> factors;

    protected Products(List<Formula> factors) {
        this.factors = factors;
    }

    @Override
    public Formula simplify() {
        if (factors.contains(CONSTANT_0)) {
            return CONSTANT_0;
        }
        List<Formula> newFactors = new ArrayList<>(factors);

        List<Constant> constants = newFactors.stream().filter(formula -> formula instanceof Constant).map(formula -> (Constant) formula).collect(Collectors.toUnmodifiableList());
        newFactors.removeAll(constants);
        newFactors.add(new Constant(constants.stream().map(Constant::getConstant).reduce(1, (integer, integer2) -> integer * integer2)));
        newFactors.removeIf(factor -> factor.equals(CONSTANT_1));
        if (newFactors.isEmpty()) {
            return CONSTANT_1;
        }
        if (newFactors.size() == 1) {
            return newFactors.iterator().next();
        }
        List<Products> productsSet = newFactors.stream()
                .filter(addon -> addon instanceof Products)
                .map(addon -> (Products) addon)
                .collect(Collectors.toUnmodifiableList());
        newFactors.removeAll(productsSet);
        newFactors.addAll(productsSet.stream().flatMap(factor -> factor.factors.stream())
                .collect(Collectors.groupingBy(formula -> formula)).values().stream()
                .map(formulas -> formulas.stream()
                        .reduce(CONSTANT_1, (formula, formula2) -> new Products(List.of(formula, formula2)).simplify()))
                .collect(Collectors.toUnmodifiableList()));
        Optional<Sums> sumsOptional = newFactors.stream()
                .filter(integer -> integer instanceof Sums).findFirst()
                .map(integer -> (Sums) integer);
        if (sumsOptional.isEmpty() && newFactors.equals(factors)) {
            return this;
        }
        if (sumsOptional.isPresent()) {
            Sums sums = sumsOptional.get();
            newFactors.remove(sums);
            return new Sums(sums.getStreamOfAddends()
                    .collect(Collectors.toUnmodifiableMap(formulaIntegerEntry -> {
                        List<Formula> product = new ArrayList<>(newFactors);
                        product.add(formulaIntegerEntry.getKey());
                        return new Products(product).simplify();
                    }, Entry::getValue, Integer::sum))).simplify();
        }

        return new Products(Collections.unmodifiableList(newFactors)).simplify();
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
