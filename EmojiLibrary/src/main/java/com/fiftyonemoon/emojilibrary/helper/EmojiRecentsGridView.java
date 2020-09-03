package com.fiftyonemoon.emojilibrary.helper;

import android.content.Context;
import android.widget.GridView;

import com.fiftyonemoon.emojilibrary.R;
import com.fiftyonemoon.emojilibrary.category.Emoji;

public class EmojiRecentsGridView extends EmojiGridView implements EmojiRecentInterface {
    EmojiAdapter mAdapter;
    private boolean mUseSystemDefault = false;



    public EmojiRecentsGridView(Context context, Emoji[] emoji,
                                EmojiRecentInterface recents, EmojiPopup emojiconsPopup, boolean useSystemDefault) {
        super(context, emoji, recents, emojiconsPopup,useSystemDefault);
        this.mUseSystemDefault=useSystemDefault;
        EmojiRecentsManager recents1 = EmojiRecentsManager
                .getInstance(rootView.getContext());
        mAdapter = new EmojiAdapter(rootView.getContext(),  recents1,mUseSystemDefault);
        mAdapter.setEmojiClickListener(new OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emoji emoji) {
                if (mEmojiconPopup.onEmojiconClickedListener != null) {
                    mEmojiconPopup.onEmojiconClickedListener.onEmojiconClicked(emoji);
                }
            }
        });
        GridView gridView = (GridView) rootView.findViewById(R.id.Emoji_GridView);
        gridView.setAdapter(mAdapter);
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addRecentEmoji(Context context, Emoji emoji) {
        EmojiRecentsManager recents = EmojiRecentsManager
                .getInstance(context);
        recents.push(emoji);

        // notify dataset changed
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

}