package com.fiftyonemoon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.fiftyonemoon.emojilibrary.EmojiActions;
import com.fiftyonemoon.emojilibrary.EmojiEditText;
import com.fiftyonemoon.emojilibrary.EmojiTextView;

public class SampleActivity extends AppCompatActivity implements EmojiActions.KeyboardListener {
    private static String TAG = SampleActivity.class.getName();

    EmojiEditText emojiEditText;
    EmojiTextView emojiTextView;
    EmojiActions emojiActions;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //UI --->>>>
        emojiEditText = findViewById(R.id.edSample);
        emojiTextView = findViewById(R.id.tvSample);
        view = findViewById(R.id.rootView);

        //INIT ACTIONS  ---->>>
        emojiActions = new EmojiActions(this, view, emojiEditText, (ImageButton) findViewById(R.id.btnEmoji));

        //SET KEYBOARD LISTENER
        emojiActions.setKeyboardListener(this);

        //SET ON CLICK EDITTEXT OPEN SOFT KEYBOARD & HIDE EMOJI ---->>>
        emojiActions.setOnClickEditTextOpenSoftKeyboard(emojiEditText);

        //YOU CAN CHANGE EMOJI ICON WITH DRAWABLE ICON  --->>> 1. FOR KEYBOARD 2. FOR EMOJI ICON
        emojiActions.setDrawableIcon(R.drawable.ic_baseline_keyboard_24, R.drawable.ic_baseline_emoji_emotions_24);

    }

    //SET MESSAGE ----->>>>
    public void send(View view) {
        String message = emojiEditText.getText().toString();
        emojiTextView.setText(message);
        emojiEditText.setText("");
    }

    //KEYBOARD LISTENER ----->>>>
    @Override
    public void onKeyboardOpen() {
        Log.d(TAG, "onKeyboardOpen: OPEN");
    }

    @Override
    public void onKeyboardClose() {
        Log.d(TAG, "onKeyboardClose: CLOSE");
    }
}