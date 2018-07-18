package com.anvesh.saranamayyappa.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.Fragments.AboutCommercial;
import com.anvesh.saranamayyappa.Fragments.Store_Feed;
import com.anvesh.saranamayyappa.R;

import java.util.ArrayList;
import java.util.List;

public class Commercial_inner extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener  {

    ImageView back;
    TextView toolbar_text;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private int mMaxScrollSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_thread);

        toolbar_text=findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Ayyappa Store");
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.appbar);
        /* appbarLayout.addOnOffsetChangedListener(getActivi);*/
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
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

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Store_Feed(), "Gallery");
        adapter.addFragment(new AboutCommercial(), "About");
        viewPager.setAdapter(adapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
