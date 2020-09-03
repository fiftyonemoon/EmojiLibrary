package com.fiftyonemoon.emojilibrary;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.fiftyonemoon.emojilibrary.category.Emoji;
import com.fiftyonemoon.emojilibrary.helper.EmojiGridView;
import com.fiftyonemoon.emojilibrary.helper.EmojiPopup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EmojiActions implements View.OnFocusChangeListener {

    private boolean useSystemEmoji = false;
    private EmojiPopup popup;
    private Context context;
    private View rootView;
    private ImageView emojiButton;
    private int KeyBoardIcon = R.drawable.ic_baseline_keyboard_24;
    private int emojiIcon = R.drawable.ic_baseline_emoji_emotions_24;
    private KeyboardListener keyboardListener;
    private List<EmojiEditText> emojiEditTextList = new ArrayList<>();
    private EmojiEditText emojiEditText;


    /**
     * Constructor
     *
     * @param context              The context of current activity.
     * @param rootView         The top most layout in your view hierarchy. The difference of this
     *                         view and the screen height will be used to calculate the keyboard
     *                         height.
     * @param emojiconEmojiEditText The Id of EditText.
     * @param emojiButton      The Id of ImageButton used to open Emoji
     */
    public EmojiActions(Context context, View rootView, EmojiEditText emojiconEmojiEditText, ImageView emojiButton) {
        this.emojiButton = emojiButton;
        this.context = context;
        this.rootView = rootView;
        addEmojiEditTextList(emojiconEmojiEditText);
        this.popup = new EmojiPopup(rootView, context, useSystemEmoji);
        initListeners();
    }

    public void addEmojiEditTextList(EmojiEditText... emojiconEmojiEditText) {
        Collections.addAll(emojiEditTextList, emojiconEmojiEditText);
        for (EmojiEditText emojiEditText : emojiconEmojiEditText) {
            emojiEditText.setOnFocusChangeListener(this);
        }
    }


    /**
     * Constructor
     *
     * @param context              The context of current activity.
     * @param rootView         The top most layout in your view hierarchy. The difference of this
     *                         view and the screen height will be used to calculate the keyboard
     *                         height.
     * @param emojiconEmojiEditText The Id of EditText.
     */
    public EmojiActions(Context context, View rootView, EmojiEditText emojiconEmojiEditText) {
        addEmojiEditTextList(emojiconEmojiEditText);
        this.context = context;
        this.rootView = rootView;
        this.popup = new EmojiPopup(rootView, context, useSystemEmoji);
        initListeners();
    }

    public EmojiPopup getPopup() {
        return popup;
    }

    /**
     * @param emojiButton The Id of ImageButton used to open Emoji
     */
    public void setEmojiButton(ImageView emojiButton) {
        this.emojiButton = emojiButton;
        initEmojiButtonListener();
    }

    /**
     * @param iconPressedColor The color of icons on tab
     * @param tabsColor        The color of tabs background
     * @param backgroundColor  The color of emoji background
     */
    public void setColors(int iconPressedColor, int tabsColor, int backgroundColor) {
        this.popup.setColors(iconPressedColor, tabsColor, backgroundColor);
    }

    public void setDrawableIcon(int keyboardIcon, int emojiIcon) {
        this.KeyBoardIcon = keyboardIcon;
        this.emojiIcon = emojiIcon;
    }

    public void setOnClickEditTextOpenSoftKeyboard(EmojiEditText emojiEditText){
       emojiEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               hidePopup();
           }
       });
    }

    private void setUseSystemEmoji(boolean useSystemEmoji) {
        this.useSystemEmoji = useSystemEmoji;
        for (EmojiEditText emojiEditText : emojiEditTextList) {
            emojiEditText.setUseSystemDefault(useSystemEmoji);
        }
        refresh();
    }


    private void refresh() {
        popup.updateUseSystemDefault(useSystemEmoji);
    }

    public void initListeners() {
        if (emojiEditText == null) {
            emojiEditText = emojiEditTextList.get(0);
        }
        //Will automatically set size according to the soft keyboard size
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(emojiButton, emojiIcon);
            }
        });

        //If the text keyboard closes, also dismiss the emoji popup
        popup.setOnSoftKeyboardOpenCloseListener(new EmojiPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {
                if (keyboardListener != null) {
                    keyboardListener.onKeyboardOpen();
                }
            }

            @Override
            public void onKeyboardClose() {
                if (keyboardListener != null) {
                    keyboardListener.onKeyboardClose();
                }
                if (popup.isShowing()) {
                    popup.dismiss();
                }
            }
        });

        //On emoji clicked, add it to edittext
        popup.setOnEmojiconClickedListener(new EmojiGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emoji emoji) {
                if (emoji == null) {
                    return;
                }

                int start = emojiEditText.getSelectionStart();
                int end = emojiEditText.getSelectionEnd();
                if (start < 0) {
                    emojiEditText.append(emoji.getEmoji());
                } else {
                    emojiEditText.getText()
                            .replace(Math.min(start, end),
                                    Math.max(start, end),
                                    emoji.getEmoji(),
                                    0,
                                    emoji.getEmoji()
                                            .length());
                }
            }
        });

        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiconBackspaceClickedListener(new EmojiPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                emojiEditText.dispatchKeyEvent(event);
            }
        });

        // To toggle between text keyboard and emoji keyboard keyboard(Popup)
        initEmojiButtonListener();
    }

    private void initEmojiButtonListener() {
        if (emojiButton != null) {
            emojiButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    togglePopupVisibility();
                }
            });
        }
    }

    private void togglePopupVisibility() {
        if (!popup.isShowing()) {
            showPopup();
        } else {
            hidePopup();
        }
    }

    public void showPopup() {
        if (emojiEditText == null) {
            emojiEditText = emojiEditTextList.get(0);
        }
        if (popup.isKeyBoardOpen()) {
            popup.showAtBottom();
        } else {
            emojiEditText.setFocusableInTouchMode(true);
            emojiEditText.requestFocus();
            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(emojiEditText, InputMethodManager.SHOW_IMPLICIT);
            popup.showAtBottomPending();
        }
        changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
    }

    public void hidePopup() {
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        }
    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId) {
        if (iconToBeChanged != null) {
            iconToBeChanged.setImageResource(drawableResourceId);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            if (view instanceof EmojiEditText) {
                emojiEditText = (EmojiEditText) view;
            }
        }
    }


    public interface KeyboardListener {
        void onKeyboardOpen();

        void onKeyboardClose();
    }

    public void setKeyboardListener(KeyboardListener listener) {
        this.keyboardListener = listener;
    }

}
