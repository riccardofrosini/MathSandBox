package ai.maths.euler.p21to40;

public class Euler28 {

    public static void main(String[] args) {
        System.out.println(sumOfDiagonalsInClockwiseNumbersNxNSquare(1001));
    }

    private static long sumOfDiagonalsInClockwiseNumbersNxNSquare(long n) {
        n = (n - 1) / 2;
        return 1 + 4 * n + 2 * n * (n + 1) + (8 * (n + 1) * (2 * n + 1) * n) / 3;
    }
}
