package com.anvesh.saranamayyappa.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.Comm_list_type;
import com.anvesh.saranamayyappa.activity.Commercials;
import com.anvesh.saranamayyappa.activity.EventDetails;
import com.anvesh.saranamayyappa.activity.Groups;
import com.anvesh.saranamayyappa.activity.Group_Inside;
import com.anvesh.saranamayyappa.activity.NearByPuja;
import com.anvesh.saranamayyappa.activity.PlayActivity;
import com.anvesh.saranamayyappa.activity.StoryDetails;
import com.anvesh.saranamayyappa.adapters.Dumy_Adapter;
import com.anvesh.saranamayyappa.adapters.MaterialsAdapter;
import com.anvesh.saranamayyappa.adapters.NearByGuruAdapter;
import com.anvesh.saranamayyappa.adapters.NearbyPujasAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.model.AyyappaSongsPojo;
import com.anvesh.saranamayyappa.model.NearByGuruPojo;
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

import static com.facebook.login.widget.ProfilePictureView.TAG;

public class Home extends Fragment implements View.OnClickListener, UpdateVolleyData, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView R1,R2,R3,R4,R5,R6;
    Dumy_Adapter dumy_adapter3,dumy_adapter4,dumy_adapter5,dumy_adapter6;
    TextView ViewAll_np,ViewAll_ngg,ViewAll_nc,ViewAll_bs,ViewAll_bv,ViewAll_as;
    String s1[],commCat[];
    TypedArray imgs;

    ViewPager viewPager;
    ArrayList<NearByPujaPojo> nearbyPujaPojoArray;
    ArrayList<NearByGuruPojo> nearbyGuruPojoArray;
    NearbyPujasAdapter nearbyPujasAdapter;
    NearByGuruAdapter nearbyGuruAdapter;
    LinearLayout sliderDotspanel;
    private int dotscount;
    ViewPagerAdapter viewPagerAdapter;
    private ImageView[] dots;
    SwipeRefreshLayout swipeRefreshLayout;
    MaterialsAdapter ayyappaSongsAdapter, videosAdapter;
    ArrayList<AyyappaSongsPojo> ayyappaSongsList, AyyappaVideosList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        nearbyPujaPojoArray = new ArrayList<>();
        nearbyGuruPojoArray = new ArrayList<>();
        ayyappaSongsList = new ArrayList<>();
        AyyappaVideosList = new ArrayList<>();

        findViews(root);
        listiners();
        adapters();
        slider();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        callNearbyEvents();
        callNearbyGuruSwamyGroups();
        callMaterials();

    }

    @Override
    public void onRefresh() {
        callNearbyEvents();
        callNearbyGuruSwamyGroups();
        callMaterials();
    }

    private void findViews(View root) {
        R1=root.findViewById(R.id.rv1);
        R2=root.findViewById(R.id.rv2);
        R3=root.findViewById(R.id.rv3);
        R4=root.findViewById(R.id.rv4);
        R5=root.findViewById(R.id.rv5);
        R6=root.findViewById(R.id.rv6);
        ViewAll_np=root.findViewById(R.id.viewAll_np);
        ViewAll_ngg=root.findViewById(R.id.viewAll_ngg);
        ViewAll_nc=root.findViewById(R.id.viewAll_nc);
        ViewAll_bv=root.findViewById(R.id.viewAll_bv);
        ViewAll_bs=root.findViewById(R.id.viewAll_bs);
        ViewAll_as=root.findViewById(R.id.viewAll_as);

       // R1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        R1.setLayoutManager(layoutManager);
        R2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R5.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R6.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        swipeRefreshLayout=root.findViewById(R.id.swipe_refresh_layout);

        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) root.findViewById(R.id.SliderDots);

        s1=getResources().getStringArray(R.array.comm);
        commCat=getResources().getStringArray(R.array.commCategory);
        imgs = getResources().obtainTypedArray(R.array.im);

    }

    private void listiners() {
        ViewAll_np.setOnClickListener(this);
        ViewAll_ngg.setOnClickListener(this);
        ViewAll_nc.setOnClickListener(this);
        ViewAll_bv.setOnClickListener(this);
        ViewAll_bs.setOnClickListener(this);
        ViewAll_as.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        onRefresh();
                                    }
                                }
        );*/
    }



    private void adapters() {

        dumy_adapter3= new Dumy_Adapter(getContext(),R.layout.card_story,s1,imgs, this,3) ;
        R3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R3.setAdapter((RecyclerView.Adapter) dumy_adapter3);

        dumy_adapter4= new Dumy_Adapter(getContext(),R.layout.card_comm_horizontal,s1,imgs, this,4,6) ;
        R4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        R4.setAdapter((RecyclerView.Adapter) dumy_adapter4);


    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.viewAll_np:
                intent = new Intent(getContext(),NearByPuja.class);
                startActivity(intent);
                break;
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
            case R.id.viewAll_ngg:
                intent = new Intent(getContext(),Groups.class);
                startActivity(intent);
                break;
            case R.id.viewAll_nc:
                intent = new Intent(getContext(),Commercials.class);
                startActivity(intent);
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
            case R.id.comm_cv:
                Intent intent3 = new Intent(getContext(),Comm_list_type.class);
                int itemPosition = R4.getChildLayoutPosition(view);
                String item = commCat[itemPosition];
                AyyappaPref.saveCommCategory(item);
                startActivity(intent3);
                break;
            case R.id.iv_videoPlay:
                AyyappaSongsPojo ayyappaSongsPojo = (AyyappaSongsPojo) view.getTag();
                redirectToPlayActivity(ayyappaSongsPojo.getVideoUrl());
                break;
            case R.id.iv_songPlay:
                AyyappaSongsPojo ayyappaSongsPojo1 = (AyyappaSongsPojo) view.getTag();
                redirectToPlayActivity(ayyappaSongsPojo1.getSongUrl());
                break;
            case R.id.story_cv:
                Intent intent4 = new Intent(getContext(),StoryDetails.class);
                startActivity(intent4);
                break;
            case R.id.viewAll_as:

                break;
        }
    }

    public void callMaterials() {
        swipeRefreshLayout.setRefreshing(true);
        String serviceUrl = AyyappaConstants.AYYAPPA_MATERIALS + "userId=" + AyyappaPref.getUserId();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequestAndResultCode(null, serviceUrl, getContext(), Home.this,3);
    }

    private void callNearbyEvents() {
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_PUJAS + "userId=" + AyyappaPref.getUserId() + "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequestAndResultCode(null, serviceUrl, getContext(), Home.this,1);

    }

    private void callNearbyGuruSwamyGroups() {
        String serviceUrl = AyyappaConstants.AYYAPPA_NEARBY_GURUSWAMYGROUPS + "userId=" + AyyappaPref.getUserId() + "&latitude=" + AyyappaPref.getLatitude() + "&longitude=" + AyyappaPref.getLongitude();
        VolleySingleton.getInstance(getContext()).addToQueueWithJsonRequestAndResultCode(null, serviceUrl, getContext(), Home.this,2);
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
                    try {
                        if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                            JSONArray data = ((JSONObject) result).getJSONArray("data");
                            System.out.println("Near By " + data.toString());
                            Gson gson = new Gson();
                            if(resultCode==1) {
                                Type listType = new TypeToken<List<NearByPujaPojo>>() {}.getType();
                                nearbyPujaPojoArray = gson.fromJson(data.toString(), listType);
                            } else if(resultCode==2) {
                                Type listType = new TypeToken<List<NearByGuruPojo>>() {}.getType();
                                nearbyGuruPojoArray = gson.fromJson(data.toString(), listType);
                            }else {
                                Type listType = new TypeToken<List<AyyappaSongsPojo>>() {
                                }.getType();

                                JSONArray songs = data.getJSONObject(0).getJSONArray("songs");
                                ayyappaSongsList = gson.fromJson(songs.toString(), listType);

                                JSONArray ayyappaDevotionalVideos = data.getJSONObject(0).getJSONArray("videos");
                                AyyappaVideosList = gson.fromJson(ayyappaDevotionalVideos.toString(), listType);
                            }

                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            notifyAdapter(resultCode);
           }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void redirectToPlayActivity(String imageUrl) {
        if (imageUrl != null) {
            Intent playIntent = new Intent(getActivity(), PlayActivity.class);
            playIntent.putExtra("VIDEO_URL", imageUrl);
            getActivity().startActivity(playIntent);
        }
    }

    void notifyAdapter(int resultCode) {
        switch (resultCode) {
            case 1:
                if (nearbyPujasAdapter == null) {
                    nearbyPujasAdapter = new NearbyPujasAdapter(getContext(), this,/*R.layout.card_event_h*/R.layout.dummy_card);
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
                notifySongsAdapter();
                notifyVideosAdapter();
                break;

        }
    }
    public void notifySongsAdapter() {
        if (ayyappaSongsAdapter == null) {
            ayyappaSongsAdapter = new MaterialsAdapter(getContext(), this,R.layout.card_song);
            ayyappaSongsAdapter.addAll(ayyappaSongsList);
            R5.setAdapter(ayyappaSongsAdapter);
        } else {
            ayyappaSongsAdapter.clear();
            ayyappaSongsAdapter.addAll(ayyappaSongsList);
            ayyappaSongsAdapter.notifyDataSetChanged();
        }

    }
    public void notifyVideosAdapter() {
        if (videosAdapter == null) {
            videosAdapter = new MaterialsAdapter(getContext(), this,R.layout.card_video);
            videosAdapter.addAll(AyyappaVideosList);
            R6.setAdapter(videosAdapter);
        } else {
            videosAdapter.clear();
            videosAdapter.addAll(AyyappaVideosList);
            videosAdapter.notifyDataSetChanged();
        }
    }

    private void slider() {

        viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(4, 0, 4, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * This method will be invoked when a new page becomes selected. Animation is not
             * necessarily complete.
             *
             * @param position Position index of the new selected page.
             */
            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }
            /**
             * Called when the scroll state changes. Useful for discovering when the user
             * begins dragging, when the pager is automatically settling to the current page,
             * or when it is fully stopped/idle.
             *
             * @param state The new scroll state.
             * @see ViewPager#SCROLL_STATE_IDLE
             * @see ViewPager#SCROLL_STATE_DRAGGING
             * @see ViewPager#SCROLL_STATE_SETTLING
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private Integer[] images = {R.drawable.img2, R.drawable.img3,R.drawable.img4, R.drawable.img5};

        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.customImage);
            imageView.setImageResource(images[position]);


            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }

    }


}

