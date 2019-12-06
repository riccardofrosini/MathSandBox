package ai.maths.euler.p21to40;

public class Euler36 {


    public static void main(String[] args) {
        long sumOfPalyndromic = 0;
        for (long i = 1; i < 1000000; i++) {
            if (isPalindrome(Long.toString(i)) && isPalindrome(Long.toBinaryString(i))) {
                sumOfPalyndromic = sumOfPalyndromic + i;
            }
        }
        System.out.println(sumOfPalyndromic);
    }

    private static boolean isPalindrome(String value) {
        for (int i = 0; i < value.length() / 2; i++) {
            if (value.charAt(i) != value.charAt(value.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
