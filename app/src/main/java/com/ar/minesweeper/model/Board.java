package com.ar.minesweeper.model;

import android.graphics.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by ariviere on 21/10/15.
 * Board object where squares are placed
 */
public class Board {

    /**
     * static variable for game running
     */
    public static final int GAME_RUNNING = 1;

    /**
     * static variable for game won
     */
    public static final int GAME_WON = 2;

    /**
     * static variable for game lost
     */
    public static final int GAME_LOST = 3;

    /**
     * static variable to uncover mines in case of lose
     */
    public static final int UNCOVER_LOSE = 1;

    /**
     * static variable to uncover mines in case of win
     */
    public static final int UNCOVER_WIN = 2;

    /**
     * static variable to uncover mines in case of cheat
     */
    public static final int UNCOVER_CHEAT = 3;

    /**
     * 1 = Game running
     * 2 = Game won
     * 3 = Game lost
     */
    private int mGameStatus = GAME_RUNNING;

    private BoardSquare[][] mBoardSquares;
    private int mBoardPixelsSize;
    private LinkedList<BoardSquare> mSquaresQueue;
    private int mSquaresToDiscover;
    private boolean mCheatActivated = false;

    private int mRowsNumber;
    private int mColumnsNumber;
    private int mMinesNumber;

    /**
     * constructor
     */
    public Board(int rows, int columns, int minesCount) {

        mRowsNumber = rows;
        mColumnsNumber = columns;
        mMinesNumber = minesCount;

        // generate mines position
        Set<Point> minesPosition = generateMinesSet();

        //declare every square of the board
        mBoardSquares = new BoardSquare[mRowsNumber][mColumnsNumber];
        for (int i = 0; i < mRowsNumber; i++) {
            if (mBoardSquares[i] == null) {
                mBoardSquares[i] = new BoardSquare[mColumnsNumber];
            }
            for (int j = 0; j < mColumnsNumber; j++) {
                if (mBoardSquares[i][j] == null) {
                    mBoardSquares[i][j] = new BoardSquare(i, j);
                }

                if (minesPosition.contains(new Point(i, j))) {
                    mBoardSquares[i][j].setHasMine(true);
                    incrementeAdjacentMinesSquares(i, j);
                }
            }
        }

        mSquaresToDiscover = mRowsNumber * mColumnsNumber
                - mMinesNumber;
    }

    /**
     * get squares of the board
     *
     * @return the board's squares
     */
    public BoardSquare[][] getBoardSquares() {
        return mBoardSquares;
    }

    /**
     * set squares of the board
     *
     * @param boardSquares the board's square
     */
    public void setBoardSquares(BoardSquare[][] boardSquares) {
        mBoardSquares = boardSquares;
    }

    /**
     * get the size of the board in pixels
     *
     * @return the size of the board in pixels
     */
    public int getBoardPixelsSize() {
        return mBoardPixelsSize;
    }

    /**
     * set the size of the board in pixels
     *
     * @param boardPixelsSize the size of the board in pixels
     */
    public void setBoardPixelsSize(int boardPixelsSize) {
        mBoardPixelsSize = boardPixelsSize;
    }

    /**
     * get the size of a square in pixels
     *
     * @return the size of a square in pixels
     */
    public int getSquarePixelsSize() {
        return mBoardPixelsSize / mColumnsNumber;
    }

    /**
     * get status of the game
     *
     * @return status of the game (documentation at var)
     */
    public int getGameStatus() {
        return mGameStatus;
    }

    /**
     * set status of the game
     *
     * @param gameStatus status of the game
     */
    public void setGameStatus(int gameStatus) {
        mGameStatus = gameStatus;
    }

    /**
     * get the number of squares to discover
     *
     * @return the number of squares to discover
     */
    public int getSquaresToDiscover() {
        return mSquaresToDiscover;
    }

    /**
     * remove one the mSquaresToDiscover
     */
    public void decrementSquaresToDiscover() {
        mSquaresToDiscover--;
    }

