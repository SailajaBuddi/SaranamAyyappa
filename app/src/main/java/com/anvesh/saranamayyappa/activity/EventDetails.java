package com.anvesh.saranamayyappa.activity;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventDetails extends AppCompatActivity implements View.OnClickListener,UpdateVolleyData {

    private RelativeLayout mRelativeLayout;
    private Button Response_btn;
    ImageView back,pujaImg,userImg;
    TextView toolbar_text,pujaName,userName,location,rsvp_date,rsvp_time,toDate,fromDate,toTime,fromTime,acc_ct,reg_ct,notsure_ct,notres_ct;
    LinearLayout res_layout,rsvp_layout;

    private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        findViews();
        listiners();

        getIntentValue();

    }

    private void findViews() {
        back=findViewById(R.id.back);
        toolbar_text=findViewById(R.id.toolbar1_text);
        res_layout=findViewById(R.id.response_layout);
        toolbar_text.setText("Ayyappa Puja");
        mRelativeLayout = (RelativeLayout)findViewById(R.id.lay);
        Response_btn = (Button)findViewById(R.id.btn_response);

        pujaName=findViewById(R.id.tv_PujaName);
        userName=findViewById(R.id.adminName);
        pujaImg=findViewById(R.id.iv_puja);
        userImg=findViewById(R.id.iv_adminpic);
        location=findViewById(R.id.tv_location);
        fromDate=findViewById(R.id.tv_startdate);
        toDate=findViewById(R.id.tv_endDate);
        toTime=findViewById(R.id.tv_EndTime);
        fromTime=findViewById(R.id.tv_StartTime);
        rsvp_date=findViewById(R.id.tv_rsvpDate);
        rsvp_time=findViewById(R.id.tv_rsvpTime);
        rsvp_layout=findViewById(R.id.ll_rsvp);
        acc_ct=findViewById(R.id.accepted_count);
        reg_ct=findViewById(R.id.rejected_count);
        notsure_ct=findViewById(R.id.notsure_count);
        notres_ct=findViewById(R.id.notresponded_count);

    }

    private void getIntentValue() {

        userName.setText(getIntent().getStringExtra("createdUserName"));
        pujaName.setText(getIntent().getStringExtra("name"));
        location.setText(getIntent().getStringExtra("view_address"));
        fromTime.setText(getIntent().getStringExtra("startTime"));
        fromDate.setText(getIntent().getStringExtra("startdate"));
        toDate.setText(getIntent().getStringExtra("enddate"));
        toTime.setText(getIntent().getStringExtra("endTime"));
        String rsvpdt = getIntent().getStringExtra("rsvpdate");
        int joinStatus = ( getIntent().getIntExtra("joinStatus", 0));
        String rsvptm = getIntent().getStringExtra("rsvptime");

        //Toast.makeText(this, ""+joinStatus, Toast.LENGTH_SHORT).show();

        if(joinStatus==1){
             res_layout.setVisibility(View.VISIBLE);
             Response_btn.setVisibility(View.GONE);


             acc_ct.setText(""+getIntent().getIntExtra("yesCount",0));
             reg_ct.setText(""+getIntent().getIntExtra("noCount",0));
             notsure_ct.setText(""+getIntent().getIntExtra("maybeCount",0));
             notres_ct.setText(""+getIntent().getIntExtra("noResponseCount",0));
            }
        rsvp_date.setText(rsvpdt);
        rsvp_time.setText(rsvptm);

       /* String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        int response= rsvptm.compareTo(timeStamp);
        int response1= rsvpdt.compareTo(timeStamp);
        if (response>0||response1>0){
            Toast.makeText(this,"Response date and time has been expiried",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Response date has not  been expiried",Toast.LENGTH_LONG).show();
        }*/

        String background = getIntent().getStringExtra("imagePath");

        if( (rsvpdt!=null) && (rsvptm!=null) ){ rsvp_layout.setVisibility(View.VISIBLE);}

        if (TextUtils.isEmpty(background)) {
            Picasso.with(this)
                    .load(R.drawable.img)
                    .into(pujaImg);
        } else {
            Picasso.with(this)
                    .load(background)
                    .placeholder(R.drawable.img)
                    .into(pujaImg);
        }
        String imageUrl = getIntent().getStringExtra("createdProfileImage");
        if (TextUtils.isEmpty(imageUrl)) {
            Picasso.with(this)
                    .load(R.drawable.img)
                    .into(userImg);
        } else {
            Picasso.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.img)
                    .into(userImg);
        }
    }

    private void listiners() {

        back.setOnClickListener(this);

        Response_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.popup_layout,null);
                mPopupWindow = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                // TextView closeButton = (TextView) customView.findViewById(R.id.ib_close);

                LinearLayout yes = customView.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EventDetails.this, "welcome to the event", Toast.LENGTH_SHORT).show();
                        mPopupWindow.dismiss();
                        /*res_layout.setVisibility(view.VISIBLE);
                        Response_btn.setVisibility(view.GONE);*/
                        userResponce("3");
                    }
                });

                LinearLayout no =  customView.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EventDetails.this, "Thanq For Response", Toast.LENGTH_SHORT).show();
                        mPopupWindow.dismiss();
                        /*res_layout.setVisibility(view.VISIBLE);
                        Response_btn.setVisibility(view.GONE);*/
                        userResponce("1");
                    }
                });
                LinearLayout may = customView.findViewById(R.id.maybe);
                may.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EventDetails.this, "Plese Come & Participate", Toast.LENGTH_SHORT).show();
                        mPopupWindow.dismiss();
                       /* res_layout.setVisibility(view.VISIBLE);
                        Response_btn.setVisibility(view.GONE);*/
                        userResponce("2");
                    }
                });

                /*closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPopupWindow.dismiss();
                    }
                });*/
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.BOTTOM,0,0);
            }
        });

    }

    private void userResponce(String s) {
        String serviceUrl = AyyappaConstants.AYYAPPA_JOIN_UNJOIN_PUJA + "userId=" + AyyappaPref.getUserId() + "&eventId=" + getIntent().getStringExtra("eventId") + "&joinStatus=" + s ;
        VolleySingleton.getInstance(EventDetails.this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void updateFromVolley(Object result) {
        if (result instanceof JSONObject) {
            System.out.println("updateFromVolley ");
            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONObject data = ((JSONObject) result).getJSONObject("data");
                        System.out.println("Groups Result " + data.toString());

                        res_layout.setVisibility(View.VISIBLE);
                        Response_btn.setVisibility(View.GONE);

                        acc_ct.setText(data.getString("yesCount"));
                        reg_ct.setText(data.getString("noCount"));
                        notsure_ct.setText(data.getString("maybeCount"));
                        notres_ct.setText(data.getString("noResponseCount"));


                    } else {

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
