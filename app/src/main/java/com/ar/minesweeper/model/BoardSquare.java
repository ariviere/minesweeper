package com.ar.minesweeper.model;

/**
 * Created by ariviere on 21/10/15.
 * square (or tiles) of the board
 */
public class BoardSquare {

    private boolean mHasMine;
    private int mAdjacentMines = 0;
    private boolean mIsOpened = false;
    private boolean mIsFailedSquare = false;
    private int positionY;
    private int positionX;

    public BoardSquare(int positionY, int positionX) {
        this.positionY = positionY;
        this.positionX = positionX;
    }

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
     * add a mine adjacent
     */
    public void addAdjacentMine() {
        mAdjacentMines++;
    }

    /**
     * the square has been clicked
     * @return true if the square has been discovered
     */
    public boolean isOpened() {
        return mIsOpened;
    }

    /**
     * the square has been clicked
     * @param isUncovered has the square been discovered
     */
    public void setIsOpened(boolean isUncovered) {
        mIsOpened = isUncovered;
    }

    /**
     * to know if the square has been clicked and failed (contains mine)
     * @return true if the square has been failed
     */
    public boolean hasFailedSquare() {
        return mIsFailedSquare;
    }

    /**
     * to know if the square has been clicked and failed (contains mine)
     * @param isFailedSquare true if the square has been failed
     */
    public void setIsFailedSquare(boolean isFailedSquare) {
        mIsFailedSquare = isFailedSquare;
    }

    /**
     * get position y on the board
     * @return position y on the board
     */
    public int getY() {
        return positionY;
    }

    /**
     * set position y on the board
     * @param positionY on the board
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * get position x on the board
     * @return position x on the board
     */
    public int getX() {
        return positionX;
    }

    /**
     * set position x of the square on the board
     * @param positionX of the square on the board
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
}
