package ai.maths.euler.p21to40;

import ai.maths.Utils;

public class Euler34 {

    public static void main(String[] args) {
        long sumOfSumOfFactorial = 0;
        for (int i = 10; i < 2540161; i++) {
            long sumOfFactorial = getSumOfFactorial(i);
            if (sumOfFactorial == i) sumOfSumOfFactorial = sumOfSumOfFactorial + sumOfFactorial;
        }
        System.out.println(sumOfSumOfFactorial);
    }

    private static long getSumOfFactorial(int i) {
        int digits = i;
        long sumOfFactorial = 0;
        while (digits != 0) {
            int digit = digits % 10;
            sumOfFactorial = sumOfFactorial + Utils.factorial(digit);
            if (sumOfFactorial > i) {
                return 0;
            }
            digits = (digits - digit) / 10;
        }
        return sumOfFactorial;
    }
}
