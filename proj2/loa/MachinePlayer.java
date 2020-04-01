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
        int depth;
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
        // FIXME
        int value = estimateBoardValue(board);
        if (depth == 0 || (sense == 1 && value > beta) || (sense == -1 && value < alpha)) {
            return value;
        }

        int response;
        Move bestMove = null;
        value = -getWinningValue();
        for (Move move : board.legalMoves()) {
            board.makeMove(move);
            Board testBoard = new Board(board);
            response = findMove(testBoard, depth - 1, false, sense, alpha, beta);
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
        return 3;
    }

    // FIXME: Other methods, variables here.
    private int estimateBoardValue(Board board) {
        if (board.winner() == side()) {
            return getWinningValue();
        } else if (board.winner() == side().opposite()) {
            return -getWinningValue();
        }
        List<Integer> myRegions = board.getRegionSizes(side());
        List<Integer> oppRegions = board.getRegionSizes(side().opposite());
        int biggestRegionDiff = myRegions.get(0) - oppRegions.get(0);
        int myPieces = 0;
        for (int i : myRegions) {
            myPieces += i;
        }
        int oppPieces = 0;
        for (int i :oppRegions) {
            oppPieces += i;
        }
        return biggestRegionDiff - 2 * myPieces + 2 * oppPieces;
    }

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
