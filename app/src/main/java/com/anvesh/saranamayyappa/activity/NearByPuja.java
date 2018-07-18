package com.anvesh.saranamayyappa.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Dumy_Adapter;
import com.anvesh.saranamayyappa.adapters.NearbyPujasAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.NearByPujaPojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.anvesh.saranamayyappa.utils.util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NearByPuja extends AppCompatActivity implements View.OnClickListener, UpdateVolleyData,SwipeRefreshLayout.OnRefreshListener {

    String s1[];
    TypedArray imgs;
    RecyclerView R1;
    Dumy_Adapter dumy_adapter1;
    FloatingActionButton floatingActionButton;
    ImageView back;
    TextView toolbar_text;
    ArrayList<NearByPujaPojo> nearbypujaPojoArray;
    NearbyPujasAdapter pujasAdapter;
    EditText search;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        nearbypujaPojoArray = new ArrayList<>();

        findViews();
        listiners();
        adapters();
        editTextListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        callPujas();
    }

    private void editTextListener() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("SearchValue"+search.getText().toString());
                System.out.println("Editable"+s);
                searchPujas(search.getText().toString());
            }
        });
    }

    private void adapters() {

    }


    private void listiners() {
        floatingActionButton.setOnClickListener(this);
        back.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void findViews() {
        R1=findViewById(R.id.rv1);
        floatingActionButton=findViewById(R.id.btn_Float);
        search = findViewById(R.id.search);
        linearLayout = findViewById(R.id.linearsearch);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        s1=getResources().getStringArray(R.array.store);
        imgs = getResources().obtainTypedArray(R.array.im);
        back=findViewById(R.id.back);
        toolbar_text=findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Near By Pujas");
        R1.setLayoutManager(new LinearLayoutManager(NearByPuja.this, LinearLayoutManager.VERTICAL, false));
    }

    private void callPujas() {
        check = 1;
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_PUJAS + "userId=" + AyyappaPref.getUserId() + "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude();
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }

    public void searchPujas(String serachValue) {
        check=2;
        //String searchs = search.getText().toString();
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_PUJAS + serachValue;
        Log.e("Search URL",serviceUrl);
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.event_cv:
                Intent intent1 = new Intent(NearByPuja.this,EventDetails.class);
                NearByPujaPojo nearbyPujaPojo1 = (NearByPujaPojo) view.getTag();
                //CreateEventPojo createEventPojo = (CreateEventPojo)view.getTag();
                try {
                    intent1.putExtra("eventId", nearbyPujaPojo1.getEventId());
                    intent1.putExtra("imagePath", nearbyPujaPojo1.getEventImageUrl());
                    intent1.putExtra("name", nearbyPujaPojo1.getEventTitle());
                    intent1.putExtra("createdUserName", nearbyPujaPojo1.getCreatedUserName());
                    intent1.putExtra("view_address", nearbyPujaPojo1.getEventLocation());
                    intent1.putExtra("joinStatus", nearbyPujaPojo1.getJoinStatus());
                    intent1.putExtra("createdProfileImage", nearbyPujaPojo1.getCreatedUserProfileImage());
                    intent1.putExtra("yesCount", nearbyPujaPojo1.getYesCount());
                    intent1.putExtra("maybeCount", nearbyPujaPojo1.getMaybeCount());
                    intent1.putExtra("noCount", nearbyPujaPojo1.getNoCount());
                    intent1.putExtra("noResponseCount", nearbyPujaPojo1.getNoResponseCount());
                    intent1.putExtra("startTime", util.displayOnlyTime(util.convertFromUTCWithTime(nearbyPujaPojo1.getEventStartDateTime())));
                    intent1.putExtra("endTime", util.displayOnlyTime(util.convertFromUTCWithTime(nearbyPujaPojo1.getEventEndDateTime())));
                    intent1.putExtra("startdate", util.displayOnlyDate(util.convertFromUTCWithTime(nearbyPujaPojo1.getEventStartDateTime())));
                    intent1.putExtra("enddate", util.displayOnlyDate(util.convertFromUTCWithTime(nearbyPujaPojo1.getEventEndDateTime())));
                    intent1.putExtra("rsvptime", util.displayOnlyTime(util.convertFromUTCWithTime(nearbyPujaPojo1.getRsvpClosingDateTime())));
                    intent1.putExtra("rsvpdate", util.displayOnlyDate(util.convertFromUTCWithTime(nearbyPujaPojo1.getRsvpClosingDateTime())));
                    //Toast.makeText(getContext(), ""+nearbyPujaPojo1.getJoinStatus(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //intent.putExtra("invited",createEventPojo.getInvitedBy());
                startActivity(intent1);
                break;
            case R.id.btn_Float:
                Intent intent = new Intent(this,CreateEvent.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onRefresh() {
        callPujas();
    }

    @Override
    public void updateFromVolley(Object result) {
        System.out.println("updateFromVolley Event UPDATED VERIFY " + result + " " + (result instanceof JSONObject));
        if (check == 1) {
            if (result instanceof JSONObject) {
                System.out.println("updateFromVolley ");
                if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                    try {
                        if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                            JSONArray data = ((JSONObject) result).getJSONArray("data");
                            System.out.println("Groups Result " + data.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<NearByPujaPojo>>() {
                            }.getType();
                            nearbypujaPojoArray = gson.fromJson(data.toString(), listType);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                notifyAdapter();
            }
        }else if (check == 2) {
            if (result instanceof JSONObject) {
                System.out.println("Search ");
                if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                    try {
                        if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 0) {
                            JSONArray data = ((JSONObject) result).getJSONArray("data");
                            System.out.println("Search" + data.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<NearByPujaPojo>>() {
                            }.getType();
                            nearbypujaPojoArray = gson.fromJson(data.toString(), listType);
                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                notifyAdapter();
            }
        }
        swipeRefreshLayout.setRefreshing(false);

    }

    void notifyAdapter() {
        if (pujasAdapter == null) {
            pujasAdapter = new NearbyPujasAdapter(this, this,R.layout.card_event_v);
            pujasAdapter.addAll(nearbypujaPojoArray);
            R1.setAdapter(pujasAdapter);
        } else {
            pujasAdapter.clear();
            pujasAdapter.addAll(nearbypujaPojoArray);
            pujasAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }
}
