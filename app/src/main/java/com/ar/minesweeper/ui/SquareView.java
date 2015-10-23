package com.ar.minesweeper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.minesweeper.R;

/**
 * Created by ariviere on 22/10/15.
 * Display a board square on the screen
 */
public class SquareView extends FrameLayout implements View.OnClickListener,
    View.OnLongClickListener {

    private ImageView mSquareImage;
    private TextView mSquareText;

    public SquareView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init(context);
        }
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context);
        }
    }

    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context);
        }
    }

    @Override
    public void onClick(View view) {
        setBackgroundResource(R.color.background_material_light);
        showMinesCount();
    }

    @Override
    public boolean onLongClick(View view) {
        setBackgroundResource(R.color.background_material_light);
        showFlagImage();
        return false;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_square, this);

        mSquareImage = (ImageView) findViewById(R.id.square_image);
        mSquareText = (TextView) findViewById(R.id.square_text);

        setBackgroundResource(R.drawable.ripple_square_undiscovered);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    private void showMinesCount() {
        mSquareImage.setVisibility(View.GONE);
        mSquareText.setVisibility(View.VISIBLE);
        mSquareText.setText("4");
    }

    private void showMineImage() {
        mSquareImage.setImageResource(R.drawable.ic_mine);
        mSquareImage.setVisibility(View.VISIBLE);
        mSquareText.setVisibility(View.GONE);
    }

    private void showFlagImage() {
        mSquareImage.setImageResource(R.drawable.ic_flag);
        mSquareImage.setVisibility(View.VISIBLE);
        mSquareText.setVisibility(View.GONE);
    }


}
