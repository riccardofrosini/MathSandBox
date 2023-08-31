package ai.maths.euler.p1to20;

import java.math.BigInteger;

import ai.maths.euler.Utils;

public class Euler20 {

    public static void main(String[] args) {
        int numOfTwos = 0;
        int numOfFives = 0;
        BigInteger factorial = BigInteger.ONE;
        for (int i = 2; i < 101; i++) {
            long afterRemovingTwosAndFives = i;
            int twos = Long.numberOfTrailingZeros(afterRemovingTwosAndFives);
            afterRemovingTwosAndFives = afterRemovingTwosAndFives >> twos;
            numOfTwos += twos;
            while (afterRemovingTwosAndFives % 5 == 0) {
                afterRemovingTwosAndFives = afterRemovingTwosAndFives / 5;
                numOfFives++;
            }
            factorial = factorial.multiply(new BigInteger(String.valueOf(afterRemovingTwosAndFives)));
        }
        int powTwo = numOfTwos - numOfFives;
        int powFive = numOfFives - numOfTwos;
        if (powTwo > 0) {
            factorial = factorial.multiply(new BigInteger("2").pow(powTwo));
        }
        if (powFive > 0) {
            factorial = factorial.multiply(new BigInteger("5").pow(powFive));
        }
        System.out.println(Utils.getSumOfDigits(factorial));
    }
}
