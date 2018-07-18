package com.anvesh.saranamayyappa.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.Description;
import com.anvesh.saranamayyappa.activity.Group_Inside;
import com.anvesh.saranamayyappa.adapters.FeedAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.FeedPojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anvesh on 6/6/2018.
 */
public class Group_Wall extends Fragment implements View.OnClickListener, UpdateVolleyData, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView R1;
    ArrayList<FeedPojo> feedArray;
    FeedAdapter feedsAdapter;
    TextView wp;
    CardView postBar;
    View root;
    SwipeRefreshLayout swipeRefreshLayout;

    /*public Group_Wall(String s){}*/


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
        root = inflater.inflate(R.layout.fragment_feeds, container, false);

        feedArray = new ArrayList<>();
        findViews();
        listiners();

        R1.setAdapter(feedsAdapter);
        return root;
    }

    private void listiners() {
        wp.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @SuppressLint("NewApi")
    private void findViews() {
        R1=root.findViewById(R.id.rl_post);
        wp = root.findViewById(R.id.write_post);
        postBar=root.findViewById(R.id.post_bar);
        swipeRefreshLayout=root.findViewById(R.id.swipe_refresh_layout);
        R1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if(!((Group_Inside) getActivity()).isOwner()){postBar.setVisibility(View.GONE);}
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_post:
                Intent intent2 = new Intent(getContext(), Description.class);
                intent2.putExtra("classType","gwall");
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onRefresh() {
        callFeeds();
    }

    private void callFeeds() {
        String serviceUrl = AyyappaConstants.AYYAPPA_GURUSWAMY_WALL + "userId=" + AyyappaPref.getUserId() + "&groupId=" + AyyappaPref.getGroupId() ;
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequest(null, serviceUrl, getContext(), Group_Wall.this);

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
                        JSONArray wall = data.getJSONObject(0).getJSONArray("wall");

                       // Toast.makeText(getContext(), ""+wall.getJSONObject(0).getJSONObject("userId").getString("profileImage"), Toast.LENGTH_SHORT).show();

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
