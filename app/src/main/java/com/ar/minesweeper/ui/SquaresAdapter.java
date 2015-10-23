package com.ar.minesweeper.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ar.minesweeper.model.Board;
import com.ar.minesweeper.GameConfiguration;

/**
 * Created by ariviere on 22/10/15.
 * Use to link the board to each square (with the GridView)
 */
public class SquaresAdapter extends BaseAdapter {

    private Context mContext;
    private Board mBoard;

    public SquaresAdapter(Context context, Board board) {
        mContext = context;
        mBoard = board;
    }

    @Override
    public int getCount() {
        return GameConfiguration.BOARD_SIZE * GameConfiguration.BOARD_SIZE;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SquareView squareView;
        if (view == null) {
            squareView = new SquareView(mContext);
            squareView.setLayoutParams(new GridView.LayoutParams(mBoard.getSquarePixelsSize(), mBoard.getSquarePixelsSize()));
            squareView.setPadding(0, 0, 0, 0);
            squareView.setModel(mBoard.getBoardSquares()[i / GameConfiguration.BOARD_SIZE][i % GameConfiguration.BOARD_SIZE]);
        } else {
            squareView = (SquareView) view;
        }

        return squareView;
    }
}
