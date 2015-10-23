package com.ar.minesweeper.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.ar.minesweeper.GameConfiguration;
import com.ar.minesweeper.R;
import com.ar.minesweeper.model.Board;
import com.ar.minesweeper.model.BoardSquare;

import java.util.HashSet;
import java.util.Set;

/**
 * Game activity where the board is shown
 * and where you can play the game
 */
public class GameActivity extends AppCompatActivity {

    private Board mBoard;
    private GridView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initBoardModel();

        setBoardSize();

        initBoardUI();
    }

    /**
     * init the model of the board (random mines, etc)
     */
    private void initBoardModel() {
        mBoard = new Board();
    }

    /**
     * set the board size according to the height and the width of the device
     */
    private void setBoardSize() {
        int boardMargin = getResources().getDimensionPixelSize(R.dimen.board_margin);

        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);

        if (screenSize.heightPixels > screenSize.widthPixels) {
            mBoard.setBoardPixelsSize(screenSize.widthPixels - 2 * boardMargin);
        } else {
            mBoard.setBoardPixelsSize(screenSize.heightPixels - 2 * boardMargin);
        }
    }

    /**
     * display the ui elements of the board
     */
    private void initBoardUI() {
        mBoardView = (GridView) findViewById(R.id.board_view);

        FrameLayout.LayoutParams boardLayoutParams = new FrameLayout.LayoutParams(
                mBoard.getBoardPixelsSize(), mBoard.getBoardPixelsSize());
        boardLayoutParams.gravity = Gravity.CENTER;
        mBoardView.setLayoutParams(boardLayoutParams);
        mBoardView.setNumColumns(GameConfiguration.BOARD_SIZE);
        mBoardView.setAdapter(new SquaresAdapter(this, mBoard));
    }
}
