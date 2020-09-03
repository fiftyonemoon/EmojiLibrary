package com.fiftyonemoon.emojilibrary.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.fiftyonemoon.emojilibrary.R;
import com.fiftyonemoon.emojilibrary.category.Emoji;
import com.fiftyonemoon.emojilibrary.EmojiTextView;

import java.util.List;

class EmojiAdapter extends ArrayAdapter<Emoji> {
    private boolean mUseSystemDefault = false;
    EmojiGridView.OnEmojiconClickedListener emojiClickListener;


    public EmojiAdapter(Context context, List<Emoji> data, boolean useSystemDefault) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = useSystemDefault;
    }

    public EmojiAdapter(Context context, Emoji[] data, boolean useSystemDefault) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = useSystemDefault;
    }


    public void setEmojiClickListener(EmojiGridView.OnEmojiconClickedListener listener){
        this.emojiClickListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.emojicon_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (EmojiTextView) v.findViewById(R.id.emojicon_icon);
            holder.icon.setUseSystemDefault(mUseSystemDefault);

            v.setTag(holder);
        }

         Emoji emoji = getItem(position);
         ViewHolder holder = (ViewHolder) v.getTag();
             holder.icon.setText(emoji.getEmoji());
                holder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emojiClickListener.onEmojiconClicked(getItem(position));
                    }
                });

        return v;
    }

    class ViewHolder {
        EmojiTextView icon;
    }
}