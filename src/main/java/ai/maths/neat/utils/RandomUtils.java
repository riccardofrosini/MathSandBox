package ai.maths.neat.utils;

import java.util.Random;

public class RandomUtils {

    private final static Random RANDOM = new Random();

    public static double getRandomWeight() {
        return (RANDOM.nextDouble() * 2) - 1;
    }

    public static double getRandomPerturbation() {
        return ((RANDOM.nextDouble() * 2) - 1) * ConfigurationNetwork.PERTURBATION_PERCENTAGE;
    }

    public static double getRandom() {
        return RANDOM.nextDouble();
    }

    public static int getRandomInt(int maxExclusive) {
        return RANDOM.nextInt(maxExclusive);
    }

    public static boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }
}
