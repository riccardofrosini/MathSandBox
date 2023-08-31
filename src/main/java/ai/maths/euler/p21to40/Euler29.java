package ai.maths.euler.p21to40;

import java.util.Stack;

public class Euler29 {

    public static void main(String[] args) {
        Stack<Integer> twoToHundred = new Stack<>();
        for (int i = 100; i > 1; i--) {
            twoToHundred.add(i);
        }
        int count = 0;
        while (!twoToHundred.isEmpty()) {
            Integer num = twoToHundred.pop();
            int maxPow = (int) Math.floor(Math.log10(100) / Math.log10(num));
            for (int j = 1; j <= maxPow; j++) {
                twoToHundred.remove((Integer) (int) Math.pow(num, j));
                if (j == 1) {
                    count += 99;
                }
                if (j == 2) {
                    count += 50;
                }
                if (j == 3) {
                    count += 50;
                }
                if (j == 4) {
                    count += 41;
                }
                if (j == 5) {
                    count += 51;
                }
                if (j == 6) {
                    count += 37;
                }
            }
        }
        System.out.println(count);
    }

}
