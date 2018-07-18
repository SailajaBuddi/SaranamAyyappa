package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.NearByGuruPojo;
import com.squareup.picasso.Picasso;

public class NearByGuruHolder extends BaseViewHolder {

    ImageView imageView;
    TextView GrpName,MemCount,Join;
    CardView cardView;
    View.OnClickListener listener;
    Context context;



    public NearByGuruHolder(Context context, View itemView, View.OnClickListener listener) {
        super(itemView);

        this.listener = listener;
        this.context = context;

        imageView = (ImageView)itemView.findViewById(R.id.img);
        GrpName = (TextView)itemView.findViewById(R.id.grp_name);
        MemCount = (TextView)itemView.findViewById(R.id.member_count);
        cardView = (CardView) itemView.findViewById(R.id.group_cv);
        Join = (TextView)itemView.findViewById(R.id.tv_join);

    }

    public void setData (final Context context, final NearByGuruPojo nearByGuruPojo){

        if(nearByGuruPojo != null){
            GrpName.setText(nearByGuruPojo.getGroupName());
            MemCount.setText(" "+nearByGuruPojo.getMemberCount());
            if(nearByGuruPojo.getJoinStatus()==0) { Join.setText("JOIN");}
            else{ Join.setText("JOINED"); }

            if (TextUtils.isEmpty(nearByGuruPojo.getGroupImageUrl())) {
                Picasso.with(context)
                        .load(R.drawable.god)
                        .into(imageView);
            } else {
                Picasso.with(context)
                        .load(nearByGuruPojo.getGroupImageUrl())
                        .placeholder(R.drawable.god)
                        .into(imageView);
            }

            cardView.setOnClickListener(listener);
            cardView.setTag(nearByGuruPojo);

        }

    }
}
