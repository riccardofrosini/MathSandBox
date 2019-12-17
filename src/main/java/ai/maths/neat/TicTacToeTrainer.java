package ai.maths.neat;

import ai.maths.neat.neuralnetwork.NeuralNetworkTrainer;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
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
        GenomeEvaluator geno = NeuralNetworkTrainer.train(9, 1, 500, NodeFunctionsCreator.linearUnit(), genomeEvaluator -> {
                    double score = 0;
                    for (TicTacToe ticTacToe : ticTacToeHashSet) {
                        int move = evaluateOut1(genomeEvaluator, ticTacToe.getBoard());
                        ticTacToe = ticTacToe.makeMove(move);
                        if (ticTacToe == null) {
                            continue;
                        }
                        Integer integer = ticTacToeHashMap.get(ticTacToe);
                        if (integer == null) {
                            integer = ticTacToe.returnEval();
                            ticTacToeHashMap.put(ticTacToe, integer);
                        }
                        score += (double) (integer + 2) / 10;
                        score++;
                    }
                    return score;
                }
        );

        TicTacToe ticTacToe = new TicTacToe();
        while (ticTacToe != null && !ticTacToe.getPossibleMoves().isEmpty()) {
            int[] board = ticTacToe.getBoard();
            int move = evaluateOut1(geno, board);
            ticTacToe = ticTacToe.makeMove(move);
            if (ticTacToe == null) break;
            System.out.println(ticTacToe);
            ticTacToe = ticTacToe.makeBestMove();
            System.out.println(ticTacToe);
        }
        System.out.println(ticTacToe);
    }

    private static int evaluateOut1(GenomeEvaluator genomeEvaluator, int[] board) {
        return (int) Math.floor(genomeEvaluator.evaluateGenome(boardToDoubleInput(board)).get(0) * 9);
    }

    private static int evaluateOut9(GenomeEvaluator genomeEvaluator, int[] board) {
        int ret = -1;
        List<Double> doubles = genomeEvaluator.evaluateGenome(boardToDoubleInput(board));
        for (int i = 0; i < doubles.size(); i++) {
            if (doubles.get(i) >= 1) {
                if (ret == -1) {
                    ret = i;
                } else return -1;
            }
        }
        return ret;
    }

    private static double[] boardToDoubleInput(int[] board) {
        double[] doubles = new double[board.length];
        for (int i = 0; i < board.length; i++) {
            doubles[i] = board[i] + 2;
        }
        return doubles;
    }
}
