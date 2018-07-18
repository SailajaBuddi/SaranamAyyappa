package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.NearByPujaPojo;
import com.anvesh.saranamayyappa.utils.util;
import com.squareup.picasso.Picasso;
import static com.anvesh.saranamayyappa.R.drawable.img;

public class NearbyPujasHolder extends BaseViewHolder {
    public ImageView ivMain,ivJoin;
    private TextView tvPujaName, tvAddress, tvPujaTime, tvDate,tvToDate, tvViewDetails, tvAddCal, tvPujaEndTime, tvJoin, tvUnJoin;
    CardView event_cv;

    View.OnClickListener listener;
    Context context;


    public NearbyPujasHolder(Context context, View itemView, View.OnClickListener listener) {
        super(itemView);

        this.listener = listener;
        this.context = context;
        tvPujaName = itemView.findViewById(R.id.tv_PujaName);
        tvAddress = itemView.findViewById(R.id.tv_Addres);
        tvPujaTime = itemView.findViewById(R.id.tv_PujaStartTime);
        tvDate = itemView.findViewById(R.id.tv_Date);
        tvToDate = itemView.findViewById(R.id.tv_toDate);
        tvPujaEndTime = itemView.findViewById(R.id.tv_PujaEndTime);
        ivMain = itemView.findViewById(R.id.event_img);
        event_cv=itemView.findViewById(R.id.event_cv);
        /*ivJoin = itemView.findViewById(R.id.iv_Join);
        joinnow = itemView.findViewById(R.id.joinnow_linear);*/
        /*tvJoin = (TextView) itemView.findViewById(R.id.tv_Join);*/
        //tvUnJoin = (TextView) itemView.findViewById(R.id.tv_UnJoin);
    }

    public void setData(final Context context, final NearByPujaPojo nearbypujaPojo) {
        if (nearbypujaPojo != null) {
            tvPujaName.setText(nearbypujaPojo.getEventTitle());
            tvAddress.setText(nearbypujaPojo.getEventLocation());
            tvDate.setText(util.displayOnlyDate(util.convertFromUTCWithTime(nearbypujaPojo.getEventStartDateTime())));
            tvToDate.setText(util.displayOnlyDate(util.convertFromUTCWithTime(nearbypujaPojo.getEventEndDateTime())));
            tvPujaTime.setText(util.displayOnlyTime(util.convertFromUTCWithTime(nearbypujaPojo.getEventStartDateTime())));
            //tvPujaTime.setText(util.convertFromUTCWithTime1(nearbyPojo.getEventStartTime()));
            tvPujaEndTime.setText(util.displayOnlyTime(util.convertFromUTCWithTime(nearbypujaPojo.getEventEndDateTime())));
            //tvDate.setText(util.convertFromUTCWithTime(nearbyPojo.getEventStartDate()));
            //tvPujaEndTime.setText(util.convertFromUTCWithTime1(nearbyPojo.getEventEndTime()));

            if (TextUtils.isEmpty(nearbypujaPojo.getEventImageUrl())) {
                Picasso.with(context)
                        .load(img)
                        .fit()
                        .into(ivMain);
            } else {
                Picasso.with(context)
                        .load(nearbypujaPojo.getEventImageUrl())
                        .placeholder(img)
                        .fit()
                        .into(ivMain);
            }

            // System.out.println("Join status" + nearbyPojo.getMembersCount());
           /* String temp = "";
            if (nearbypujaPojo.getJoinStatus() == 1) {
                if (nearbypujaPojo.getMemberCount() == 1) {
                    temp = "You Joined";
                } else {
                    temp = "You +" + (nearbypujaPojo.getMemberCount() - 1) + " members joined";
                }
            } else {
                if (nearbypujaPojo.getMemberCount() == 0) {
                    temp = "Join to Puja";
                } else if (nearbypujaPojo.getJoinStatus()==0 ){
                    // temp = +nearbyPojo.getMembersCount() + " members joined";
                    temp = "Join to Puja";
                }
            }
            tvJoin.setText(temp);*/

            event_cv.setOnClickListener(listener);
            event_cv.setTag(nearbypujaPojo);
        }
    }


}
