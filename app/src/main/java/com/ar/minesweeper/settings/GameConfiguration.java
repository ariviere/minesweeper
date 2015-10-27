package com.ar.minesweeper.settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ariviere on 21/10/15.
 */
public final class GameConfiguration {

    /**
     * size of the board in squares for beginner
     */
    public static final int BOARD_SIZE_BEGINNER = 8;

    /**
     * number of mines on the board for beginner
     */
    public static final int MINES_COUNT_BEGINNER = 10;

    /**
     * size of the board in squares for intermediate
     */
    public static final int BOARD_SIZE_INTERMEDIATE = 16;

    /**
     * number of mines on the board for intermediate
     */
    public static final int MINES_COUNT_INTERMEDIATE = 40;

    /**
     * columns of the board in squares for expert
     */
    public static final int BOARD_COLUMNS_EXPERT = 16;

    /**
     * rows of the board in squares for expert
     */
    public static final int BOARD_ROWS_EXPERT = 30;

    /**
     * number of mines on the board for beginner
     */
    public static final int MINES_COUNT_EXPERT = 99;

    private static final String MINESWEEPER_PREFERENCES = "minesweeperPreferences";

    private static final String ROWS_NUMBER = "rowsNumber";
    private static final String COLUMNS_NUMBER = "columnsNumber";
    private static final String MINES_NUMBER = "minesNumber";


    private GameConfiguration() {
    }

    /**
     * set the number of rows
     * @param context context of the app
     * @param rowsNumber number of rows to save
     */
    public static void setBoardRowsNumber(Context context, int rowsNumber) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                MINESWEEPER_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ROWS_NUMBER, rowsNumber);
        editor.apply();
    }

    /**
     * get the number of rows for the board
     * @param context context of the app
     * @return the number of rows
     */
    public static int getBoardRowsNumber(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                MINESWEEPER_PREFERENCES, Context.MODE_PRIVATE);

        return sharedPref.getInt(ROWS_NUMBER, BOARD_SIZE_BEGINNER);
    }

    /**
     * set the number of columns
     * @param context context of the app
     * @param columnsNumber number of columns to save
     */
    public static void setBoardColumnsNumber(Context context, int columnsNumber) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                MINESWEEPER_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(COLUMNS_NUMBER, columnsNumber);
        editor.apply();
    }

    /**
     * get the number of columns for the board
     * @param context context of the app
     * @return the number of columns
     */
    public static int getBoardColumnsNumber(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                MINESWEEPER_PREFERENCES, Context.MODE_PRIVATE);

        return sharedPref.getInt(COLUMNS_NUMBER, BOARD_SIZE_BEGINNER);
    }

    /**
     * set the number of mines
     * @param context context of the app
     * @param minesNumber number of mines to save
     */
    public static void setBoardMinesNumber(Context context, int minesNumber) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                MINESWEEPER_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(MINES_NUMBER, minesNumber);
        editor.apply();
    }

    /**
     * get the number of mines for the board
     * @param context context of the app
     * @return the number of mines
     */
    public static int getBoardMinesNumber(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                MINESWEEPER_PREFERENCES, Context.MODE_PRIVATE);

        return sharedPref.getInt(MINES_NUMBER, MINES_COUNT_BEGINNER);
    }


}
