package com.anvesh.saranamayyappa.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.anvesh.saranamayyappa.adapters.NearByGuruAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.NearByGuruPojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Groups extends AppCompatActivity implements View.OnClickListener, UpdateVolleyData,SwipeRefreshLayout.OnRefreshListener{

    String s1[];
    TypedArray imgs;
    ImageView back;
    TextView toolbar_text;
    RecyclerView R1;
    Dumy_Adapter dumy_adapter1;
    ArrayList<NearByGuruPojo> groupsPojoArrayList;
    NearByGuruAdapter groupsAdapter;
    EditText search;
    FloatingActionButton floatingActionButton;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;

    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        groupsPojoArrayList = new ArrayList<>();
        findViews();
        listiners();
        adapters();
        editTextListener();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callGroups();
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
                searchGroup(search.getText().toString());
            }
        });
    }

    @Override
    public void onRefresh() {

        callGroups();

    }

    private void adapters() {

      /*  dumy_adapter1= new Dumy_Adapter(Groups.this,R.layout.card_group1,s1,imgs, this,2,20) ;
        R1.setLayoutManager(new GridLayoutManager(this, 2));
        R1.setAdapter((RecyclerView.Adapter) dumy_adapter1);*/

    }


    private void listiners() {
        floatingActionButton.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void findViews() {
        R1=findViewById(R.id.rv1);
        floatingActionButton=findViewById(R.id.btn_Float);
        search = findViewById(R.id.search);
        linearLayout = findViewById(R.id.linearsearch);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        s1=getResources().getStringArray(R.array.store);
        imgs = getResources().obtainTypedArray(R.array.im);
        back=(ImageView)findViewById(R.id.back);
        toolbar_text=findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Groups");
        R1.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void callGroups() {
        check = 1;
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_GURUSWAMYGROUPS + "userId=" + AyyappaPref.getUserId() +  "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude();
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }

    public void searchGroup(String serachValue) {
        check=2;
        //String searchs = search.getText().toString();
        String serviceUrl = AyyappaConstants.AYYAPPA_GROUPSEARCH + serachValue;
        Log.e("Search URL",serviceUrl);
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.group_cv:
                Intent intent2 = new Intent(Groups.this,Group_Inside.class);
                NearByGuruPojo nearbyGuruPojo1 = (NearByGuruPojo) view.getTag();
                intent2.putExtra("name", nearbyGuruPojo1.getGroupName());
                intent2.putExtra("owner",nearbyGuruPojo1.getOwner());
                intent2.putExtra("joinStatus", nearbyGuruPojo1.getJoinStatus());
                intent2.putExtra("imagePath", nearbyGuruPojo1.getGroupImageUrl());
                AyyappaPref.saveGroupId(nearbyGuruPojo1.getGroupId());
                startActivity(intent2);
                break;
            case R.id.btn_Float:
                Intent intent1 = new Intent(this,CreateNewGroup.class);
                startActivity(intent1);
                finish();
                break;

        }
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
                            Type listType = new TypeToken<List<NearByGuruPojo>>() {
                            }.getType();
                            groupsPojoArrayList = gson.fromJson(data.toString(), listType);
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
                            Type listType = new TypeToken<List<NearByGuruPojo>>() {
                            }.getType();
                            groupsPojoArrayList = gson.fromJson(data.toString(), listType);
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
        if (groupsAdapter == null) {
            groupsAdapter = new NearByGuruAdapter(this, this);
            groupsAdapter.addAll(groupsPojoArrayList);
            R1.setAdapter(groupsAdapter);
        } else {
            groupsAdapter.clear();
            groupsAdapter.addAll(groupsPojoArrayList);
            groupsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }
}
