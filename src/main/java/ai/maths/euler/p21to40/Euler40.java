package ai.maths.euler.p21to40;

public class Euler40 {

    public static void main(String[] args) {
        System.out.println(digitAtPosition(1) *
                digitAtPosition(10) *
                digitAtPosition(100) *
                digitAtPosition(1000) *
                digitAtPosition(10000) *
                digitAtPosition(100000) *
                digitAtPosition(1000000));
    }

    private static int digitAtPosition(int n) {
        for (int i = 1; ; i++) {
            int digitsToDiscard = i * 9 * (int) Math.pow(10, i - 1);
            if (n <= digitsToDiscard) {
                int finalNumber = ((n - 1) / i) + (int) Math.pow(10, i - 1);
                int digitPositionOfNumber = (n - 1) % i;
                return Integer.parseInt(String.valueOf(finalNumber).substring(digitPositionOfNumber, digitPositionOfNumber + 1));
            }
            n = n - digitsToDiscard;
        }
    }
}
