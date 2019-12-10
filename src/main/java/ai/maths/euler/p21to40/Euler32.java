package ai.maths.euler.p21to40;

import ai.maths.euler.Utils;

import java.util.HashSet;

public class Euler32 {

    public static void main(String[] args) {
        HashSet<Long> panDigitalProducts = new HashSet<>();
        double tenTo3p5 = Math.pow(10, 3.5);
        double tenTo4 = Math.pow(10, 4);
        for (long i = 5000; i > Math.floor(tenTo3p5 / i); i--) {
            for (long j = Math.min((long) Math.ceil(tenTo4 / i), i - 1); j > Math.floor(tenTo3p5 / i); j--) {
                long prod = i * j;
                if (Utils.isPandigital(prod + "" + i + "" + j)) {
                    panDigitalProducts.add(prod);
                }
            }
        }
        System.out.println(panDigitalProducts.stream().reduce((total, pan) -> total += pan).orElse(null));
    }


}
