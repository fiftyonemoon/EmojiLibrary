package com.fiftyonemoon.emojilibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.fiftyonemoon.emojilibrary.R;
import com.fiftyonemoon.emojilibrary.category.Emoji;
import com.fiftyonemoon.emojilibrary.category.People;

import java.util.Arrays;


public class EmojiGridView {
    public View rootView;
    EmojiPopup mEmojiconPopup;
    EmojiRecentInterface mRecents;
    Emoji[] mData;
    private boolean mUseSystemDefault = false;


    public EmojiGridView(Context context, Emoji[] emoji, EmojiRecentInterface recents, EmojiPopup emojiconPopup, boolean useSystemDefault) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.emojicon_grid, null);
        setRecents(recents);
        GridView gridView = (GridView) rootView.findViewById(R.id.Emoji_GridView);
        if (emoji== null) {
            mData = People.DATA;
        } else {
            Object[] o = (Object[]) emoji;
            mData = Arrays.asList(o).toArray(new Emoji[o.length]);
        }
        EmojiAdapter mAdapter = new EmojiAdapter(rootView.getContext(), mData ,useSystemDefault);
        mAdapter.setEmojiClickListener(new OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emoji emoji) {
                if (mEmojiconPopup.onEmojiconClickedListener != null) {
                    mEmojiconPopup.onEmojiconClickedListener.onEmojiconClicked(emoji);
                }
                if (mRecents != null) {
                    mRecents.addRecentEmoji(rootView.getContext(), emoji);
                }
            }
        });
        gridView.setAdapter(mAdapter);
    }

    private void setRecents(EmojiRecentInterface recents) {
        mRecents = recents;
    }

    public interface OnEmojiconClickedListener {
        void onEmojiconClicked(Emoji emoji);
    }

}