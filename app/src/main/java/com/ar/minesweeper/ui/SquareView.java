package com.ar.minesweeper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.minesweeper.R;
import com.ar.minesweeper.model.Board;
import com.ar.minesweeper.model.BoardSquare;

/**
 * Created by ariviere on 22/10/15.
 * Display a board square on the screen
 */
public class SquareView extends FrameLayout implements View.OnClickListener,
        View.OnLongClickListener {

    private ImageView mSquareImage;
    private TextView mSquareText;

    private BoardSquare mSquare;

    private Listener mListener;

    /**
     * constructor
     *
     * @param context context
     */
    public SquareView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init(context);
        }
    }

    /**
     * constructor
     *
     * @param context context
     * @param attrs   attrs
     */
    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context);
        }
    }

    /**
     * constructor
     *
     * @param context      context
     * @param attrs        attrs
     * @param defStyleAttr defStyleAttr
     */
    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context);
        }
    }

    @Override
    public void onClick(View view) {
        if (!mSquare.isOpened()) {
            mSquare.setIsOpened(true);
            mSquare.setIsFlagged(false);


            if (mSquare.hasMine()) {
                mSquare.setIsFailedSquare(true);
                mListener.onMineClicked();
            } else if (mSquare.getAdjacentMines() == 0) {
                mListener.onZeroAdjacentClicked(mSquare);
            }

            openSquare();

            mListener.onSquareDiscovered();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (!mSquare.isOpened()) {
            mSquare.setIsFlagged(!mSquare.isFlagged());
            showFlagImage();
        }
        return false;
    }

    /**
     * set model of the square view
     *
     * @param square     current square of the board
     * @param gameStatus status of the game
     */
    public void setModel(BoardSquare square, int gameStatus) {
        mSquare = square;

        if (mSquare.isOpened()) {
            openSquare();
        }

        if (mSquare.isFlagged()) {
            showFlagImage();
        }

        if (gameStatus == Board.GAME_RUNNING) {
            setOnClickListener(this);
            setOnLongClickListener(this);
        } else {
            setOnClickListener(null);
            setOnLongClickListener(null);
        }
    }

    /**
     * set listener for the square view
     *
     * @param listener listener from the squaresAdapter
     */
    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_square, this);

        mSquareImage = (ImageView) findViewById(R.id.square_image);
        mSquareText = (TextView) findViewById(R.id.square_text);

        setBackgroundResource(R.drawable.ripple_square_undiscovered);
    }

    private void showMinesCount() {
        mSquareImage.setVisibility(View.GONE);
        mSquareText.setVisibility(View.VISIBLE);
        mSquareText.setText(String.valueOf(mSquare.getAdjacentMines()));
    }

    private void showMineImage() {
        mSquareImage.setImageResource(R.drawable.ic_mine);
        mSquareImage.setVisibility(View.VISIBLE);
        mSquareText.setVisibility(View.GONE);
    }

    private void showFlagImage() {
        if (mSquare.isFlagged()) {
            mSquareImage.setImageResource(R.drawable.ic_flag);
            mSquareImage.setVisibility(View.VISIBLE);
            mSquareText.setVisibility(View.GONE);
        } else {
            mSquareImage.setVisibility(View.GONE);
        }
    }

    private void openSquare() {
        if (!mSquare.hasFailedSquare()) {
            setBackgroundResource(R.color.background_material_light);
        } else {
            setBackgroundResource(R.color.light_red);
        }

        if (mSquare.hasMine()) {
            showMineImage();
        } else if (mSquare.getAdjacentMines() > 0) {
            showMinesCount();
        }
    }

    /**
     * Listener for the square view
     */
    public interface Listener {
        /**
         * Triggered when a user click on a mine
         */
        void onMineClicked();

        /**
         * Triggered when a square with 0 adjacent mine is clicked
         *
         * @param square square to check
         */
        void onZeroAdjacentClicked(BoardSquare square);

        /**
         * Triggered when a square is discovered
         */
        void onSquareDiscovered();
    }
}
