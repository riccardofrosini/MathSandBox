package ai.maths.sat3.algebraic;

import static ai.maths.sat3.algebraic.Constant.CONSTANT_0;
import static ai.maths.sat3.algebraic.Constant.CONSTANT_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Products extends Formula {

    private final Set<NotAProduct> factors;

    protected Products(List<NotAProduct> factors) {
        List<Constant> constants = factors.stream()
                .filter(formula -> formula instanceof Constant)
                .map(formula -> (Constant) formula)
                .collect(Collectors.toList());
        factors.removeAll(constants);
        Constant constant = constants.stream().reduce(CONSTANT_1, (constant1, constant2) -> new Constant(constant1.getConstant() * constant2.getConstant()));
        factors.add(constant);
        factors.removeIf(factor -> factor.equals(CONSTANT_1));
        Set<NotAProduct> formulas = Set.copyOf(factors);
        if (formulas.size() != factors.size()) {
            System.out.println("THE CODE SHOULD NEVER EVER ENTER HERE!");
        }
        this.factors = formulas;
    }

    @Override
    public Formula simplify() {
        if (factors.isEmpty()) {
            return CONSTANT_1;
        }
        if (factors.contains(CONSTANT_0)) {
            return CONSTANT_0;
        }
        if (factors.size() == 1) {
            return factors.iterator().next();
        }
        List<NotAProduct> newFactors = new ArrayList<>(factors);

        Optional<Sums> sumsOptional = newFactors.stream()
                .filter(addend -> addend instanceof Sums).findFirst()
                .map(integer -> (Sums) integer);
        if (sumsOptional.isEmpty() && newFactors.containsAll(factors) && newFactors.size() == factors.size()) {
            return this;
        }
        if (sumsOptional.isPresent()) {
            Sums sums = sumsOptional.get();
            newFactors.remove(sums);
            return new Sums(sums.getStreamOfAddends()
                    .collect(Collectors.toMap(formulaIntegerEntry -> {
                        List<NotAProduct> product = new ArrayList<>(newFactors);
                        Formula key = formulaIntegerEntry.getKey();
                        if (key instanceof Products) {
                            product.addAll(((Products) key).factors);
                        } else {
                            product.add((NotAProduct) key);
                        }
                        return new Products(product).simplify();
                    }, Entry::getValue, Integer::sum))).simplify();
        }
        return new Products(newFactors).simplify();
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
