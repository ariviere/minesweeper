package com.ar.minesweeper.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ar.minesweeper.R;
import com.ar.minesweeper.model.Board;
import com.ar.minesweeper.sensor.ShakingDetector;
import com.ar.minesweeper.settings.GameConfiguration;
import com.ar.minesweeper.settings.SettingsActivity;

/**
 * Game activity where the board is shown
 * and where you can play the game
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener,
        SquaresAdapter.Listener, ShakingDetector.OnShakeListener {

    private Board mBoard;
    private SquaresAdapter mSquaresAdapter;
    private ImageView mStatusImage;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakingDetector mShakingDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initGame();

        initStatusImage();

        /**
         * init shake to cheat
         */
        initShakingDetector();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.status_button) {
            mBoard.setGameStatus(Board.GAME_RUNNING);
            mStatusImage.setImageResource(R.drawable.ic_happy);
            mBoard.resetSquares();
            mSquaresAdapter.notifyDataSetChanged();
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
            mSquaresAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onShake() {
        if (mBoard.getGameStatus() == Board.GAME_RUNNING) {
            mBoard.uncoverMines(Board.UNCOVER_CHEAT);
            mSquaresAdapter.notifyDataSetChanged();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivityForResult(settingsIntent, SettingsActivity.ACTIVITY_CODE);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SettingsActivity.ACTIVITY_CODE
                && (mBoard.getColumnsNumber() != GameConfiguration.getBoardColumnsNumber(this)
                || mBoard.getRowsNumber() != GameConfiguration.getBoardRowsNumber(this)
                || mBoard.getMinesNumber() != GameConfiguration.getBoardMinesNumber(this))) {
            initGame();
        }
    }

    private void initGame() {
        initBoardModel();
        setBoardSize();
        initBoardUI();
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
        mBoard = new Board(GameConfiguration.getBoardRowsNumber(this),
                GameConfiguration.getBoardColumnsNumber(this),
                GameConfiguration.getBoardMinesNumber(this));
    }

    /**
     * set the board size according to the height and the width of the device
     */
    private void setBoardSize() {
        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        if (mBoard.getColumnsNumber() < 8) {
            mBoard.setBoardPixelsSize((screenSize.widthPixels / 8) * mBoard.getColumnsNumber());
        } else {
            mBoard.setBoardPixelsSize(screenSize.widthPixels);
        }
    }

    /**
     * display the ui elements of the board
     */
    private void initBoardUI() {
        GridView boardView = (GridView) findViewById(R.id.board_view);

        LinearLayout.LayoutParams boardLayoutParams = new LinearLayout.LayoutParams(
                mBoard.getBoardPixelsSize(), ViewGroup.LayoutParams.WRAP_CONTENT);
        boardLayoutParams.gravity = Gravity.CENTER;
        boardView.setLayoutParams(boardLayoutParams);

        boardView.setNumColumns(mBoard.getColumnsNumber());
        mSquaresAdapter = new SquaresAdapter(this, mBoard);
        mSquaresAdapter.setListener(this);
        boardView.setAdapter(mSquaresAdapter);
    }

    private void initShakingDetector() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakingDetector = new ShakingDetector();
        mShakingDetector.setOnShakeListener(this);
    }
}
