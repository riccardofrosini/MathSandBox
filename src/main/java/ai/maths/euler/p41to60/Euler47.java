package ai.maths.euler.p41to60;

public class Euler47 {

    public static void main(String[] args) {
        System.out.println(consecutiveWith4PrimeFactors());
    }

    private static long consecutiveWith4PrimeFactors() {
        long i = 210; //first number with 4 prime factors
        while (true) {
            if (has4PrimeFactors(i)) {
                i++;
                if (has4PrimeFactors(i)) {
                    i++;
                    if (has4PrimeFactors(i)) {
                        i++;
                        if (has4PrimeFactors(i)) {
                            return i - 3;
                        }
                    }
                }
            }
            i++;
        }
    }

    private static boolean has4PrimeFactors(long n) {
        int primeFactorsCount = 0;

        for (int j = 2; j <= Math.sqrt(n); j = j + 2) {
            if (n % j == 0) {
                primeFactorsCount++;
                while (n % j == 0) {
                    n = n / j;
                }
            }
            if (j == 2) j--;
        }
        return primeFactorsCount == 3;
    }
}
