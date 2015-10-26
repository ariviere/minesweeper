package com.ar.minesweeper.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ar.minesweeper.GameConfiguration;
import com.ar.minesweeper.model.Board;
import com.ar.minesweeper.model.BoardSquare;

/**
 * Created by ariviere on 22/10/15.
 * Use to link the board to each square (with the GridView)
 */
public class SquaresAdapter extends BaseAdapter implements SquareView.Listener {

    private Context mContext;
    private Board mBoard;
    private Listener mListener;

    /**
     * constructor
     *
     * @param context context of the app
     * @param board   board with squares
     */
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

        int squareY = i / GameConfiguration.BOARD_SIZE;
        int squareX = i % GameConfiguration.BOARD_SIZE;

        squareView = new SquareView(mContext);
        squareView.setListener(this);

        squareView.setLayoutParams(new GridView.LayoutParams(
                mBoard.getSquarePixelsSize(), mBoard.getSquarePixelsSize()));
        squareView.setPadding(0, 0, 0, 0);

        squareView.setModel(mBoard.getBoardSquares()[squareY][squareX], mBoard.getGameStatus());

        return squareView;
    }

    @Override
    public void onMineClicked() {
        mListener.onMineClicked();
    }

    @Override
    public void onZeroAdjacentClicked(BoardSquare square) {
        mBoard.uncoverAdjacentZero(square);
        notifyDataSetChanged();
    }

    @Override
    public void onSquareDiscovered() {
        mBoard.decrementSquaresToDiscover();
        mListener.onSquareDiscovered();
    }

    /**
     * set listener to the adapter
     *
     * @param listener listener from game activity
     */
    public void setListener(Listener listener) {
        mListener = listener;
    }

    /**
     * listener of the adapter
     */
    public interface Listener {
        /**
         * triggered when a mine is clicked
         */
        void onMineClicked();

        /**
         * triggered when a square is discovered
         */
        void onSquareDiscovered();
    }
}
