package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.TemplesPojo;
import com.anvesh.saranamayyappa.utils.util;
import com.squareup.picasso.Picasso;

import static com.anvesh.saranamayyappa.R.drawable.img;

public class NearbyTemplesHolder extends BaseViewHolder {
    private ImageView tempImg;
    private TextView tempName, tempAddress;
    CardView temp_cv;
    RatingBar ratingBar;
    View.OnClickListener listener;
    Context context;


    public NearbyTemplesHolder(Context context, View itemView, View.OnClickListener listener) {
        super(itemView);

        this.listener = listener;
        this.context = context;

        tempName = itemView.findViewById(R.id.templeName);
        tempAddress = itemView.findViewById(R.id.tv_add);
        tempImg = itemView.findViewById(R.id.img);
        ratingBar = itemView.findViewById(R.id.rating);
       temp_cv =itemView.findViewById(R.id.temp_cv);

    }

    public void setData(final Context context, final TemplesPojo templesPojo) {
        if (templesPojo != null) {

           tempName.setText(templesPojo.getName());
            tempAddress.setText(templesPojo.getVicinity());
            //ratingBar.setRating(templesPojo.getRating().floatValue());
            /*ratingBar.setRating(templesPojo.getRating());*/

            if (!TextUtils.isEmpty(templesPojo.getIcon())) {
                Picasso.with(context)
                        .load(templesPojo.getIcon())
                        .placeholder(img)
                        .fit()
                        .into(tempImg);
            } else {

            }

            temp_cv.setOnClickListener(listener);
            temp_cv.setTag(templesPojo);
        }
    }


}
