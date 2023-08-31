package ai.maths.euler.p21to40;

import ai.maths.euler.Utils;

public class Euler27 {

    public static void main(String[] args) {
        int result = 0;
        int maxChainPrimes = 0;
        for (int i = 3; i < 1000; i = i + 2) {
            if (Utils.isPrime(i)) {
                for (int j = -i; j < 1000; j = j + 2) {
                    int chainLength = getChainLength(i, j);
                    if (chainLength > maxChainPrimes) {
                        maxChainPrimes = chainLength;
                        result = i * j;
                    }
                }
            }
        }
        System.out.println(result);
    }

    private static int getChainLength(int i, int j) {
        int currentChain = 0;
        for (; currentChain < i; currentChain++) {
            int polynomial = currentChain * currentChain + currentChain * j + i;
            if (polynomial < 0 || !Utils.isPrime(polynomial)) {
                return currentChain;
            }
        }
        return currentChain;
    }

}
