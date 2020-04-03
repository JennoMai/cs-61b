/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Jenny Mei
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;

        for (int row = 0; row < contents.length; row += 1) {
            for (int col = 0; col < contents[0].length; col += 1) {
                _board[sq(col, row).index()] = contents[row][col];
            }
        }

        _winner = null;
        _winnerKnown = false;
        _subsetsInitialized = false;

    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }

        for (int i = 0; i < board._board.length; i += 1) {
            _board[i] = board._board[i];
        }
        _turn = board.turn();
        for (Move m : board._moves) {
            _moves.add(m);
        }
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        _board[sq.index()] = v;
        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. Assumes MOVE.isCapture()
     *  is false. */
    void makeMove(Move move) {
        assert isLegal(move);
        if (_board[move.getTo().index()] == _turn.opposite()) {
            _moves.add(move.captureMove());
        } else {
            _moves.add(move);
        }

        set(move.getTo(), _board[move.getFrom().index()], _turn.opposite());
        _board[move.getFrom().index()] = EMP;
        _subsetsInitialized = false;
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        Move unmove = _moves.get(_moves.size() - 1);
        set(unmove.getFrom(), _board[unmove.getTo().index()], _turn.opposite());

        if (unmove.isCapture()) {
            _board[unmove.getTo().index()] = _turn.opposite();
        } else {
            _board[unmove.getTo().index()] = EMP;
        }

        _moves.remove(unmove);

        if (gameOver()) {
            _winnerKnown = false;
            _winner = null;
        }
        _subsetsInitialized = false;
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. */
    boolean isLegal(Square from, Square to) {
        if (to == null || from == null
            || !from.isValidMove(to)
            || _board[from.index()] != _turn
            || blocked(from, to)) {
            return false;
        }

        int dir = from.direction(to);
        int backdir = oppositeDir(dir);
        int distance = from.distance(to);
        int count = 0;

        Square forward = from;
        while (forward != null) {
            if (_board[forward.index()] != EMP) {
                count += 1;
            }
            forward = forward.moveDest(dir, 1);
        }
        Square back = from.moveDest(backdir, 1);
        while (back != null) {
            if (_board[back.index()] != EMP) {
                count += 1;
            }
            back = back.moveDest(backdir, 1);
        }

        return count == distance;
    }

    /** Returns the opposite direction of DIR. */
    private int oppositeDir(int dir) {
        if (dir < 4) {
            return dir + 4;
        } else {
            return dir - 4;
        }
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position. */
    List<Move> legalMoves() {
        ArrayList<Move> moveList = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row += 1) {
            for (int col = 0; col < BOARD_SIZE; col += 1) {
                Square sq = sq(col, row);
                for (int dir = 0; dir < 8; dir += 1) {
                    for (int dis = 1; dis < 8; dis += 1) {
                        if (isLegal(sq, sq.moveDest(dir, dis))) {
                            moveList.add(Move.mv(sq, sq.moveDest(dir, dis)));
                        }
                    }
                }
            }
        }
        return moveList;
    }

    /** Return true iff the game is over (either player has all his
     *  pieces continguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (!_winnerKnown) {
            if (movesMade() > _moveLimit + 1) {
                _winner = EMP;
            } else if (piecesContiguous(WP) && piecesContiguous(BP)) {
                _winner = turn().opposite();
            } else if (piecesContiguous(WP)) {
                _winner = WP;
            } else if (piecesContiguous(BP)) {
                _winner = BP;
            } else {
                return null;
            }
            _winnerKnown = true;
        }
        return _winner;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        Piece current = _board[from.index()];

        int direction = from.direction(to);
        int distance = from.distance(to);
        for (int i = 1; i <= distance; i += 1) {
            Square sq = from.moveDest(direction, i);
            if (i != distance && _board[sq.index()] == current.opposite()) {
                return true;
            } else if (_board[sq.index()] == current && i == distance) {
                return true;
            }
        }
        return false;
    }

    /** Return the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    ArrayList<Square> squaresContig(Square sq, boolean[][] visited, Piece p) {
        ArrayList<Square> group = new ArrayList<>();
        if (_board[sq.index()] == p && !visited[sq.row()][sq.col()]) {
            visited[sq.row()][sq.col()] = true;
            group.add(sq);
        } else {
            return null;
        }

        Square[] adjacent = sq.adjacent();
        for (Square adj : adjacent) {
            if (_board[sq.index()] == p && !visited[adj.row()][adj.col()]) {
                ArrayList<Square> others = squaresContig(adj, visited, p);
                if (others != null) {
                    group.addAll(others);
                }
            }
        }
        return group;
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteGroups.clear();
        _blackGroups.clear();
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();

        boolean[][] visited = new boolean[8][8];

        for (int row = 0; row < BOARD_SIZE; row += 1) {
            for (int col = 0; col < BOARD_SIZE; col += 1) {
                if (!visited[row][col]) {
                    Square sq = sq(col, row);
                    ArrayList<Square> group;
                    if (_board[sq.index()] == WP) {
                        group = squaresContig(sq, visited, WP);
                        if (group != null) {
                            _whiteRegionSizes.add(group.size());
                            _whiteGroups.add(group);
                        }
                    } else if (_board[sq.index()] == BP) {
                        group = squaresContig(sq, visited, BP);
                        if (group != null) {
                            _blackRegionSizes.add(group.size());
                            _blackGroups.add(group);
                        }
                    }
                }
            }
        }

        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    static final Piece[][] TEST = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { BP, EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
        { EMP, BP, BP, WP, EMP, EMP, EMP, EMP    },
        { EMP, EMP, EMP, EMP, EMP, BP, WP, EMP   },
        { EMP, EMP, EMP, EMP, EMP, BP, EMP, EMP  },
        { EMP, EMP, BP, BP, EMP, EMP, EMP, EMP   },
        { EMP, BP, EMP, EMP, EMP, EMP, WP, EMP   },
        { EMP, EMP, EMP, BP, EMP, EMP, EMP, EMP  }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of continguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();

    /** Squares belonging to each group sorted into lists.*/
    private final ArrayList<List>
        _whiteGroups = new ArrayList<>(),
        _blackGroups = new ArrayList<>();
}
