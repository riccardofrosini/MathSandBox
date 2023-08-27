package ai.maths.primes;

import java.util.ArrayList;

import ai.maths.euler.Utils;

public class PrimeChecks {


    public static void main(String[] args) {
        ArrayList<Long> primes = new ArrayList<>();
        for (long i = 2; i < 3300; i++) {
            if (Utils.isPrime(i)) {
                primes.add(i);
            }
        }

        final XYSeriesDemo demo = new XYSeriesDemo();

        int div = 0;
        double prev = 0;
        for (long i = 1; i <= 10000000; i++) {
            long j = i;
            double sqrt = Math.sqrt(i);
            for (Long prime : primes) {
                if (j == 1) {
                    break;
                }
                if (prime > sqrt) {
                    div++;
                    break;
                }
                while (j % prime == 0) {
                    j = j / prime;
                    div++;
                }
            }
            if (div != 0 && i % 1000 == 0) {
                System.out.println(i + " " + div);
                System.out.println((double) (i * i) / div - prev);
                demo.addDot(i, (double) (i * i) / div - prev);
                prev = (double) (i * i) / div;

            }
        }

        demo.postProcess();
        demo.pack();
        demo.setVisible(true);
    }
}
