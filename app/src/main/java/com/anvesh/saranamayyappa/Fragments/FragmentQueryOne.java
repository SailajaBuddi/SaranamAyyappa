package com.anvesh.saranamayyappa.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;


/**
 * Created by SRI LATHA on 01-09-2017.
 */

public class FragmentQueryOne extends Fragment implements View.OnClickListener{
    ImageView r1,r2,r3,r4;
    ViewPager viewPager;
  Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_query_one,null,false);
        r1= (ImageView) view.findViewById(R.id.option_r1);
        r2= (ImageView) view.findViewById(R.id.option_r2);
        r3= (ImageView) view.findViewById(R.id.option_r3);
        r4= (ImageView) view.findViewById(R.id.option_r4);
        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);

        viewPager= (ViewPager) getActivity().findViewById(R.id.Q_viewpager);

        return view;

    }
    public void setImage(ImageView image){
        image.setImageResource(R.drawable.radio_checked);
        image.setTag("pic2");
    }
    public void getPreviousImage(ImageView imageView){
        imageView.setImageResource(R.drawable.radio_unselect);
        imageView.setTag("pic1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.option_r1:
                if(r1.getTag() != null && r1.getTag().toString().equals("pic1")){
                    setImage(r1);
                    getPreviousImage(r2);
                    getPreviousImage(r3);
                    getPreviousImage(r4);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(1);

                        }
                    },1000);


                    //viewPager.setCurrentItem(1);
                } else {
                    getPreviousImage(r1);
                }
                break;
            case R.id.option_r2:
                if(r2.getTag() != null && r2.getTag().toString().equals("pic1")){
                    setImage(r2);
                    getPreviousImage(r1);
                    getPreviousImage(r3);
                    getPreviousImage(r4);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(1);

                        }
                    },1000);
                   // viewPager.setCurrentItem(1);

                } else {
                    getPreviousImage(r2);
                }
                break;
            case R.id.option_r3:
                if(r3.getTag() != null && r3.getTag().toString().equals("pic1")){
                    setImage(r3);
                    getPreviousImage(r2);
                    getPreviousImage(r1);
                    getPreviousImage(r4);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(1);

                        }
                    },1000);
                   //viewPager.setCurrentItem(1);
                } else {
                    getPreviousImage(r3);
                }
                break;
            case R.id.option_r4:
                if(r4.getTag() != null && r4.getTag().toString().equals("pic1")){
                    setImage(r4);
                    getPreviousImage(r2);
                    getPreviousImage(r3);
                    getPreviousImage(r1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(1);

                        }
                    },1000);
                   // viewPager.setCurrentItem(1);
                } else {
                    getPreviousImage(r4);
                }
                break;
            default:break;
        }


    }
}

