package ai.maths.euler.p41to60;

import java.util.HashMap;
import java.util.Map;

public class Euler43 {

    public static void main(String[] args) {
        System.out.println(nPandigitalWithProperty());
    }

    private static long nPandigitalWithProperty() {
        HashMap<Integer, Boolean> intsUsed = new HashMap<>(10);
        for (int i = 0; i < 10; i++) {
            intsUsed.put(i, false);
        }

        return doAllCombinationsAndFindPrime(intsUsed, 0, 0);
    }

    private static long doAllCombinationsAndFindPrime(HashMap<Integer, Boolean> intsUsed, int depth, long digit) {
        long sumOfPandigitals = 0;

        if (depth == 4 && (digit % 1000) % 2 != 0) {
            return sumOfPandigitals;
        }

        if (depth == 5 && (digit % 1000) % 3 != 0) {
            return sumOfPandigitals;
        }

        if (depth == 6 && (digit % 1000) % 5 != 0) {
            return sumOfPandigitals;
        }

        if (depth == 7 && (digit % 1000) % 7 != 0) {
            return sumOfPandigitals;
        }

        if (depth == 8 && (digit % 1000) % 11 != 0) {
            return sumOfPandigitals;
        }

        if (depth == 9 && (digit % 1000) % 13 != 0) {
            return sumOfPandigitals;
        }

        if (depth == 10 && (digit % 1000) % 17 == 0) {
            return digit;

        }

        for (Map.Entry<Integer, Boolean> integerBooleanEntry : intsUsed.entrySet()) {
            if (!integerBooleanEntry.getValue()) {
                intsUsed.replace(integerBooleanEntry.getKey(), true);
                sumOfPandigitals += doAllCombinationsAndFindPrime(intsUsed, depth + 1, digit * 10 + integerBooleanEntry.getKey());
                intsUsed.replace(integerBooleanEntry.getKey(), false);
            }
        }
        return sumOfPandigitals;
    }
}
