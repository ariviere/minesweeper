package com.ar.minesweeper.model;

/**
 * Created by ariviere on 21/10/15.
 * square (or tiles) of the board
 */
public class BoardSquare {

    private boolean mHasMine;
    private int mAdjacentMines;
    private boolean mIsUncovered;

    /**
     * determine if a square has a mine
     * @return true if a mine is inside the square
     */
    public boolean hasMine() {
        return mHasMine;
    }

    /**
     * determine if a square has a mine
     * @param hasMine mine is inside the square
     */
    public void setHasMine(boolean hasMine) {
        mHasMine = hasMine;
    }

    /**
     * number of mines adjacent to the square
     * @return the number of mines adjacent
     */
    public int getAdjacentMines() {
        return mAdjacentMines;
    }

    /**
     * number of mines adjacent to the square
     * @param adjacentMines the number of mines adjacent
     */
    public void setAdjacentMines(int adjacentMines) {
        mAdjacentMines = adjacentMines;
    }

    /**
     * the square has been clicked
     * @return true if the square has been discovered
     */
    public boolean isUncovered() {
        return mIsUncovered;
    }

    /**
     * the square has been clicked
     * @param isUncovered has the square been discovered
     */
    public void setIsUncovered(boolean isUncovered) {
        mIsUncovered = isUncovered;
    }
}
