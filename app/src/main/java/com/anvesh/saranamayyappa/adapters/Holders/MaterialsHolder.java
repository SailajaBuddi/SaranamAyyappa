package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.AyyappaSongsPojo;
import com.squareup.picasso.Picasso;

/**
 * Created by Anvesh on 7/15/2018.
 */

public class MaterialsHolder extends BaseViewHolder {

    ImageView videoImg;
    ImageView videoPlay,songPlay;
    View.OnClickListener listener;
    Context context;

    public MaterialsHolder(Context context, View itemView, View.OnClickListener listener) {
        super(itemView);
        this.listener = listener;
        this.context = context;
        videoImg = (ImageView) itemView.findViewById(R.id.iv_fcimg);
        videoPlay = (ImageView) itemView.findViewById(R.id.iv_videoPlay);
        songPlay = (ImageView) itemView.findViewById(R.id.iv_songPlay);
    }


    public void setData(final Context context, final AyyappaSongsPojo ayyappaSongsPojo) {
        if (ayyappaSongsPojo != null) {
            if (TextUtils.isEmpty(ayyappaSongsPojo.getImageUrl())) {
                Picasso.with(context)
                        .load(R.drawable.feed_placeholder)
                        .into(videoImg);
            } else {
                Picasso.with(context)
                        .load(ayyappaSongsPojo.getImageUrl())
                        .placeholder(R.drawable.feed_placeholder)
                        .into(videoImg);
            }
            videoPlay.setOnClickListener(listener);
            videoPlay.setTag(ayyappaSongsPojo);
            songPlay.setOnClickListener(listener);
            songPlay.setTag(ayyappaSongsPojo);
        }
    }
}