    /**
     * number of rows
     *
     * @return the number of rows
     */
    public int getRowsNumber() {
        return mRowsNumber;
    }

    /**
     * number of rows
     *
     * @param rowsNumber on the board
     */
    public void setRowsNumber(int rowsNumber) {
        mRowsNumber = rowsNumber;
    }

    /**
     * number of columns
     *
     * @return the number of columns
     */
    public int getColumnsNumber() {
        return mColumnsNumber;
    }

    /**
     * number of columns
     *
     * @param columnsNumber on the board
     */
    public void setColumnsNumber(int columnsNumber) {
        mColumnsNumber = columnsNumber;
    }

    /**
     * number of mines
     *
     * @return the number of mines
     */
    public int getMinesNumber() {
        return mMinesNumber;
    }

    /**
     * get random position for mines
     * non deterministic function
     *
     * @return a set of random position for mines
     */
    public Set<Point> generateMinesSet() {
        Set<Point> minesPosition = new HashSet<>(mMinesNumber);
        for (int i = 0; i < mMinesNumber; i++) {
            Point randomPoint = new Point();
            do {
                randomPoint.x = (int) (Math.random() * (mRowsNumber));
                randomPoint.y = (int) (Math.random() * (mColumnsNumber));
            } while (minesPosition.contains(randomPoint));
            minesPosition.add(randomPoint);
        }

        return minesPosition;
    }

    /**
     * increment adjacent mine value to squares
     *
     * @param i row of the board
     * @param j column of the board
     */
    public void incrementeAdjacentMinesSquares(int i, int j) {
        if (i - 1 >= 0) {
            mBoardSquares[i - 1][j].addAdjacentMine();

            if (j - 1 >= 0) {
                mBoardSquares[i - 1][j - 1].addAdjacentMine();
            }

            if (j + 1 < mColumnsNumber) {
                mBoardSquares[i - 1][j + 1].addAdjacentMine();
            }
        }

        if (i + 1 < mRowsNumber) {
            if (mBoardSquares[i + 1] == null) {
                mBoardSquares[i + 1] = new BoardSquare[mColumnsNumber];
            }

            if (mBoardSquares[i + 1][j] == null) {
                mBoardSquares[i + 1][j] = new BoardSquare(i + 1, j);
            }
            mBoardSquares[i + 1][j].addAdjacentMine();

            if (j - 1 >= 0) {
                if (mBoardSquares[i + 1][j - 1] == null) {
                    mBoardSquares[i + 1][j - 1] = new BoardSquare(i + 1, j - 1);
                }
                mBoardSquares[i + 1][j - 1].addAdjacentMine();
            }

            if (j + 1 < mColumnsNumber) {
                if (mBoardSquares[i + 1][j + 1] == null) {
                    mBoardSquares[i + 1][j + 1] = new BoardSquare(i + 1, j + 1);
                }
                mBoardSquares[i + 1][j + 1].addAdjacentMine();
            }
        }

        if (j - 1 >= 0) {
            mBoardSquares[i][j - 1].addAdjacentMine();
        }

        if (j + 1 < mColumnsNumber) {
            if (mBoardSquares[i][j + 1] == null) {
                mBoardSquares[i][j + 1] = new BoardSquare(i, j + 1);
            }
            mBoardSquares[i][j + 1].addAdjacentMine();
        }
    }

