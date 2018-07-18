package com.anvesh.saranamayyappa.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.Comm_list_type;
import com.anvesh.saranamayyappa.activity.Commercials;
import com.anvesh.saranamayyappa.activity.EventDetails;
import com.anvesh.saranamayyappa.activity.Group_Inside;
import com.anvesh.saranamayyappa.activity.Groups;
import com.anvesh.saranamayyappa.activity.NearByPuja;
import com.anvesh.saranamayyappa.adapters.Dumy_Adapter;
import com.anvesh.saranamayyappa.adapters.NearByGuruAdapter;
import com.anvesh.saranamayyappa.adapters.NearbyPujasAdapter;
import com.anvesh.saranamayyappa.adapters.NearbyTemplesAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.NearByGuruPojo;
import com.anvesh.saranamayyappa.model.NearByPujaPojo;
import com.anvesh.saranamayyappa.model.TemplesPojo;
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

import static com.facebook.login.widget.ProfilePictureView.TAG;


public class Nearby extends Fragment implements View.OnClickListener, UpdateVolleyData, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView R1,R2,R3,R4;
    Dumy_Adapter dumy_adapter3,dumy_adapter4;
    TextView ViewAll_np,ViewAll_ngg,ViewAll_nc,ViewAll_nt;
    String s1[],commCat[];;
    TypedArray imgs;
    ArrayList<NearByPujaPojo> nearbyPujaPojoArray;
    ArrayList<NearByGuruPojo> nearbyGuruPojoArray;
    ArrayList<TemplesPojo> templesPojoArray;
    NearbyPujasAdapter nearbyPujasAdapter;
    NearByGuruAdapter nearbyGuruAdapter;
    NearbyTemplesAdapter nearbyTemplesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_nearby, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        nearbyPujaPojoArray = new ArrayList<>();
        nearbyGuruPojoArray = new ArrayList<>();
        templesPojoArray = new ArrayList<>();

        findViews(root);
        listiners();
        adapters();

        return  root;
    }

    @Override
    public void onResume() {
        super.onResume();
        callNearbyEvents();
        callNearbyGuruSwamyGroups();
        callNearbyTemples();
    }

    private void adapters() {

        dumy_adapter3= new Dumy_Adapter(getContext(),R.layout.card_comm_horizontal,s1,imgs,this,4) ;
        R3.setAdapter((RecyclerView.Adapter) dumy_adapter3);

        /*dumy_adapter4= new Dumy_Adapter(getContext(),R.layout.temple_card,this, 2) ;
        R4.setAdapter((RecyclerView.Adapter) dumy_adapter4);*/

    }

    private void listiners() {
        ViewAll_np.setOnClickListener(this);
        ViewAll_ngg.setOnClickListener(this);
        ViewAll_nc.setOnClickListener(this);
        ViewAll_nt.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        callNearbyEvents();
        callNearbyGuruSwamyGroups();
    }

    private void findViews(View root) {
        R1=root.findViewById(R.id.rv1);
        R2=root.findViewById(R.id.rv2);
        R3=root.findViewById(R.id.rv3);
        R4=root.findViewById(R.id.rv4);
        ViewAll_np=root.findViewById(R.id.viewAll_np);
        ViewAll_ngg=root.findViewById(R.id.viewAll_ngg);
        ViewAll_nc=root.findViewById(R.id.viewAll_nc);
        ViewAll_nt=root.findViewById(R.id.viewAll_nt);
        swipeRefreshLayout=root.findViewById(R.id.swipe_refresh_layout);
        commCat=getResources().getStringArray(R.array.commCategory);

        s1=getResources().getStringArray(R.array.comm);
        imgs = getResources().obtainTypedArray(R.array.im);
        R1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.group_cv:
                Intent intent2 = new Intent(getContext(),Group_Inside.class);
                NearByGuruPojo nearbyGuruPojo1 = (NearByGuruPojo) view.getTag();
                intent2.putExtra("name", nearbyGuruPojo1.getGroupName());
                intent2.putExtra("owner",nearbyGuruPojo1.getOwner());
                intent2.putExtra("joinStatus", nearbyGuruPojo1.getJoinStatus());
                intent2.putExtra("imagePath", nearbyGuruPojo1.getGroupImageUrl());
                AyyappaPref.saveGroupId(nearbyGuruPojo1.getGroupId());
                startActivity(intent2);
                break;
            case R.id.event_cv:
                Intent intent1 = new Intent(getContext(),EventDetails.class);
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
            case R.id.viewAll_np:
                intent = new Intent(getContext(),NearByPuja.class);
                startActivity(intent);
                break;
            case R.id.viewAll_nc:
                intent = new Intent(getContext(),Commercials.class);
                startActivity(intent);
                break;
            case R.id.viewAll_ngg:
                intent = new Intent(getContext(),Groups.class);
                startActivity(intent);
                break;
            case R.id.comm_cv:
                intent = new Intent(getContext(),Comm_list_type.class);
                String[] commCat=getResources().getStringArray(R.array.commCategory);
                String item = commCat[R1.getChildLayoutPosition(view)];
                AyyappaPref.saveCommCategory(item);
                startActivity(intent);
                break;
           /*   case R.id.viewAll_ngps:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameContent, new Groups(), "NewFragmentTag");
                ((MainActivity)getActivity()).updateNavigationBarState(1);
                ft.commit();
                break;*/
        }
    }

    private String getUrl(String latitude , String longitude)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&rankby=distance");
        /*googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);*/
        googlePlaceUrl.append("&type=hindu_temple");
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getResources().getString(R.string.app_google_api));

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    private void callNearbyEvents() {
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_PUJAS + "userId=" + AyyappaPref.getUserId() + "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequestAndResultCode(null, serviceUrl, getContext(), Nearby.this,1);
    }

    private void callNearbyTemples() {
        String serviceUrl = getUrl(AyyappaPref.getLatitude(),AyyappaPref.getLongitude());
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequestAndResultCode(null, serviceUrl, getContext(), Nearby.this,3);
    }

    private void callNearbyGuruSwamyGroups() {
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_GURUSWAMYGROUPS + "userId=" + AyyappaPref.getUserId() + "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequestAndResultCode(null, serviceUrl, getContext(), Nearby.this,2);
    }

    @Override
    public void updateFromVolley(Object result) {

    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {
        System.out.println("updateFromVolley Event UPDATED VERIFY " + result + " " + (result instanceof JSONObject));
        if (result instanceof JSONObject) {
            Log.d(TAG, "updateFromVolley: "+result.toString());
            System.out.println("updateFromVolley ");


            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                /*if(resultCode==3)
                Toast.makeText(getContext(), ""+resultCode, Toast.LENGTH_SHORT).show();*/
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        if(resultCode==3)
                            Toast.makeText(getContext(), ""+resultCode, Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        if(resultCode==1) {
                            JSONArray data = ((JSONObject) result).getJSONArray("data");
                            Type listType = new TypeToken<List<NearByPujaPojo>>() {}.getType();
                            nearbyPujaPojoArray = gson.fromJson(data.toString(), listType);
                        } else if(resultCode==2) {
                            JSONArray data = ((JSONObject) result).getJSONArray("data");
                            Type listType = new TypeToken<List<NearByGuruPojo>>() {}.getType();
                            nearbyGuruPojoArray = gson.fromJson(data.toString(), listType);
                        }

                    }
                    else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if(resultCode==3){

                templesPojoArray.clear();
                try {


                JSONArray data1 = ((JSONObject) result).getJSONArray("results");
                // Toast.makeText(getContext(), ""+data1.toString(), Toast.LENGTH_SHORT).show();

                for(int i=0;i<data1.length();i++) {
                    TemplesPojo templesPojo =new TemplesPojo();
                    templesPojo.setLat(data1.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                    templesPojo.setLng(data1.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                    templesPojo.setName(data1.getJSONObject(i).getString("name"));
                    templesPojo.setIcon(data1.getJSONObject(i).getString("icon"));
                    templesPojo.setId(data1.getJSONObject(i).getString("id"));
                    templesPojo.setPlace_id(data1.getJSONObject(i).getString("place_id"));
                    templesPojo.setCompound_code(data1.getJSONObject(i).getJSONObject("plus_code").getString("compound_code"));
                   /* int t = data1.getJSONObject(i).getInt("rating");
                    Toast.makeText(getContext(), "jhfsdyteytyetye", Toast.LENGTH_SHORT).show();*/
                    templesPojo.setVicinity(data1.getJSONObject(i).getString("vicinity"));
                    templesPojoArray.add(templesPojo);
                }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            notifyAdapter(resultCode);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    void notifyAdapter(int resultCode) {
        switch (resultCode) {
            case 1:
                if (nearbyPujasAdapter == null) {
                    nearbyPujasAdapter = new NearbyPujasAdapter(getContext(), this,R.layout.dummy_card
                    );
                    nearbyPujasAdapter.addAll(nearbyPujaPojoArray);
                    R1.setAdapter(nearbyPujasAdapter);
                } else {
                    nearbyPujasAdapter.clear();
                    nearbyPujasAdapter.addAll(nearbyPujaPojoArray);
                    nearbyPujasAdapter.notifyDataSetChanged();
                }break;
            case 2:
                if (nearbyGuruAdapter == null) {
                    nearbyGuruAdapter = new NearByGuruAdapter(getContext(), this);
                    nearbyGuruAdapter.addAll(nearbyGuruPojoArray);
                    R2.setAdapter(nearbyGuruAdapter);
                } else {
                    nearbyGuruAdapter.clear();
                    nearbyGuruAdapter.addAll(nearbyGuruPojoArray);
                    nearbyGuruAdapter.notifyDataSetChanged();
                }break;

            case 3:
                if (nearbyTemplesAdapter == null) {
                    nearbyTemplesAdapter = new NearbyTemplesAdapter(getContext(), this);
                    nearbyTemplesAdapter.addAll(templesPojoArray);
                    R4.setAdapter(nearbyTemplesAdapter);
                } else {
                    nearbyTemplesAdapter.clear();
                    nearbyTemplesAdapter.addAll(templesPojoArray);
                    nearbyTemplesAdapter.notifyDataSetChanged();
                }break;
        }
    }

}
