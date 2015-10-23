package com.ar.minesweeper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.ar.minesweeper.GameConfiguration;
import com.ar.minesweeper.R;
import com.ar.minesweeper.model.Board;

/**
 * Game activity where the board is shown
 * and where you can play the game
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener,
    SquaresAdapter.Listener {

    private Board mBoard;
    private GridView mBoardView;
    private SquaresAdapter mSquaresAdapter;
    private ImageView mStatusImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initStatusImage();

        initBoardModel();

        setBoardSize();

        initBoardUI();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.status_button) {
            mStatusImage.setImageResource(R.drawable.ic_happy);
            initBoardModel();
            setBoardSize();
            initBoardUI();
        }
    }

    @Override
    public void onMineClicked() {
        mBoard.uncoverMines();
        mSquaresAdapter.notifyDataSetChanged();
        mStatusImage.setImageResource(R.drawable.ic_sad);
    }

    /**
     * init the status image
     * click to start new game
     */
    private void initStatusImage() {
        mStatusImage = (ImageView) findViewById(R.id.status_button);

        mStatusImage.setOnClickListener(this);
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
        mSquaresAdapter = new SquaresAdapter(this, mBoard);
        mSquaresAdapter.setListener(this);
        mBoardView.setAdapter(mSquaresAdapter);
    }
}
