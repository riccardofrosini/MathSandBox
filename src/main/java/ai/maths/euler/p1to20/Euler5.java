package ai.maths.euler.p1to20;

import ai.maths.euler.Utils;

public class Euler5 {

    public static void main(String[] args) {
        long result = 1;
        for (int i = 2; i <= 20; i++) {
            result = Utils.lcm(i, result);
        }
        System.out.println(result);
    }
}
