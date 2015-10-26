package com.ar.minesweeper.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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
import com.ar.minesweeper.hardware.ShakingDetector;
import com.ar.minesweeper.model.Board;

/**
 * Game activity where the board is shown
 * and where you can play the game
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener,
        SquaresAdapter.Listener, ShakingDetector.OnShakeListener {

    private Board mBoard;
    private GridView mBoardView;
    private SquaresAdapter mSquaresAdapter;
    private ImageView mStatusImage;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakingDetector mShakingDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initStatusImage();

        initBoardModel();

        setBoardSize();

        initBoardUI();

        /**
         * init shake to cheat
         */
        initShakingDetector();
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
        mBoard.setGameStatus(Board.GAME_LOST);
        mBoard.uncoverMines(Board.UNCOVER_LOSE);
        mSquaresAdapter.notifyDataSetChanged();
        mStatusImage.setImageResource(R.drawable.ic_sad);
    }

    @Override
    public void onSquareDiscovered() {
        if (mBoard.getSquaresToDiscover() == 0) {
            mStatusImage.setImageResource(R.drawable.ic_sunglasses);
            mBoard.uncoverMines(Board.UNCOVER_WIN);
            mBoard.setGameStatus(Board.GAME_WON);
        }
    }

    @Override
    public void onShake() {
        mBoard.uncoverMines(Board.UNCOVER_CHEAT);
        mSquaresAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakingDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakingDetector);
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
        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);

        if (screenSize.heightPixels > screenSize.widthPixels) {
            mBoard.setBoardPixelsSize(screenSize.widthPixels - 2);
        } else {
            mBoard.setBoardPixelsSize(screenSize.heightPixels - 2);
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

    private void initShakingDetector() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakingDetector = new ShakingDetector();
        mShakingDetector.setOnShakeListener(this);
    }
}
