package ai.maths.euler.p1to20;

public class Euler15 {

    public static void main(String[] args) {
        System.out.println(pathCombinations(20, 20));

    }

    private static long pathCombinations(long i, long j) {
        long n = i + j;
        long k = Math.min(i, j);
        long result = 1;
        for (long l = 0; l < k; l++) {
            result = ((n - l) * result) / (l + 1);
        }
        return result;
    }
}
