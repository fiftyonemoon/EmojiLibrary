package com.fiftyonemoon.emojilibrary.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.fiftyonemoon.emojilibrary.category.Emoji;

import java.util.ArrayList;
import java.util.StringTokenizer;



public class EmojiRecentsManager extends ArrayList<Emoji> {

    private static final String PREFERENCE_NAME = "emoji";
    private static final String PREF_RECENT = "recent_emojis";
    private static final String PREF_PAGE = "recent_page";

    private static final Object LOCK = new Object();
    private static EmojiRecentsManager sInstance;

    private Context mContext;

    private EmojiRecentsManager(Context context) {
        mContext = context.getApplicationContext();
        loadRecents();
    }

    public static EmojiRecentsManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new EmojiRecentsManager(context);
                }
            }
        }
        return sInstance;
    }

    public int getRecentPage() {
        return getPreferences().getInt(PREF_PAGE, 0);
    }

    public void setRecentPage(int page) {
        getPreferences().edit().putInt(PREF_PAGE, page).commit();
    }

    public void push(Emoji emoji) {
        // FIXME totally inefficient way of adding the emoji to the adapter
        // TODO this should be probably replaced by a deque
        if (contains(emoji)) {
            super.remove(emoji);
        }
        add(0, emoji);
    }

    @Override
    public boolean add(Emoji emoji) {
        boolean ret = super.add(emoji);
        return ret;
    }

    @Override
    public void add(int index, Emoji emoji) {
        super.add(index, emoji);
    }

    @Override
    public boolean remove(Object object) {
        boolean ret = super.remove(object);
        return ret;
    }

    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private void loadRecents() {
        SharedPreferences prefs = getPreferences();
        String str = prefs.getString(PREF_RECENT, "");
        StringTokenizer tokenizer = new StringTokenizer(str, "~");
        while (tokenizer.hasMoreTokens()) {
            try {
                add(new Emoji(tokenizer.nextToken()));
            }
            catch (NumberFormatException e) {
                // ignored
            }
        }
    }

    public void saveRecents() {
        StringBuilder str = new StringBuilder();
        int c = size();
        for (int i = 0; i < c; i++) {
            Emoji e = get(i);
            str.append(e.getEmoji());
            if (i < (c - 1)) {
                str.append('~');
            }
        }
        SharedPreferences prefs = getPreferences();
        prefs.edit().putString(PREF_RECENT, str.toString()).apply();
    }

}
