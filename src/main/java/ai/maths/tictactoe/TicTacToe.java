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
        if (move < 0 || move >= 9 || board[move] != 0 || isEndPosition()) {
            return null;
        }
        TicTacToe ticTacToe = new TicTacToe(board, !cross);
        ticTacToe.board[move] = cross ? 1 : -1;
        return ticTacToe;
    }


    public List<Integer> getPossibleMoves() {
        List<Integer> moves = new ArrayList<>(9);
        if (isEndPosition()) {
            return moves;
        }
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                moves.add(i);
            }
        }
        return moves;
    }

    public TicTacToe makeRandomMove() {
        List<Integer> possibleMoves = getPossibleMoves();
        if (possibleMoves.isEmpty()) return null;
        return makeMove(possibleMoves.get(RandomUtils.getRandomInt(possibleMoves.size())));
    }

    public TicTacToe makeBestMove() {
        Integer move = returnBestMoveAndEval()[0];
        return move == null ? this : makeMove(move);
    }

    public Integer[] returnBestMoveAndEval() {
        int whoWonPosition = whoWonPosition();
        if (whoWonPosition != 0) return new Integer[]{null, null};
        List<Integer> possibleMoves = getPossibleMoves();
        if (possibleMoves.isEmpty()) return new Integer[]{null, null};

        Integer bestMove = null;
        int bestEval = cross ? -1 : 1;
        for (Integer possibleMove : possibleMoves) {
            Integer currentEval = makeMove(possibleMove).returnEval();
            if ((cross && currentEval >= bestEval) || (!cross && currentEval <= bestEval)) {
                bestMove = possibleMove;
                bestEval = currentEval;
            }
        }
        if (bestMove != null) {
            return new Integer[]{bestMove, bestEval};
        }
        return new Integer[]{null, null};
    }

    public Integer returnEval() {
        int whoWonPosition = whoWonPosition();
        if (whoWonPosition != 0) return whoWonPosition;
        List<Integer> possibleMoves = getPossibleMoves();
        if (possibleMoves.isEmpty()) return 0;

        int evaluation = cross ? -1 : 1;
        for (Integer possibleMove : possibleMoves) {
            Integer currentEval = makeMove(possibleMove).returnEval();
            if ((cross && currentEval >= evaluation) || (!cross && currentEval <= evaluation)) {
                evaluation = currentEval;
            }
        }
        return evaluation;
    }

    private boolean isEndPosition() {
        return whoWonPosition() != 0;
    }

    private int whoWonPosition() {
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
        return 0;
    }

    public int[] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            ret.append("|");
            for (int j = 0; j < 3; j++) {
                ret.append(board[i * 3 + j] == -1 ? "O|" : board[i * 3 + j] == 1 ? "X|" : "_|");
            }
            ret.append("\n-------\n");
        }
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
