package ai.maths.euler;

import java.math.BigInteger;

public class Utils {

    public static final double SQRT5 = Math.sqrt(5);
    public static final double PHI = (SQRT5 + 1) / 2;

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static boolean isPrime(long p) {
        if (p <= 1) {
            return false;
        }
        if (p == 2) {
            return true;
        }
        if (p == 3) {
            return true;
        }
        if (p % 2 == 0) {
            return false;
        }
        if (p % 3 == 0) {
            return false;
        }
        double sqrt = Math.sqrt(p);
        for (long j = 1; j * 6 - 1 <= sqrt; j++) {
            if (p % (j * 6 - 1) == 0) {
                return false;
            }
            if (p % (j * 6 + 1) == 0) {
                return false;
            }
        }
        return true;
    }

    public static long sumOfFactors(long n) {
        long nCopy = n;
        long sum = 1;

        for (long i = 2; i * i <= nCopy; i = i + 2) {
            long partial = 1;
            long prime = i;
            long powPrime = prime;
            while (nCopy % prime == 0) {
                partial += powPrime;
                nCopy = nCopy / prime;
                powPrime = powPrime * prime;
            }
            sum = sum * partial;
            if (i == 2) {
                i--;
            }
        }
        if (nCopy > 1) {
            sum = sum * (1 + nCopy);
        }
        return sum - n;
    }

    public static int getMaxPathOfPyramidNumbers(int[][] pyramid) {
        for (int i = 1; i < pyramid.length; i++) {
            int[] prevValues = pyramid[i - 1];
            int[] values = pyramid[i];
            for (int j = 0; j < values.length; j++) {
                int prevLeft = j < prevValues.length ? prevValues[j] : 0;
                int prevRight = j - 1 >= 0 ? prevValues[j - 1] : 0;
                values[j] += Math.max(prevLeft, prevRight);
            }
        }
        int maxPath = 0;
        for (int i = 0; i < pyramid[pyramid.length - 1].length; i++) {
            maxPath = Math.max(maxPath, pyramid[pyramid.length - 1][i]);
        }
        return maxPath;
    }

    public static long getSumOfDigits(BigInteger num) {
        return getSumOfDigits(num.toString());
    }

    private static long getSumOfDigits(String numToString) {
        long sum = 0;
        for (byte aByte : numToString.getBytes()) {
            sum = sum + (aByte - 48);
        }
        return sum;
    }

    public static BigInteger factorialBigInteger(long n) {
        BigInteger factorial = BigInteger.ONE;
        if (n == 0) {
            return factorial;
        }
        for (long i = 2; i <= n; i++) {
            factorial = factorial.multiply(new BigInteger(String.valueOf(i)));
        }
        return factorial;
    }

    public static long factorial(long n) {
        long factorial = 1;
        if (n == 0) {
            return factorial;
        }
        for (long i = 2; i <= n; i++) {
            factorial = factorial * i;
        }
        return factorial;
    }

    public static boolean isPandigital(String str) {
        return str.length() == 9
                && str.contains("1")
                && str.contains("2")
                && str.contains("3")
                && str.contains("4")
                && str.contains("5")
                && str.contains("6")
                && str.contains("7")
                && str.contains("8")
                && str.contains("9");
    }

    public static long getSumOfLetters(String name) {
        long sumName = 0;
        for (byte aByte : name.getBytes()) {
            sumName += aByte - 64;
        }
        return sumName;
    }

    public static boolean isPerfectSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt - Math.floor(sqrt) == 0;
    }

}
