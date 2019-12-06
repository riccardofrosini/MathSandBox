package ai.maths.euler.p1to20;

public class Euler3 {
    public static void main(String[] args) {
        long n = 600851475143L;
        long maxPf = n;
        long maxPrimeFactor = 2;
        /*while (maxPf % 2 == 0) {
            maxPf = maxPf / 2;
        }*/
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            while (maxPf % i == 0) {
                maxPrimeFactor = i;
                maxPf = maxPf / i;
            }
        }
        System.out.println(Math.max(maxPrimeFactor, maxPf));
    }
}
