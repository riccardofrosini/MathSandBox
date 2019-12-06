package ai.maths.euler.p1to20;

public class Euler4 {

    public static void main(String[] args) {
        System.out.println(findBiggestPalindrome());
    }

    private static int findBiggestPalindrome() {
        for (int i = 9; i > 0; i--) {
            for (int j = 9; j >= 0; j--) {
                for (int k = 9; k >= 0; k--) {
                    int palindrome = i * 100001 + j * 10010 + k * 1100;
                    for (int divisor1 = 990; divisor1 > 99; divisor1 -= 11) {
                        if (palindrome % divisor1 == 0) {
                            int divisor2 = palindrome / divisor1;
                            if (divisor2 >= 100 && divisor2 < 1000) {
                                return palindrome;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }
}
