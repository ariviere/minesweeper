package com.ar.minesweeper;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    private int mBoardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setBoardSize();
    }


    /**
     * set the board size according to the height and the width of the device
     */
    private void setBoardSize() {
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        if (screenSize.x > screenSize.y) {
            mBoardSize = screenSize.y;
        } else {
            mBoardSize = screenSize.x;
        }

    }

}
