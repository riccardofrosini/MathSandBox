package ai.maths.neat;

import ai.maths.neat.neuralnetwork.NeuralNetworkTrainer;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.utils.ConfigurationNetwork;
import ai.maths.neat.utils.NodeFunctionsCreator;
import ai.maths.tictactoe.TicTacToe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TicTacToeTrainer {

    private static HashMap<TicTacToe, Integer> ticTacToeHashMap = new HashMap<>();
    private static HashSet<TicTacToe> ticTacToeHashSet = new HashSet<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            TicTacToe ticTacToe = new TicTacToe();
            while (ticTacToe != null && !ticTacToe.getPossibleMoves().isEmpty()) {
                ticTacToeHashSet.add(ticTacToe);
                ticTacToe = ticTacToe.makeRandomMove();
                Integer integer = ticTacToeHashMap.get(ticTacToe);
                if (integer == null) {
                    integer = ticTacToe.returnEval();
                    ticTacToeHashMap.put(ticTacToe, integer);
                }
                ticTacToe = ticTacToe.makeRandomMove();
            }
        }
        System.out.println(ticTacToeHashSet.size());
        ConfigurationNetwork.SPECIES_DELTA_THRESHOLD = 3;
        GenomeEvaluator geno = NeuralNetworkTrainer.train(27, 9, 500, NodeFunctionsCreator.linearUnit(), genomeEvaluator -> {
                    double score = 0;
                    for (TicTacToe ticTacToe : ticTacToeHashSet) {
                        int move = evaluateIn27Out9(genomeEvaluator, ticTacToe.getBoard());
                        ticTacToe = ticTacToe.makeMove(move);
                        if (ticTacToe == null) {
                            continue;
                        }
                        Integer integer = ticTacToeHashMap.get(ticTacToe);
                        if (integer == null) {
                            integer = ticTacToe.returnEval();
                            ticTacToeHashMap.put(ticTacToe, integer);
                        }
                        score += (double) (integer + 1) / 10;
                        score++;
                    }
                    return score;
                }
        );

        TicTacToe ticTacToe = new TicTacToe();
        while (ticTacToe != null && !ticTacToe.getPossibleMoves().isEmpty()) {
            int[] board = ticTacToe.getBoard();
            int move = evaluateIn27Out9(geno, board);
            ticTacToe = ticTacToe.makeMove(move);
            if (ticTacToe == null) {
                break;
            }
            System.out.println(ticTacToe);
            ticTacToe = ticTacToe.makeBestMove();
            System.out.println(ticTacToe);
        }
        System.out.println(ticTacToe);
    }

    static int evaluateIn27Out1(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = new double[27];
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                doubles[i * 3] = 1;
            }
            if (board[i] == 1) {
                doubles[i * 3 + 1] = 1;
            }
            if (board[i] == -1) {
                doubles[i * 3 + 2] = 1;
            }
        }
        return (int) Math.floor(genomeEvaluator.evaluateGenome(doubles).get(0) * 9);
    }

    static int evaluateIn27Out9(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = new double[27];
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                doubles[i * 3] = 1;
            }
            if (board[i] == 1) {
                doubles[i * 3 + 1] = 1;
            }
            if (board[i] == -1) {
                doubles[i * 3 + 2] = 1;
            }
        }
        List<Double> evaluation = genomeEvaluator.evaluateGenome(doubles);
        int ret = 0;
        double largest = 0;
        for (int i = 0; i < evaluation.size(); i++) {
            if (evaluation.get(i) > largest) {
                largest = evaluation.get(i);
                ret = i;
            }
        }
        return ret;
    }

    private static int evaluateIn9Out1(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = new double[board.length];
        for (int i = 0; i < board.length; i++) {
            doubles[i] = board[i] == 0 ? 0 : ((double) board[i] + 3) / 2;
        }
        return (int) Math.floor(genomeEvaluator.evaluateGenome(doubles).get(0) * 9);
    }

    private static int evaluateIn9Out9(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = new double[board.length];
        for (int i = 0; i < board.length; i++) {
            doubles[i] = board[i] == 0 ? 0 : ((double) board[i] + 3) / 2;
        }
        List<Double> evaluation = genomeEvaluator.evaluateGenome(doubles);
        int ret = 0;
        double largest = 0;
        for (int i = 0; i < evaluation.size(); i++) {
            if (evaluation.get(i) > largest) {
                largest = evaluation.get(i);
                ret = i;
            }
        }
        return ret;
    }
}
