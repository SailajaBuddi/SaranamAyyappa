package com.anvesh.saranamayyappa.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Memb_Adapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.GroupMembersPojo;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Anvesh on 6/13/2018.
 */

public class GroupMembers extends Fragment implements UpdateVolleyData, View.OnClickListener {
    private RecyclerView posts;
    ArrayList<GroupMembersPojo> groupMembersPojoArray;
    Memb_Adapter memb_adapter;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        callGroupMembers();
    }

    private void callGroupMembers() {
        String serviceUrl = AyyappaConstants.AYYAPPA_GROUP_MEMBERS + "groupId=" + AyyappaPref.getGroupId();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequest(null, serviceUrl, getContext(), GroupMembers.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_members, container, false);
        groupMembersPojoArray = new ArrayList<>();
        findViews();
        //callGroupMembers();

        // posts.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        posts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        posts.setAdapter(memb_adapter);

        return view;
    }

    private void findViews() {
        posts= view.findViewById(R.id.mem);
    }

    void notifyAdapter() {
        if (memb_adapter == null) {
            memb_adapter = new Memb_Adapter(getContext());
            memb_adapter.addAll(groupMembersPojoArray);
            posts.setAdapter(memb_adapter);
        } else {
            memb_adapter.clear();
            memb_adapter.addAll(groupMembersPojoArray);
            memb_adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void updateFromVolley(Object result) {
        groupMembersPojoArray.clear();
        System.out.println("updateFromVolley Event UPDATED VERIFY " + result + " " + (result instanceof JSONObject));
        if (result instanceof JSONObject) {
            System.out.println("updateFromVolley ");
            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONArray data = ((JSONObject) result).getJSONArray("data");
                        JSONObject users=data.getJSONObject(0);
                        JSONArray data1 = users.getJSONArray("users");
                        for(int i=0;i<data1.length();i++) {
                            JSONObject user1=data1.getJSONObject(i);
                            JSONObject userData=user1.getJSONObject("userId");
                            System.out.println("Near By " + userData.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<GroupMembersPojo>() {
                            }.getType();
                            //groupMembersPojoArray = gson.fromJson(userData.toString(), listType);
                            groupMembersPojoArray.add((GroupMembersPojo) gson.fromJson(userData.toString(), listType));
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        notifyAdapter();
    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }

    @Override
    public void onClick(View view) {

    }
}
