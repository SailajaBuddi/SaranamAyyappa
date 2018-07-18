package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.FeedPojo;
import com.anvesh.saranamayyappa.utils.util;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.anvesh.saranamayyappa.utils.util.convertFromUTCWithTime;

public class FeedHolder extends BaseViewHolder {

    CircleImageView circleImageView;
    TextView FullName,Time,Description;
    CardView cardView;
    ImageView imageView;

    public FeedHolder(View view) {
        super(view);
        circleImageView = (CircleImageView)view.findViewById(R.id.profile);
        FullName = (TextView)view.findViewById(R.id.grp_name);
        Time = (TextView)view.findViewById(R.id.time_ago);
        Description = (TextView)view.findViewById(R.id.wall_dec);
        imageView = (ImageView)view.findViewById(R.id.img);

    }

    public void setData(final Context context, final FeedPojo feedPojo){

        if(feedPojo != null){
            FullName.setText(feedPojo.getFullName());
            Time.setText(convertFromUTCWithTime(feedPojo.getCreatedDate()));
            Description.setText(feedPojo.getDescription());

            if(feedPojo.getDescription()==null || feedPojo.getDescription()==""){
                Description.setVisibility(View.GONE);
                Toast.makeText(context, "*"+feedPojo.getDescription()+"*", Toast.LENGTH_SHORT).show();
              }else{ Description.setVisibility(View.VISIBLE);}


            if (TextUtils.isEmpty(feedPojo.getProfileImage())) {
                Picasso.with(context)
                        .load(R.drawable.god)
                        .into(circleImageView);
            } else {
                Picasso.with(context)
                        .load(feedPojo.getProfileImage())
                        .placeholder(R.drawable.god)
                        .into(circleImageView);
            }

            if (TextUtils.isEmpty(feedPojo.getImageUrl())) {
                Picasso.with(context)
                        .load(R.drawable.feed_placeholder)
                        .into(imageView);
                imageView.setVisibility(View.GONE);
            } else {
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(feedPojo.getImageUrl())
                        .placeholder(R.drawable.feed_placeholder)
                        .into(imageView);
            }


        }


    }
}
