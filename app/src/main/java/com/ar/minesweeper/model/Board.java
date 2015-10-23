package com.ar.minesweeper.model;

import com.ar.minesweeper.GameConfiguration;

/**
 * Created by ariviere on 21/10/15.
 * Board object where squares are placed
 */
public class Board {

    private BoardSquare[][] mBoardSquares;
    private int mBoardPixelsSize;

    /**
     * get squares of the board
     * @return the board's squares
     */
    public BoardSquare[][] getBoardSquares() {
        return mBoardSquares;
    }

    /**
     * set squares of the board
     * @param boardSquares the board's square
     */
    public void setBoardSquares(BoardSquare[][] boardSquares) {
        mBoardSquares = boardSquares;
    }

    /**
     * get the size of the board in pixels
     * @return the size of the board in pixels
     */
    public int getBoardPixelsSize() {
        return mBoardPixelsSize;
    }

    /**
     * set the size of the board in pixels
     * @param boardPixelsSize the size of the board in pixels
     */
    public void setBoardPixelsSize(int boardPixelsSize) {
        mBoardPixelsSize = boardPixelsSize;
    }

    /**
     * get the size of a square in pixels
     * @return the size of a square in pixels
     */
    public int getSquarePixelsSize() {
        return mBoardPixelsSize / GameConfiguration.BOARD_SIZE;
    }
}
