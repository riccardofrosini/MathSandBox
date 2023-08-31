package ai.maths.euler.p41to60;

import java.util.HashMap;
import java.util.Map;

import ai.maths.euler.Utils;

public class Euler41 {


    public static void main(String[] args) {
        // only prime numbers can be 7n pan or 4 pan
        //System.out.println(nPandigitalPrime(4));
        System.out.println(nPandigitalPrime(7));
    }

    private static int nPandigitalPrime(int n) {
        HashMap<Integer, Boolean> intsUsed = new HashMap<>(n);
        for (int i = 1; i <= n; i++) {
            intsUsed.put(i, false);
        }

        return doAllCombinationsAndFindPrime(intsUsed, (int) Math.pow(10, n - 1), 0);
    }

    private static int doAllCombinationsAndFindPrime(HashMap<Integer, Boolean> intsUsed, int minimumForPandigital, int digit) {
        int biggestPrime = 0;
        if (digit > minimumForPandigital) {
            if (Utils.isPrime(digit)) {
                return digit;
            }
            return biggestPrime;
        }
        for (Map.Entry<Integer, Boolean> integerBooleanEntry : intsUsed.entrySet()) {
            if (!integerBooleanEntry.getValue()) {
                intsUsed.replace(integerBooleanEntry.getKey(), true);
                biggestPrime = Math.max(doAllCombinationsAndFindPrime(intsUsed, minimumForPandigital, digit * 10 + integerBooleanEntry.getKey()), biggestPrime);
                intsUsed.replace(integerBooleanEntry.getKey(), false);
            }
        }
        return biggestPrime;
    }
}
