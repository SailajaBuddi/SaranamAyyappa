package com.anvesh.saranamayyappa.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.QueryAdapter;


public class QuestionsActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentManager fm;
    ImageView closeImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        closeImg=(ImageView)findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.Q_viewpager);
        FragmentManager manager = getSupportFragmentManager();
        viewPager.setAdapter(new QueryAdapter(manager));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}