    /**
     * to uncover all the mines
     *
     * @param uncoverMode either Board.UNCOVER_WIN, Board.UNCOVER.LOSE, Board.UNCOVER_CHEAT
     */
    public void uncoverMines(int uncoverMode) {
        for (int i = 0; i < mRowsNumber; i++) {
            for (int j = 0; j < mColumnsNumber; j++) {

                if (mBoardSquares[i][j].hasMine()) {
                    if (uncoverMode == UNCOVER_WIN) {
                        mBoardSquares[i][j].setIsFlagged(true);
                    } else if (uncoverMode == UNCOVER_LOSE) {
                        mBoardSquares[i][j].setIsOpened(true);
                        mBoardSquares[i][j].setIsFlagged(false);
                    } else if (uncoverMode == UNCOVER_CHEAT) {
                        if (mCheatActivated) {
                            mBoardSquares[i][j].setIsOpened(true);
                        } else {
                            mBoardSquares[i][j].setIsOpened(false);
                        }
                    }
                }
            }
        }

        if (uncoverMode == UNCOVER_CHEAT) {
            mCheatActivated = !mCheatActivated;
        }
    }

    /**
     * discover squares adjacent to 0 and do the same if a square with 0 adjacent is discovered
     *
     * @param square square opened
     */
    public void uncoverAdjacentZero(BoardSquare square) {
        mSquaresQueue = new LinkedList<>();
        mSquaresQueue.add(mBoardSquares[square.getY()][square.getX()]);
        while (!mSquaresQueue.isEmpty()) {

            BoardSquare queuedSquare = mSquaresQueue.getFirst();
            if (queuedSquare.getY() - 1 >= 0) {
                processUncoverSquare(mBoardSquares[queuedSquare.getY() - 1][queuedSquare.getX()]);

                if (queuedSquare.getX() - 1 >= 0) {
                    processUncoverSquare(mBoardSquares[queuedSquare.getY() - 1][queuedSquare.getX() - 1]);
                }

                if (queuedSquare.getX() + 1 < mColumnsNumber) {
                    processUncoverSquare(mBoardSquares[queuedSquare.getY() - 1][queuedSquare.getX() + 1]);
                }
            }

            if (queuedSquare.getY() + 1 < mRowsNumber) {
                processUncoverSquare(mBoardSquares[queuedSquare.getY() + 1][queuedSquare.getX()]);

                if (queuedSquare.getX() - 1 >= 0) {
                    processUncoverSquare(mBoardSquares[queuedSquare.getY() + 1][queuedSquare.getX() - 1]);
                }

                if (queuedSquare.getX() + 1 < mColumnsNumber) {
                    processUncoverSquare(mBoardSquares[queuedSquare.getY() + 1][queuedSquare.getX() + 1]);
                }
            }

            if (queuedSquare.getX() - 1 >= 0) {
                processUncoverSquare(mBoardSquares[queuedSquare.getY()][queuedSquare.getX() - 1]);
            }

            if (queuedSquare.getX() + 1 < mColumnsNumber) {
                processUncoverSquare(mBoardSquares[queuedSquare.getY()][queuedSquare.getX() + 1]);
            }


            mSquaresQueue.removeFirst();
        }

        mSquaresQueue = null;
    }

    /**
     * reset board squares (new game)
     */
    public void resetSquares() {
        // generate mines position
        Set<Point> minesPosition = generateMinesSet();

        mBoardSquares = new BoardSquare[mRowsNumber][mColumnsNumber];
        for (int i = 0; i < mRowsNumber; i++) {
            if (mBoardSquares[i] == null) {
                mBoardSquares[i] = new BoardSquare[mColumnsNumber];
            }
            for (int j = 0; j < mColumnsNumber; j++) {
                if (mBoardSquares[i][j] == null) {
                    mBoardSquares[i][j] = new BoardSquare(i, j);
                }

                if (minesPosition.contains(new Point(i, j))) {
                    mBoardSquares[i][j].setHasMine(true);
                    incrementeAdjacentMinesSquares(i, j);
                }
            }
        }

        mSquaresToDiscover = mRowsNumber * mColumnsNumber
                - mMinesNumber;
    }

    private void processUncoverSquare(BoardSquare square) {
        if (!square.isOpened()) {
            mSquaresToDiscover--;
            square.setIsFlagged(false);
            square.setIsOpened(true);
            if (square.getAdjacentMines() == 0) {
                mSquaresQueue.addLast(square);
            }
        }
    }
}
