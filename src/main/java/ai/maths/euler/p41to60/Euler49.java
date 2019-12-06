package ai.maths.euler.p41to60;

import ai.maths.Utils;

import java.util.Arrays;

public class Euler49 {

    public static void main(String[] args) {
        System.out.println(permutationPrimes());
    }

    private static String permutationPrimes() {
        for (int p1 = 1489; p1 < 10000; p1 = 2 + p1) {
            if (Utils.isPrime(p1)) {
                for (int j = 2; p1 + j + j < 10000; j = j + 2) {
                    int p2 = p1 + j;
                    int p3 = p1 + j + j;
                    if (arePermutations(p1, p2) && Utils.isPrime(p2) &&
                            arePermutations(p1, p3) && Utils.isPrime(p3)) {
                        return p1 + "" + p2 + "" + p3;
                    }
                }
            }
        }
        return null;
    }

    private static boolean arePermutations(int p1, int p2) {
        String s1 = Integer.toString(p1);
        String s2 = Integer.toString(p2);
        byte[] ints1 = s1.getBytes();
        Arrays.sort(ints1);
        byte[] ints2 = s2.getBytes();
        Arrays.sort(ints2);
        return Arrays.equals(ints1, ints2);
    }
}
