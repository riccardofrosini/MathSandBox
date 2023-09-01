package ai.maths.sat3.algebraic;

import static ai.maths.sat3.algebraic.Constant.CONSTANT_0;
import static ai.maths.sat3.algebraic.Constant.CONSTANT_1;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Formula {

    public abstract Formula simplify();

    public static Formula buildSumsForIntersection(Formula formula1, Formula formula2, Formula intersectionFormula) {
        if (formula1.equals(intersectionFormula)) {
            return formula2;
        }
        if (formula2.equals(intersectionFormula)) {
            return formula1;
        }
        return new Sums(Map.of(formula1, 1, formula2, 1, intersectionFormula, -1)).simplify();
    }

    public static Formula buildSumsForNegation(Formula formula) {
        return new Sums(Map.of(CONSTANT_1, 1, formula, -1)).simplify();
    }

    public static Formula buildProducts(List<Formula> factors) {
        return new Products(Collections.unmodifiableList(factors)).simplify();
    }

    public static Formula buildSingletonVariable(String name) {
        return new SingletonVariable(name);
    }

    public static Formula buildConstant(Integer constant) {
        if (constant == 1) {
            return CONSTANT_1;
        }
        if (constant == 0) {
            return CONSTANT_0;
        }
        return new Constant(constant);
    }
}
