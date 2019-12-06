package ai.maths.euler.p21to40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Euler24 {

    public static void main(String[] args) {
        long position = 999999;
        List<Integer> digits = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        StringBuilder result = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            long factorial = factorial(i);
            int digit = (int) Math.floor(position / factorial);
            position = position - digit * factorial;
            result.append(digits.remove(digit));
        }
        System.out.println(result);
    }

    private static long factorial(long n) {
        long factorial = 1;
        if (n == 0 || n == 1) return factorial;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
