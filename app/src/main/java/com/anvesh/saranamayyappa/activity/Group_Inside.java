package com.anvesh.saranamayyappa.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.Fragments.AboutGroup;
import com.anvesh.saranamayyappa.Fragments.GroupMembers;
import com.anvesh.saranamayyappa.Fragments.Group_Feed;
import com.anvesh.saranamayyappa.Fragments.Group_Wall;
import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.login.widget.ProfilePictureView.TAG;

public class Group_Inside extends AppCompatActivity implements View.OnClickListener, UpdateVolleyData, AppBarLayout.OnOffsetChangedListener  {
   /* Wall_Adapter wall_adapter;
    private RecyclerView posts;
    String s1[];*/
    TextView toolbar_text,join;
    ImageView back,toolbar_img;
    TabLayout tabLayout;
    ViewPager viewPager;
    private int mMaxScrollSize;
    int joinState=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_thread);

            toolbar_img=findViewById(R.id.toolbar1_img);
            join=findViewById(R.id.tv_join);
            toolbar_text=findViewById(R.id.toolbar1_text);
            toolbar_text.setText(getIntent().getStringExtra("name"));
            joinState = getIntent().getIntExtra("joinStatus",0);
            if(joinState==0){
                join.setVisibility(View.VISIBLE);
            }else{ join.setVisibility(View.GONE); }

            toolbar_img.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(getIntent().getStringExtra("imagePath"))) {
            Picasso.with(this)
                    .load(R.drawable.img)
                    .into(toolbar_img);
        } else {
            Picasso.with(this)
                    .load(getIntent().getStringExtra("imagePath"))
                    .placeholder(R.drawable.img)
                    .into(toolbar_img);
        }
           back=findViewById(R.id.back);
           back.setOnClickListener(this);
           join.setOnClickListener(this);

        AppBarLayout appbarLayout = findViewById(R.id.appbar);
       /* appbarLayout.addOnOffsetChangedListener(getActivi);*/
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        viewPager = findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

    }

    public Boolean isOwner(){
        return getIntent().getBooleanExtra("owner", false);
    }
    public int isJoin(){ return joinState; }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_join:
                 joinOrUnjoinGroup(1);
                break;
            /*case R.id.btn_Float1:
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout, new Add_post(), "NewFragmentTag");
                ft.commit();
                ft.addToBackStack(null);
                break;*/
           /* case R.id.write_post:
                Intent intent=new Intent(this, Description.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;*/
        }
    }


    public void joinOrUnjoinGroup(int i){
        String serviceUrl = AyyappaConstants.AYYAPPA_JOIN_UNJOIN_GROUPS + "userId=" + AyyappaPref.getUserId() + "&groupId=" + AyyappaPref.getGroupId()  + "&joinStatus=" + i;
        VolleySingleton.getInstance(this).addToQueueWithJsonRequest(null, serviceUrl, this, null);
    }

    @Override
    public void updateFromVolley(Object result) {
        System.out.println("updateFromVolley Event UPDATED VERIFY " + result + " " + (result instanceof JSONObject));
        if (result instanceof JSONObject) {
            Log.d(TAG, "updateFromVolley: "+result.toString());
            System.out.println("updateFromVolley ");
            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONObject data = ((JSONObject) result).getJSONObject("data");
                      // Toast.makeText(this, ""+data.toString(), Toast.LENGTH_SHORT).show();

                        if(data.getString("joinStatus").equals("1"))  {
                            join.setVisibility(View.GONE);
                            joinState = 1;
                             }else{     finish();   }

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

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Group_Wall(), "Wall");
        adapter.addFragment(new Group_Feed(), "Feeds");
        adapter.addFragment(new GroupMembers(), "Members");
        adapter.addFragment(new AboutGroup(), "About");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

         ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
           /* Bundle bundle=new Bundle();
            bundle.putString("classType","kl");*/

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

         void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
