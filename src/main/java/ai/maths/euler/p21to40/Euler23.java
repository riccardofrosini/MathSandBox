package ai.maths.euler.p21to40;

import ai.maths.Utils;

import java.util.LinkedHashSet;

public class Euler23 {

    public static void main(String[] args) {
        int max = 28123;
        LinkedHashSet<Integer> abundantNumbers = new LinkedHashSet<>();
        for (int i = 12; i < max; i++) {
            if (Utils.sumOfFactors(i) > i) {
                abundantNumbers.add(i);
            }
        }
        long sum = 0;
        for (int i = 1; i < 28123; i++) {
            for (Integer abundantNumber : abundantNumbers) {
                if (i - abundantNumber < 12) {
                    sum += i;
                    break;
                }
                if (abundantNumbers.contains(i - abundantNumber)) break;
            }
        }
        System.out.println(sum);
    }
}
