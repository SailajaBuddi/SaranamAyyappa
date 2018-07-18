package com.anvesh.saranamayyappa.Fragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.PlayActivity;
import com.anvesh.saranamayyappa.activity.StoryDetails;
import com.anvesh.saranamayyappa.adapters.Dumy_Adapter;
import com.anvesh.saranamayyappa.adapters.MaterialsAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.AyyappaSongsPojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Material extends Fragment implements View.OnClickListener, UpdateVolleyData,SwipeRefreshLayout.OnRefreshListener {
    RecyclerView R1, R2, R3,R4,R5;
    MaterialsAdapter ayyappaSongsAdapter,slokasAdapter,bhajanaSongsAdapter, videosAdapter;
    ArrayList<AyyappaSongsPojo> ayyappaSongsList, AyyappaVideosList,slokasList,bhajanaSongsList;
    SwipeRefreshLayout swipeRefreshLayout;
    String[] s1;
    TypedArray imgs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materials, container, false);
        ayyappaSongsList = new ArrayList<>();
        AyyappaVideosList = new ArrayList<>();
        slokasList = new ArrayList<>();
        bhajanaSongsList = new ArrayList<>();

        findViews(view);
        listeners();
        adapters();
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    private void adapters() {
        Dumy_Adapter dumy_adapter= new Dumy_Adapter(getContext(),R.layout.card_story,s1,imgs, this,3) ;
        R1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R1.setAdapter((RecyclerView.Adapter) dumy_adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        callMaterials();
    }

    private void listeners() {

    }

    public void findViews(View view) {
        R1 = view.findViewById(R.id.rv1);
        R2 = view.findViewById(R.id.rv2);
        R3 = view.findViewById(R.id.rv3);
        R4 = view.findViewById(R.id.rv4);
        R5 = view.findViewById(R.id.rv5);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        R1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        R2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        R3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        R4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        R5.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        s1=getResources().getStringArray(R.array.comm);
        imgs = getResources().obtainTypedArray(R.array.im);

    }

    public void notifyAdapter() {
        if (ayyappaSongsAdapter == null) {
            ayyappaSongsAdapter = new MaterialsAdapter(getContext(), this,R.layout.card_song);
            ayyappaSongsAdapter.addAll(ayyappaSongsList);
            R2.setAdapter(ayyappaSongsAdapter);
        } else {
            ayyappaSongsAdapter.clear();
            ayyappaSongsAdapter.addAll(ayyappaSongsList);
            ayyappaSongsAdapter.notifyDataSetChanged();
        }

    }

    public void notifySlokasAdapter() {
        if (slokasAdapter == null) {
            slokasAdapter = new MaterialsAdapter(getContext(), this,R.layout.card_song);
            slokasAdapter.addAll(slokasList);
            R3.setAdapter(slokasAdapter);
        } else {
            slokasAdapter.clear();
            slokasAdapter.addAll(slokasList);
            slokasAdapter.notifyDataSetChanged();
        }

    }

    public void notifyBajanaSongsAdapter() {
        if (bhajanaSongsAdapter == null) {
            bhajanaSongsAdapter = new MaterialsAdapter(getContext(), this,R.layout.card_song);
            bhajanaSongsAdapter.addAll(bhajanaSongsList);
            R4.setAdapter(bhajanaSongsAdapter);
        } else {
            bhajanaSongsAdapter.clear();
            bhajanaSongsAdapter.addAll(bhajanaSongsList);
            bhajanaSongsAdapter.notifyDataSetChanged();
        }
    }

    public void notifyVideosAdapter() {
        if (videosAdapter == null) {
            videosAdapter = new MaterialsAdapter(getContext(), this,R.layout.card_video);
            videosAdapter.addAll(AyyappaVideosList);
            R5.setAdapter(videosAdapter);
        } else {
            videosAdapter.clear();
            videosAdapter.addAll(AyyappaVideosList);
            videosAdapter.notifyDataSetChanged();
        }
    }


    private void redirectToPlayActivity(String imageUrl) {
        if (imageUrl != null) {
            Intent playIntent = new Intent(getActivity(), PlayActivity.class);
            playIntent.putExtra("VIDEO_URL", imageUrl);
            getActivity().startActivity(playIntent);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_videoPlay:
                AyyappaSongsPojo ayyappaSongsPojo = (AyyappaSongsPojo) v.getTag();
                redirectToPlayActivity(ayyappaSongsPojo.getVideoUrl());
                break;
            case R.id.iv_songPlay:
                AyyappaSongsPojo ayyappaSongsPojo1 = (AyyappaSongsPojo) v.getTag();
                redirectToPlayActivity(ayyappaSongsPojo1.getSongUrl());
                break;
            case R.id.story_cv:
                Intent intent4 = new Intent(getContext(),StoryDetails.class);
                startActivity(intent4);
                break;
        }
    }

    public void callMaterials() {
        swipeRefreshLayout.setRefreshing(true);
        String serviceUrl = AyyappaConstants.AYYAPPA_MATERIALS + "userId=" + AyyappaPref.getUserId();
        Log.e("Search URL", serviceUrl);
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequest(null, serviceUrl, getContext(), Material.this);
    }

    @Override
    public void updateFromVolley(Object result) {
        System.out.println("updateFromVolley Event UPDATED VERIFY " + result + " " + (result instanceof JSONObject));
        if (result instanceof JSONObject) {
            System.out.println("updateFromVolley ");
            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONArray data = ((JSONObject) result).getJSONArray("data");
                        System.out.println("Material Result " + data.toString());
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<AyyappaSongsPojo>>() {}.getType();

                        JSONArray songs = data.getJSONObject(0).getJSONArray("songs");
                        ayyappaSongsList = gson.fromJson(songs.toString(), listType);

                        JSONArray slokassongs = data.getJSONObject(0).getJSONArray("slokas");
                        slokasList = gson.fromJson(slokassongs.toString(), listType);

                        JSONArray bhajanaSongs = data.getJSONObject(0).getJSONArray("bhajanaSongs");
                        bhajanaSongsList = gson.fromJson(bhajanaSongs.toString(), listType);

                        JSONArray ayyappaDevotionalVideos = data.getJSONObject(0).getJSONArray("videos");
                        AyyappaVideosList = gson.fromJson(ayyappaDevotionalVideos.toString(), listType);


                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            notifyAdapter();
            notifySlokasAdapter();
            notifyBajanaSongsAdapter();
            notifyVideosAdapter();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }

    @Override
    public void onRefresh() {
        callMaterials();
    }
}
