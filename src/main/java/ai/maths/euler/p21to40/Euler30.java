package ai.maths.euler.p21to40;

public class Euler30 {

    public static void main(String[] args) {
        int result = 0;
        for (int i = 2; i < 354294; i++) {
            int sumOfPow = 0;
            int iCopy = i;
            while (iCopy != 0) {
                int digit = iCopy % 10;
                iCopy = (iCopy - digit) / 10;
                sumOfPow += Math.pow(digit, 5);
            }
            if (sumOfPow == i) {
                result += sumOfPow;
            }
        }
        System.out.println(result);
    }
}
