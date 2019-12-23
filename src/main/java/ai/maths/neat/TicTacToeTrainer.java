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

    static final HashMap<TicTacToe, Integer> ticTacToeHashMap = new HashMap<>();
    static final HashSet<TicTacToe> ticTacToeHashSet = new HashSet<>();

    private static void fillUpEvaluationsAndPositions(TicTacToe ticTacToe) {
        int eval = ticTacToe.returnEval();
        if (ticTacToe.isCross() && !ticTacToe.getPossibleMoves().isEmpty()) {
            ticTacToeHashSet.add(ticTacToe);
        }
        ticTacToeHashMap.put(ticTacToe, eval);
        List<Integer> possibleMoves = ticTacToe.getPossibleMoves();
        for (Integer possibleMove : possibleMoves) {
            fillUpEvaluationsAndPositions(ticTacToe.makeMove(possibleMove));
        }
    }

    static void fillUpEvaluationsAndPositions() {
        fillUpEvaluationsAndPositions(new TicTacToe());
    }

    public static void main(String[] args) {
        fillUpEvaluationsAndPositions();
        System.out.println(ticTacToeHashMap.size());
        System.out.println(ticTacToeHashSet.size());
        ConfigurationNetwork.MAX_POPULATION = 5000;
        ConfigurationNetwork.MAX_POPULATION_STAGNATION_GENERATION = 50;
        ConfigurationNetwork.MAX_SPECIES_STAGNATION_GENERATION = 40;
        ConfigurationNetwork.SPECIES_DELTA_THRESHOLD = 2;
        GenomeEvaluator geno = NeuralNetworkTrainer.train(27, 9, 10000, NodeFunctionsCreator.linearUnit(), genomeEvaluator -> {
                    double score = 0;
                    for (TicTacToe ticTacToe : ticTacToeHashSet) {
                        int move = getMoveIn27Out9(genomeEvaluator, ticTacToe.getBoard());
                        ticTacToe = ticTacToe.makeMove(move);
                        if (ticTacToe == null) {
                            continue;
                        }
                        score += (double) (ticTacToeHashMap.get(ticTacToe) + 1) / 10;
                        //score++;
                    }
                    return score;
                }
        );

        TicTacToe ticTacToe = new TicTacToe();
        while (ticTacToe != null && !ticTacToe.getPossibleMoves().isEmpty()) {
            int[] board = ticTacToe.getBoard();
            int move = getMoveIn27Out9(geno, board);
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

    static int getMoveIn9Out1(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = boardTo9Input(board);
        return getMove1Output(genomeEvaluator, doubles);
    }

    static int getMoveIn9Out9(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = boardTo9Input(board);
        return getMove9Output(genomeEvaluator, doubles);
    }

    static int getMoveIn27Out1(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = boardTo27Input(board);
        return getMove1Output(genomeEvaluator, doubles);
    }

    static int getMoveIn27Out9(GenomeEvaluator genomeEvaluator, int[] board) {
        double[] doubles = boardTo27Input(board);
        return getMove9Output(genomeEvaluator, doubles);
    }

    private static int getMove1Output(GenomeEvaluator genomeEvaluator, double[] doubles) {
        return (int) Math.floor(genomeEvaluator.evaluateGenome(doubles).get(0) * 9);
    }

    private static int getMove9Output(GenomeEvaluator genomeEvaluator, double[] doubles) {
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

    private static double[] boardTo9Input(int[] board) {
        double[] doubles = new double[board.length];
        for (int i = 0; i < board.length; i++) {
            doubles[i] = board[i] == 0 ? 0 : ((double) board[i] + 3) / 2;
        }
        return doubles;
    }

    private static double[] boardTo27Input(int[] board) {
        double[] doubles = new double[27];
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                doubles[i * 3] = 1;
                doubles[i * 3 + 1] = 0;
                doubles[i * 3 + 2] = 0;
            }
            if (board[i] == 1) {
                doubles[i * 3] = 0;
                doubles[i * 3 + 1] = 1;
                doubles[i * 3 + 2] = 0;
            }
            if (board[i] == -1) {
                doubles[i * 3] = 0;
                doubles[i * 3 + 1] = 0;
                doubles[i * 3 + 2] = 1;
            }
        }
        return doubles;
    }
}
