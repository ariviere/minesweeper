package com.ar.minesweeper.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ar.minesweeper.R;

/**
 * Created by ariviere on 26/10/15.
 * Settings activity
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * activity code for start activity for result
     */
    public static final int ACTIVITY_CODE = 1;

    private RadioButton mBeginnerButton;
    private RadioButton mIntermediateButton;
    private RadioButton mExpertButton;
    private RadioButton mCustomButton;
    private View mCustomContainer;

    private EditText mRowsInput;
    private EditText mColumnsInput;
    private EditText mMinesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.game_settings));

        setupGameModes();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.beginner_button:
                mCustomContainer.setVisibility(View.INVISIBLE);
                GameConfiguration.setBoardColumnsNumber(this, GameConfiguration.BOARD_SIZE_BEGINNER);
                GameConfiguration.setBoardRowsNumber(this, GameConfiguration.BOARD_SIZE_BEGINNER);
                GameConfiguration.setBoardMinesNumber(this, GameConfiguration.MINES_COUNT_BEGINNER);
                break;
            case R.id.intermediate_button:
                mCustomContainer.setVisibility(View.INVISIBLE);
                GameConfiguration.setBoardColumnsNumber(this, GameConfiguration.BOARD_SIZE_INTERMEDIATE);
                GameConfiguration.setBoardRowsNumber(this, GameConfiguration.BOARD_SIZE_INTERMEDIATE);
                GameConfiguration.setBoardMinesNumber(this, GameConfiguration.MINES_COUNT_INTERMEDIATE);
                break;
            case R.id.expert_button:
                mCustomContainer.setVisibility(View.INVISIBLE);
                GameConfiguration.setBoardColumnsNumber(this, GameConfiguration.BOARD_COLUMNS_EXPERT);
                GameConfiguration.setBoardRowsNumber(this, GameConfiguration.BOARD_ROWS_EXPERT);
                GameConfiguration.setBoardMinesNumber(this, GameConfiguration.MINES_COUNT_EXPERT);
                break;
            case R.id.custom_button:
                mCustomContainer.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void setupGameModes() {
        mBeginnerButton = (RadioButton) findViewById(R.id.beginner_button);
        mIntermediateButton = (RadioButton) findViewById(R.id.intermediate_button);
        mExpertButton = (RadioButton) findViewById(R.id.expert_button);
        mCustomButton = (RadioButton) findViewById(R.id.custom_button);
        mCustomContainer = findViewById(R.id.custom_container);

        mRowsInput = (EditText) findViewById(R.id.rows_input);
        mColumnsInput = (EditText) findViewById(R.id.columns_input);
        mMinesInput = (EditText) findViewById(R.id.mines_input);

        if (GameConfiguration.getBoardColumnsNumber(this) == GameConfiguration.BOARD_SIZE_BEGINNER
            && GameConfiguration.getBoardRowsNumber(this) == GameConfiguration.BOARD_SIZE_BEGINNER
            && GameConfiguration.getBoardMinesNumber(this) == GameConfiguration.MINES_COUNT_BEGINNER) {
            mBeginnerButton.setChecked(true);
        } else if (GameConfiguration.getBoardColumnsNumber(this) == GameConfiguration.BOARD_SIZE_INTERMEDIATE
                && GameConfiguration.getBoardRowsNumber(this) == GameConfiguration.BOARD_SIZE_INTERMEDIATE
                && GameConfiguration.getBoardMinesNumber(this) == GameConfiguration.MINES_COUNT_INTERMEDIATE) {
            mIntermediateButton.setChecked(true);
        } else if (GameConfiguration.getBoardColumnsNumber(this) == GameConfiguration.BOARD_COLUMNS_EXPERT
                && GameConfiguration.getBoardRowsNumber(this) == GameConfiguration.BOARD_ROWS_EXPERT
                && GameConfiguration.getBoardMinesNumber(this) == GameConfiguration.MINES_COUNT_EXPERT) {
            mExpertButton.setChecked(true);
        } else {
            mCustomButton.setChecked(true);
            mCustomContainer.setVisibility(View.VISIBLE);
            mRowsInput.setText(String.valueOf(GameConfiguration.getBoardRowsNumber(this)));
            mColumnsInput.setText(String.valueOf(GameConfiguration.getBoardColumnsNumber(this)));
            mMinesInput.setText(String.valueOf(GameConfiguration.getBoardMinesNumber(this)));
        }

        mBeginnerButton.setOnClickListener(this);
        mIntermediateButton.setOnClickListener(this);
        mExpertButton.setOnClickListener(this);
        mCustomButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate:

                if (mCustomButton.isChecked()) {
                    if (TextUtils.isEmpty(mColumnsInput.getText().toString())
                            || TextUtils.isEmpty(mRowsInput.getText().toString())
                            || TextUtils.isEmpty(mMinesInput.getText().toString())) {
                        mCustomContainer.setVisibility(View.VISIBLE);
                        return true;
                    }

                    int customColumns = Integer.valueOf(mColumnsInput.getText().toString());
                    int customRows = Integer.valueOf(mRowsInput.getText().toString());
                    int customMines = Integer.valueOf(mMinesInput.getText().toString());

                    GameConfiguration.setBoardColumnsNumber(this, customColumns);
                    GameConfiguration.setBoardRowsNumber(this, customRows);
                    GameConfiguration.setBoardMinesNumber(this, customMines);
                }

                setResult(RESULT_OK);
                finish();
                break;
        }
        return true;
    }
}
