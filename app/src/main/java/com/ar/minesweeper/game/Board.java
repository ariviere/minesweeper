package com.ar.minesweeper.game;

/**
 * Created by ariviere on 21/10/15.
 * Board object where squares are placed
 */
public class Board {

    private BoardSquare[][] mBoardSquares;

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


}
