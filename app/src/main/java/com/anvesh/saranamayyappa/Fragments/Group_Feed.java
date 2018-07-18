package com.anvesh.saranamayyappa.Fragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.Description;
import com.anvesh.saranamayyappa.activity.Group_Inside;
import com.anvesh.saranamayyappa.adapters.Dumy_Adapter;
import com.anvesh.saranamayyappa.adapters.FeedAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.FeedPojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anvesh on 6/6/2018.
 */
public class Group_Feed extends Fragment implements View.OnClickListener, UpdateVolleyData, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView R1;
    ArrayList<FeedPojo> feedArray;
    FeedAdapter feedsAdapter;
    TextView wp;
    CardView postBar;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        callFeeds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feeds, container, false);

        feedArray = new ArrayList<>();
        findViews();
        listiners();
        adapters();

        R1.setAdapter(feedsAdapter);
        return view;
    }
    private void adapters() {

    }

    private void listiners() {
        wp.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void findViews() {
        R1=view.findViewById(R.id.rl_post);
        wp = view.findViewById(R.id.write_post);
        postBar=view.findViewById(R.id.post_bar);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);
        R1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if(((Group_Inside) getActivity()).isJoin()==0){postBar.setVisibility(View.GONE);}
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_post:
                Intent intent2 = new Intent(getContext(), Description.class);
                intent2.putExtra("classType","gfeed");
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onRefresh() {
        callFeeds();
    }


    private void callFeeds() {

        String serviceUrl = AyyappaConstants.AYYAPPA_GURUSWAMY_FEED + "userId=" + AyyappaPref.getUserId() + "&groupId=" + AyyappaPref.getGroupId() ;
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequest(null, serviceUrl, getContext(), Group_Feed.this);
    }

    @Override
    public void updateFromVolley(Object result) {
        feedArray.clear();
        if (result instanceof JSONObject) {
            System.out.println("updateFromVolley ");

            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONArray data = ((JSONObject) result).getJSONArray("data");
                        System.out.println("Groups Result " + data.toString());
                        JSONArray wall = data.getJSONObject(0).getJSONArray("feeds");
                        //Toast.makeText(getContext(), ""+wall.toString(), Toast.LENGTH_SHORT).show();

                        for(int i=0;i<wall.length();i++) {
                            FeedPojo feedPojo =new FeedPojo();
                            feedPojo.setDescription(wall.getJSONObject(i).getString("description"));
                            feedPojo.setImageUrl(wall.getJSONObject(i).getString("imageUrl"));
                            feedPojo.setCreatedDate(wall.getJSONObject(i).getString("createdDate"));
                            JSONObject user = wall.getJSONObject(i).getJSONObject("userId");
                            feedPojo.setProfileImage(user.getString("profileImage"));
                            feedPojo.setFullName(user.getString("fullName"));
                            feedArray.add(feedPojo);
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            notifyAdapter();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    void notifyAdapter() {
        if (feedsAdapter == null) {
            feedsAdapter = new FeedAdapter(getContext());
            feedsAdapter.addAll(feedArray);
            R1.setAdapter(feedsAdapter);
        } else {
            feedsAdapter.clear();
            feedsAdapter.addAll(feedArray);
            feedsAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }
}
