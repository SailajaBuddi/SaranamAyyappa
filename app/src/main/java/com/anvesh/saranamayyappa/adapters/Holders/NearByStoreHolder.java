package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.NearByStorePojo;
import com.squareup.picasso.Picasso;

public class NearByStoreHolder extends BaseViewHolder{

    ImageView imageView;
    TextView StoreName,StoreLoc;
    CardView cardView;

    View.OnClickListener listener;
    Context context;

    public NearByStoreHolder(Context context, View itemView, View.OnClickListener listener) {
        super(itemView);
        this.listener = listener;
        this.context = context;
        imageView = (ImageView)itemView.findViewById(R.id.img);
        StoreName = (TextView)itemView.findViewById(R.id.text);
        StoreLoc = (TextView)itemView.findViewById(R.id.tv_location);
        cardView = (CardView)itemView.findViewById(R.id.comm_cv);
    }
     public void setData(final Context context, final NearByStorePojo nearByStorePojo){

        if(nearByStorePojo != null){
            StoreName.setText(nearByStorePojo.getStoreName());
            StoreLoc.setText(nearByStorePojo.getStoreLocation());
            if (TextUtils.isEmpty(nearByStorePojo.getStoreImageUrl())) {
                Picasso.with(context)
                        .load(R.drawable.god)
                        .into(imageView);
            } else {
                Picasso.with(context)
                        .load(nearByStorePojo.getStoreImageUrl())
                        .placeholder(R.drawable.god)
                        .into(imageView);
            }
        }
        cardView.setOnClickListener(listener);
        cardView.setTag(nearByStorePojo);


     }


}
