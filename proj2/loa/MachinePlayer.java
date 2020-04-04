/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.List;

import static loa.Piece.*;

/** An automated Player.
 *  @author Jenny Mei
 */
class MachinePlayer extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new MachinePlayer with no piece or controller (intended to produce
     *  a template). */
    MachinePlayer() {
        this(null, null);
    }

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice;
        assert side() == getGame().getBoard().turn();
        choice = searchForMove();
        getGame().reportMove(choice);
        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private Move searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            value = findMove(work, chooseDepth(), true, 1, -INFTY, INFTY);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
        }
        return _foundMove;
    }

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        int value = estimateBoardValue(board);
        if (depth == 0 || (sense == 1 && value > beta)
                || (sense == -1 && value < alpha) || Math.abs(value) == WINNING_VALUE) {
            return value;
        }

        int response;
        Move bestMove = null;
        value = -getWinningValue();
        for (Move move : board.legalMoves()) {
            board.makeMove(move);
            Board testBoard = new Board(board);
            response = findMove(testBoard, Math.min(depth - 1, chooseDepth()),
                    false, sense, alpha, beta);
            if (sense == 1 && response > value) {
                bestMove = move;
                value = response;
                alpha = Math.max(alpha, response);

            } else if (sense == -1 && response < value) {
                bestMove = move;
                value = response;
                beta = Math.min(beta, response);
            }

            if (beta <= alpha) {
                break;
            }
            board.retract();
        }

        if (saveMove) {
            _foundMove = bestMove;
        }
        return value;
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        int pieces = 0;
        for (int count : getBoard().getRegionSizes(side())) {
            pieces += count;
        }
        if (pieces <= 3) {
            return 4;
        }
        return 3;
    }

    /**Returns BOARD value. */
    private int estimateBoardValue(Board board) {
        if (board.winner() == WP) {
            return getWinningValue();
        } else if (board.winner() == BP) {
            return -getWinningValue();
        } else if (board.winner() == EMP) {
            return 0;
        }

        List<Integer> wRegions = board.getRegionSizes(WP);
        List<Integer> bRegions = board.getRegionSizes(BP);
        int wPieces = 0;
        for (int i : wRegions) {
            wPieces += i;
        }
        int bPieces = 0;
        for (int i : bRegions) {
            bPieces += i;
        }
        return bRegions.size() - wRegions.size() - 2 * wPieces + 2 * bPieces;
    }

    /** Returns winning value of the side. */
    private int getWinningValue() {
        if (side() == WP) {
            return WINNING_VALUE;
        } else {
            return -WINNING_VALUE;
        }
    }

    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

}
