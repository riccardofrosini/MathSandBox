package ai.maths.euler.p21to40;

public class Euler31 {

    public static void main(String[] args) {

        int[] amounts = new int[201];
        amounts[0] = 1;
        int[] coins = {1, 2, 5, 10, 20, 50, 100, 200};
        for (int coin : coins) {
            for (int i = coin; i < 201; i++) {
                amounts[i] = amounts[i] + amounts[i - coin];

            }
        }
        System.out.println(amounts[200]);
    }
}
