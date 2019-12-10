package ai.maths.neat;

import ai.maths.neat.neuralnetwork.NeuralNetworkTrainer;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.utils.ConfigurationNetwork;
import ai.maths.neat.utils.NodeFunctionsCreator;

public class TrainingTester {


    public static void main(String[] args) {
        ConfigurationNetwork.setSpeciesDeltaThreshold(0.2);
        NeuralNetworkTrainer.train(9, 1, 100, NodeFunctionsCreator.rectifiedLinearUnit(), genomeEvaluator -> {
                    double score = 0;
                    double[] input1 =
                            {-1, 0, 1,
                                    -1, 0, 1,
                                    1, -1, 0};
                    score += checkValidityAndWinning(input1, (int) Math.floor(genomeEvaluator.evaluateGenome(input1).get(0) * 9));
                    double[] input2 =
                            {0, 0, 0,
                                    -1, 1, 0,
                                    1, -1, 0};
                    score += checkValidityAndWinning(input1, (int) Math.floor(genomeEvaluator.evaluateGenome(input2).get(0) * 9));
                    double[] input3 =
                            {0, 0, 0,
                                    0, -1, 0,
                                    1, -1, 1};
                    score += checkValidityAndWinning(input1, (int) Math.floor(genomeEvaluator.evaluateGenome(input3).get(0) * 9));

                    return score;
                }
        );
    }

    public static int checkValidityAndWinning(double[] board, int move) {
        if (move < 9 && board[move] == 0) {
            board[move] = 1;
            if ((board[0] == 1 && board[1] == 1 && board[2] == 1) ||
                    (board[3] == 1 && board[4] == 1 && board[5] == 1) ||
                    (board[6] == 1 && board[7] == 1 && board[8] == 1) ||
                    (board[0] == 1 && board[3] == 1 && board[6] == 1) ||
                    (board[1] == 1 && board[4] == 1 && board[7] == 1) ||
                    (board[2] == 1 && board[5] == 1 && board[8] == 1) ||
                    (board[0] == 1 && board[4] == 1 && board[8] == 1) ||
                    (board[2] == 1 && board[4] == 1 && board[6] == 1)) {
                return 1;
            } else if (
                    (board[0] == 0 && ((board[1] == -1 && board[2] == -1) || (board[3] == -1 && board[6] == -1) || (board[4] == -1 && board[8] == -1))) ||
                            (board[1] == 0 && ((board[0] == -1 && board[2] == -1) || (board[4] == -1 && board[7] == -1))) ||
                            (board[2] == 0 && ((board[0] == -1 && board[1] == -1) || (board[5] == -1 && board[8] == -1) || (board[4] == -1 && board[6] == -1))) ||
                            (board[3] == 0 && ((board[4] == -1 && board[5] == -1) || (board[0] == -1 && board[6] == -1))) ||
                            (board[4] == 0 && ((board[3] == -1 && board[5] == -1) || (board[1] == -1 && board[7] == -1) || (board[0] == -1 && board[8] == -1) || (board[2] == -1 && board[6] == -1))) ||
                            (board[5] == 0 && ((board[3] == -1 && board[4] == -1) || (board[2] == -1 && board[8] == -1))) ||
                            (board[6] == 0 && ((board[0] == -1 && board[3] == -1) || (board[7] == -1 && board[8] == -1) || (board[2] == -1 && board[4] == -1))) ||
                            (board[7] == 0 && ((board[1] == -1 && board[4] == -1) || (board[6] == -1 && board[8] == -1))) ||
                            (board[8] == 0 && ((board[0] == -1 && board[4] == -1) || (board[2] == -1 && board[5] == -1) || (board[6] == -1 && board[7] == -1)))
            ) {
                return -1;
            }
            return 0;
        }
        return -10000;
    }

    public static void main1(String[] args) {
        ConfigurationNetwork.setSpeciesDeltaThreshold(0.1);
        GenomeEvaluator genomeEvaluator = NeuralNetworkTrainer.train(2, 1, 100,
                NodeFunctionsCreator.linearUnit(), neuralNetworkEvaluator -> {

                    double[] input = {3, 4};
                    Double aDouble = neuralNetworkEvaluator.evaluateGenome(input).get(0);
                    double score = Math.abs(aDouble - (double) 7);

                    double[] input1 = {2, 1};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input1).get(0);
                    score += Math.abs(aDouble - (double) 3);

                    double[] input2 = {1, 2};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input2).get(0);
                    score += Math.abs(aDouble - (double) 3);

                    double[] input3 = {1, 1};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input3).get(0);
                    score += Math.abs(aDouble - (double) 2);

                    double[] input4 = {3, 1};
                    aDouble = neuralNetworkEvaluator.evaluateGenome(input4).get(0);
                    score += Math.abs(aDouble - (double) 4);

                    return score == 0 ? 2000000000 : 1 / score;
                });


        double[] input = {3, 4};
        System.out.println(genomeEvaluator.evaluateGenome(input).get(0));
        double[] input1 = {2, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input1).get(0));
        double[] input2 = {1, 2};
        System.out.println(genomeEvaluator.evaluateGenome(input2).get(0));
        double[] input3 = {1, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input3).get(0));
        double[] input4 = {3, 1};
        System.out.println(genomeEvaluator.evaluateGenome(input4).get(0));


    }
}
