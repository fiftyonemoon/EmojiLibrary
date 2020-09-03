package com.fiftyonemoon.emojilibrary.helper;

import android.content.Context;
import com.fiftyonemoon.emojilibrary.category.Emoji;

public interface EmojiRecentInterface {
    void addRecentEmoji(Context context, Emoji emoji);
}