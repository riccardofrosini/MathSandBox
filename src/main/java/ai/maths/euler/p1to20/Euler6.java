package ai.maths.euler.p1to20;

public class Euler6 {

    public static void main(String[] args) {
        long n = 100;
        long squareOfSumsMinusSumOfSquares = (n * (n * (n * (3 * n + 2) - 3) - 2)) / 12;
        System.out.println(squareOfSumsMinusSumOfSquares);
    }
}
