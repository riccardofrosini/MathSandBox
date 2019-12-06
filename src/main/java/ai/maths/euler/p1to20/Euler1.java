package ai.maths.euler.p1to20;

public class Euler1 {

    public static void main(String[] args) {
        long total = sumDivisibleUpTo(3, 999) + sumDivisibleUpTo(5, 999) - sumDivisibleUpTo(15, 999);
        System.out.println(total);
    }

    private static long sumDivisibleUpTo(long div, long upTo) {
        long sumOf = (long) Math.floor((double) upTo / div);
        return (div * sumOf * (sumOf + 1)) / 2;
    }
}
