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

public class FragmentQueryThree extends Fragment implements View.OnClickListener{
    ImageView yes,no,correct,correct2;
    ViewPager viewPager;
    Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_query_three,null,false);
        yes=view.findViewById(R.id.yes);
        no=view.findViewById(R.id.no);
        correct=view.findViewById(R.id.correct);
        correct2=view.findViewById(R.id.correct1);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        viewPager= (ViewPager) getActivity().findViewById(R.id.Q_viewpager);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes:
                correct.setVisibility(View.VISIBLE);
                correct2.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(3);
                    }
                },1000);
                break;
            case R.id.no:
                correct2.setVisibility(View.VISIBLE);
                correct.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(3);
                    }
                },1000);
                break;
            default:break;
        }

    }
}
