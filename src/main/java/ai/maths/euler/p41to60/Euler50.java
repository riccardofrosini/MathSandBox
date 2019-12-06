package ai.maths.euler.p41to60;

public class Euler50 {


    private static Integer[] primesArray;
    private static int index = 0;

    public static void main(String[] args) {
        primesArray = new Integer[5000000];
        buildPrimes();
        System.out.println(getPrimeSumOfMostConsecutivePrimes());
    }


    private static void buildPrimes() {
        primesArray[index++] = 2;
        primesArray[index++] = 3;

        for (int i = 1; i * 6 - 1 < 1000000; i++) {
            if (isPrimeWithPreBuildPrimes(i * 6 - 1)) {
                primesArray[index++] = (i * 6 - 1);
            }
            if (isPrimeWithPreBuildPrimes(i * 6 + 1)) {
                primesArray[index++] = i * 6 + 1;
            }

        }
    }

    private static boolean isPrimeWithPreBuildPrimes(int i) {
        for (Integer prime : primesArray) {
            if (prime > Math.sqrt(i)) {
                return true;
            }
            if (i % prime == 0) {
                return false;
            }
        }
        return true;
    }


    private static int getPrimeSumOfMostConsecutivePrimes() {
        int numOfConsecutivePrimes = 1;
        int primeSumOfMostConsecutivePrimes = 2;
        for (int i = 0; i < index; i++) {
            Integer prime = primesArray[i];
            int numOfConsecutivePrimesAddUpTo = getNumOfConsecutivePrimesAddUpTo(prime);
            if (numOfConsecutivePrimesAddUpTo > numOfConsecutivePrimes) {
                numOfConsecutivePrimes = numOfConsecutivePrimesAddUpTo;
                primeSumOfMostConsecutivePrimes = prime;
            }
        }
        return primeSumOfMostConsecutivePrimes;
    }


    /* //why is this so slow compared to the other one?
    private static int getNumOfConsecutivePrimesAddUpTo(int p) {
        int maxIndex = 0;
        int sum = 0;
        for (int minIndex = 0; primesArray[minIndex] <= p; minIndex++) {
            while (sum + primesArray[maxIndex] <= p) {
                sum = sum + primesArray[maxIndex];
                if (sum == p) {
                    return maxIndex - minIndex + 1;
                }
                maxIndex++;
            }
            sum = sum - primesArray[minIndex];
        }
        return 1;
    }*/

    private static int getNumOfConsecutivePrimesAddUpTo(int p) {
        int maxIndex = 0;
        for (int minIndex = 0; primesArray[minIndex] <= p; minIndex++) {
            while (p - primesArray[maxIndex] >= 0) {
                p = p - primesArray[maxIndex];
                if (p == 0) {
                    return maxIndex - minIndex + 1;
                }
                maxIndex++;
            }
            p = p + primesArray[minIndex];
        }
        return 1;
    }

}
