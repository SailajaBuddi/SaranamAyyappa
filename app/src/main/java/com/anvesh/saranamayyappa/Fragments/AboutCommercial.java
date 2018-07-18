package com.anvesh.saranamayyappa.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class AboutCommercial extends Fragment implements UpdateVolleyData{
    TextView Name,OwnerName,Date_Time,Loc,Phone,web,fb,desc;
    // LinearLayout ll_time,ll_category,ll_loc,ll_num,ll_mail,ll_fb,ll_web;
    ImageView back,pujaImg;
    CircleImageView userImg;

    String time,date;

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_commercial, container, false);


        AboutDetails();

        findViews();
        return view;
    }

    private void findViews() {

        Name = (TextView)view.findViewById(R.id.store_name);
        OwnerName = (TextView)view.findViewById(R.id.tv_ownerName);
        Date_Time = (TextView)view.findViewById(R.id.tv_time);
        Loc = (TextView)view.findViewById(R.id.tv_location);
        Phone = (TextView)view.findViewById(R.id.tv_num);
        web = (TextView)view.findViewById(R.id.web);
        fb = (TextView)view.findViewById(R.id.fb);
        pujaImg = (ImageView)view.findViewById(R.id.iv_puja);
        //userImg = (CircleImageView) view.findViewById(R.id.iv_adminpic);
        back=  (ImageView) view.findViewById(R.id.back);
        desc = (TextView)view.findViewById(R.id.desc);

       /* ll_time = (LinearLayout)view.findViewById(R.id.ll_time);
        ll_category = (LinearLayout)view.findViewById(R.id.ll_category);
        ll_loc = (LinearLayout)view.findViewById(R.id.ll_loc);
        ll_num = (LinearLayout)view.findViewById(R.id.ll_num);
        ll_mail = (LinearLayout)view.findViewById(R.id.ll_mail);
        ll_web = (LinearLayout)view.findViewById(R.id.ll_web);
        ll_fb = (LinearLayout)view.findViewById(R.id.ll_fb);*/
    }

    private void AboutDetails() {
        // Toast.makeText(getContext(), "Success" +AyyappaPref.getGroupId(), Toast.LENGTH_SHORT).show();
        String serviceUrl = AyyappaConstants.AYYAPPA_ABOUT_STORE + "userId=" + AyyappaPref.getUserId() + "&storeId=" + AyyappaPref.getStoreId();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequest(null, serviceUrl, getContext(), AboutCommercial.this);

    }


    @Override
    public void updateFromVolley(Object result) {
        if (result instanceof JSONObject) {
            System.out.println("updateFromVolley ");
            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                // Toast.makeText(getContext(), "Success" +result.toString(), Toast.LENGTH_SHORT).show();
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONArray data = ((JSONObject) result).getJSONArray("data");
                        System.out.println("About Result " + data.toString());
                        JSONObject data1 = ((JSONArray) data).getJSONObject(0);
                        Name.setText(data1.getString("storeName"));
                        Loc.setText(data1.getString("storeLocation"));
                        Date_Time.setText(data1.getString("createdDateTime"));
                        //OwnerName.setText(data1.getString("createdUserName"));
                        Phone.setText(data1.getString("phoneNumber"));

                        Toast.makeText(getContext(), "Success"+data1.getString("website"), Toast.LENGTH_SHORT).show();
                       // web.setText(data1.getString("website"));
                        fb.setText(data1.getString("facebookLink"));

                       /* date = util.displayOnlyDate(util.convertFromUTCWithTime(data1.getString("createdDateTime")));
                        time  = util.displayOnlyTime(util.convertFromUTCWithTime(data1.getString("createdDateTime")));

                        Date_Time.setText(data1.getString(date +" " +time));
                        */

                        if (TextUtils.isEmpty(data1.getString("storeImageUrl"))) {
                            Picasso.with(getContext())
                                    .load(R.drawable.img)
                                    .into(pujaImg);
                        } else {
                            Picasso.with(getContext())
                                    .load(data1.getString("storeImageUrl"))
                                    .placeholder(R.drawable.img)
                                    .into(pujaImg);
                        }
                        /*if (TextUtils.isEmpty(data1.getString("createdUserImageURL"))) {
                            Picasso.with(getContext())
                                    .load(R.drawable.img)
                                    .into(userImg);
                        } else {
                            Picasso.with(getContext())
                                    .load(data1.getString("createdUserImageURL"))
                                    .placeholder(R.drawable.img)
                                    .into(userImg);
                        }*/

                        // Toast.makeText(getContext(), "Success" +data.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("in status code else ::" + result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }
}
