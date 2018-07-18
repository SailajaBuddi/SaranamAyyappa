package com.anvesh.saranamayyappa.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.Group_Inside;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class AboutGroup extends Fragment implements UpdateVolleyData {

    TextView Name,OwnerName,Date_Time,Category,Loc,Phone,Mail,Web,Fb,desc;
    LinearLayout ll_desc;
    ImageView back,pujaImg;
    CircleImageView userImg;
    Button btn_exitGroup;
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
        view = inflater.inflate(R.layout.fragment_about_group, container, false);
        findViews();
        listner();
        return view;
    }

    private void listner() {
        btn_exitGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Group_Inside) getActivity()).joinOrUnjoinGroup(0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AboutDetails();
        if(((Group_Inside) getActivity()).isJoin()==1 && !((Group_Inside) getActivity()).isOwner()){btn_exitGroup.setVisibility(View.VISIBLE);}
    }

    private void findViews() {

        Name = (TextView)view.findViewById(R.id.store_name);
        OwnerName = (TextView)view.findViewById(R.id.tv_ownerName);
        Date_Time = (TextView)view.findViewById(R.id.tv_time);
        Category = (TextView)view.findViewById(R.id.tv_cat);
        Loc = (TextView)view.findViewById(R.id.tv_location);
        Phone = (TextView)view.findViewById(R.id.tv_num);
        Mail = (TextView)view.findViewById(R.id.mail);
        Web = (TextView)view.findViewById(R.id.web);
        Fb = (TextView)view.findViewById(R.id.fb);
        pujaImg = (ImageView)view.findViewById(R.id.iv_puja);
        userImg = (CircleImageView) view.findViewById(R.id.iv_adminpic);
        back=  (ImageView) view.findViewById(R.id.back);
        desc = (TextView)view.findViewById(R.id.desc);
        ll_desc = view.findViewById(R.id.ll_desc);
        btn_exitGroup=view.findViewById(R.id.btn_exit);

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
        String serviceUrl = AyyappaConstants.AYYAPPA_ABOUT_GROUP + "userId=" + AyyappaPref.getUserId() + "&groupId=" + AyyappaPref.getGroupId();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequest(null, serviceUrl, getContext(), AboutGroup.this);

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
                        Name.setText(data1.getString("groupName"));
                        String desc_text=data1.getString("description");
                        if(desc_text!=null && desc.getText()!="" && desc.getText()!=" "){ll_desc.setVisibility(View.VISIBLE);}
                        desc.setText(desc_text);
                        Loc.setText(data1.getString("locationName"));
                       // Date_Time.setText(data1.getString("createdDateTime"));
                        OwnerName.setText(data1.getString("createdUserName"));
                        Phone.setText(data1.getString("createdUserMobileNo"));

                       /* date = util.displayOnlyDate(util.convertFromUTCWithTime(data1.getString("createdDateTime")));
                        time  = util.displayOnlyTime(util.convertFromUTCWithTime(data1.getString("createdDateTime")));

                        Date_Time.setText(data1.getString(date +" " +time));
                        */

                        if (TextUtils.isEmpty(data1.getString("groupImageUrl"))) {
                            Picasso.with(getContext())
                                    .load(R.drawable.img)
                                    .into(pujaImg);
                        } else {
                            Picasso.with(getContext())
                                    .load(data1.getString("groupImageUrl"))
                                    .placeholder(R.drawable.img)
                                    .into(pujaImg);
                        }
                        if (TextUtils.isEmpty(data1.getString("createdUserImageURL"))) {
                            Picasso.with(getContext())
                                    .load(R.drawable.img)
                                    .into(userImg);
                        } else {
                            Picasso.with(getContext())
                                    .load(data1.getString("createdUserImageURL"))
                                    .placeholder(R.drawable.img)
                                    .into(userImg);
                        }

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
