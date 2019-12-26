package ai.maths.tictactoe;

import ai.maths.neat.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class TicTacToe {

    private final int[] board;
    private final boolean cross;

    public TicTacToe() {
        board = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.cross = true;
    }

    private TicTacToe(int[] board, boolean cross) {
        this.board = Arrays.copyOf(board, board.length);
        this.cross = cross;
    }


    public TicTacToe makeMove(int move) {
        if (move < 0 || move >= 9 || board[move] != 0 || whoWonPosition() != null) {
            return null;
        }
        TicTacToe ticTacToe = new TicTacToe(board, !cross);
        ticTacToe.board[move] = cross ? 1 : -1;
        return ticTacToe;
    }

    public List<TicTacToe> getPossibleBoards() {
        List<TicTacToe> moves = new ArrayList<>(9);
        if (whoWonPosition() != null) {
            return moves;
        }
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                moves.add(makeMove(i));
            }
        }
        return moves;
    }

    public TicTacToe makeRandomMove() {
        List<TicTacToe> possibleMoves = getPossibleBoards();
        if (possibleMoves.isEmpty()) return null;
        return possibleMoves.get(RandomUtils.getRandomInt(possibleMoves.size()));
    }

    public TicTacToe makeBestMove() {
        Integer move = returnBestMoveAndEval()[0];
        return move == null ? null : makeMove(move);
    }

    public Integer[] returnBestMoveAndEval() {
        Integer whoWonPosition = whoWonPosition();
        if (whoWonPosition != null) return new Integer[]{null, whoWonPosition};

        Integer bestMove = null;
        int bestEval = cross ? -1 : 1;
        for (int i = 0; i < 9; i++) {
            TicTacToe ticTacToe = makeMove(i);
            if (ticTacToe != null) {
                int currentEval = ticTacToe.returnEval();
                if ((cross && currentEval >= bestEval) || (!cross && currentEval <= bestEval)) {
                    bestMove = i;
                    bestEval = currentEval;
                }
            }
        }
        return new Integer[]{bestMove, bestEval};
    }

    public int returnEval() {
        Integer whoWonPosition = whoWonPosition();
        if (whoWonPosition != null) return whoWonPosition;
        List<TicTacToe> possibleMoves = getPossibleBoards();

        int evaluation = cross ? -1 : 1;
        for (TicTacToe possibleMove : possibleMoves) {
            int currentEval = possibleMove.returnEval();
            if ((cross && currentEval >= evaluation) || (!cross && currentEval <= evaluation)) {
                evaluation = currentEval;
            }
        }
        return evaluation;
    }

    private Integer whoWonPosition() {
        if ((board[0] == 1 && board[1] == 1 && board[2] == 1) ||
                (board[3] == 1 && board[4] == 1 && board[5] == 1) ||
                (board[6] == 1 && board[7] == 1 && board[8] == 1) ||
                (board[0] == 1 && board[3] == 1 && board[6] == 1) ||
                (board[1] == 1 && board[4] == 1 && board[7] == 1) ||
                (board[2] == 1 && board[5] == 1 && board[8] == 1) ||
                (board[0] == 1 && board[4] == 1 && board[8] == 1) ||
                (board[2] == 1 && board[4] == 1 && board[6] == 1)) {
            return 1;
        }
        if ((board[0] == -1 && board[1] == -1 && board[2] == -1) ||
                (board[3] == -1 && board[4] == -1 && board[5] == -1) ||
                (board[6] == -1 && board[7] == -1 && board[8] == -1) ||
                (board[0] == -1 && board[3] == -1 && board[6] == -1) ||
                (board[1] == -1 && board[4] == -1 && board[7] == -1) ||
                (board[2] == -1 && board[5] == -1 && board[8] == -1) ||
                (board[0] == -1 && board[4] == -1 && board[8] == -1) ||
                (board[2] == -1 && board[4] == -1 && board[6] == -1)) {
            return -1;
        }
        if (board[0] != 0 && board[1] != 0 && board[2] != 0 &&
                board[3] != 0 && board[4] != 0 && board[5] != 0 &&
                board[6] != 0 && board[7] != 0 && board[8] != 0) {
            return 0;
        }
        return null;
    }

    public int[] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ret.append(board[i * 3 + j] == -1 ? "O" : board[i * 3 + j] == 1 ? "X" : " ");
                if (j != 2) ret.append("|");
            }
            if (i != 2) ret.append("\n-+-+-\n");
        }
        ret.append("\n");
        return ret.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicTacToe)) return false;
        TicTacToe ticTacToe = (TicTacToe) o;
        return cross == ticTacToe.cross &&
                Arrays.equals(board, ticTacToe.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cross);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }

    public boolean isCross() {
        return cross;
    }
}
