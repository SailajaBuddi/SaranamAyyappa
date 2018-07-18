package com.anvesh.saranamayyappa.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.NearByStoreAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.NearByStorePojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Comm_list_type extends AppCompatActivity implements View.OnClickListener, UpdateVolleyData, SwipeRefreshLayout.OnRefreshListener {

    String s1[];
    TypedArray imgs;
    RecyclerView R1;
    int size=6;
    ImageView back;
    FloatingActionButton create;
    String category;
    ArrayList<NearByStorePojo> storePojoArray;
    NearByStoreAdapter nearByStoreAdapter;
    EditText search;
    SwipeRefreshLayout swipeRefreshLayout;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        storePojoArray = new ArrayList<>();

        category= AyyappaPref.getCommCategory();

        findViews();
        listiners();
        adapters();
        editTextListener();
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void adapters() {

        /*dumy_adapter1= new Dumy_Adapter(Comm_list_type.this,R.layout.comm_stor_card,s1,imgs, this,4) ;
        R1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        R1.setAdapter((RecyclerView.Adapter) dumy_adapter1);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        callStors();
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
                searchStore(search.getText().toString());
            }
        });
    }


    @Override
    public void onRefresh() {
        callStors();
    }



    private void listiners() {
        back.setOnClickListener(this);
        create.setOnClickListener(this);
    }

    private void findViews() {
        R1=findViewById(R.id.rv1);
        back=(ImageView)findViewById(R.id.back);
        create=findViewById(R.id.btn_Float);
        search = findViewById(R.id.search);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        R1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        s1=getResources().getStringArray(R.array.store);
        imgs = getResources().obtainTypedArray(R.array.im);

    }

    private void callStors() {
        check = 1;
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_STORES + "userId=" + AyyappaPref.getUserId() +  "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude() + "&category=" + AyyappaPref.getCommCategory() ;
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }

    public void searchStore(String serachValue) {
        check=2;
        //String searchs = search.getText().toString();
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_STORES + serachValue;
        Log.e("Search URL",serviceUrl);
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.comm_cv:
                Intent intent2 = new Intent(this,Commercial_inner.class);
                NearByStorePojo store = (NearByStorePojo) view.getTag();
                intent2.putExtra("owner",store.getOwner());
                AyyappaPref.saveStoreId(store.getStoreId());
                startActivity(intent2);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_Float:
                Intent intent = new Intent(this,CreateComm.class);
                startActivity(intent);
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
                            Type listType = new TypeToken<List<NearByStorePojo>>() {
                            }.getType();
                            storePojoArray = gson.fromJson(data.toString(), listType);
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
                            Type listType = new TypeToken<List<NearByStorePojo>>() {
                            }.getType();
                            storePojoArray = gson.fromJson(data.toString(), listType);
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
        if (nearByStoreAdapter == null) {
            nearByStoreAdapter = new NearByStoreAdapter(this, this);
            nearByStoreAdapter.addAll(storePojoArray);
            R1.setAdapter(nearByStoreAdapter);
        } else {
            nearByStoreAdapter.clear();
            nearByStoreAdapter.addAll(storePojoArray);
            nearByStoreAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }

}
