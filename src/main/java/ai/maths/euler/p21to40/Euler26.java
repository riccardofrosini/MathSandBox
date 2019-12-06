package ai.maths.euler.p21to40;

import java.math.BigInteger;

public class Euler26 {

    public static void main(String[] args) {

        int maxRecurring = 0;
        int fractionWithMaxRecurring = 1;

        for (int i = 1; i < 1000; i++) {
            int val = i;
            while (val % 2 == 0) {
                val = val / 2;
            }
            while (val % 5 == 0) {
                val = val / 5;
            }
            BigInteger ten = new BigInteger("10");
            BigInteger nine = new BigInteger("9");
            BigInteger nines = nine;
            BigInteger valAsBI = new BigInteger(String.valueOf(val));
            while (!nines.mod(valAsBI).equals(BigInteger.ZERO)) {
                nines = nines.multiply(ten).add(nine);
            }
            int length = String.valueOf(nines).length();
            if (length > maxRecurring) {
                maxRecurring = length;
                fractionWithMaxRecurring = i;
            }
        }
        System.out.println(fractionWithMaxRecurring);
    }
}
