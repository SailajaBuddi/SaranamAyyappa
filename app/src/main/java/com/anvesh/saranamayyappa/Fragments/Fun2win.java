package com.anvesh.saranamayyappa.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.FunAdapter;
import com.anvesh.saranamayyappa.model.FamousTalkProvider;

import java.util.ArrayList;

public class Fun2win extends Fragment {
    Toolbar tool;
    RecyclerView rfun;
    ArrayList<FamousTalkProvider> array = new ArrayList<FamousTalkProvider>();
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutmanager;
    String[] textfun1, textfun2;
    int[] imgf = {R.drawable.img, R.drawable.img1, R.drawable.img2, R.drawable.img4};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fun, container, false);


       // tool = (Toolbar) findViewById(R.id.fun_tool);
       // tool.setTitle("Fun 2 Win Challenges");
        rfun = (RecyclerView)view. findViewById(R.id.fun_rview);

        textfun1 = getResources().getStringArray(R.array.textf1);
        textfun2 = getResources().getStringArray(R.array.textf2);
        int i = 0;
        for (String a : textfun1) {
            FamousTalkProvider dp1 = new FamousTalkProvider(imgf[i], a,textfun2[i] );
            array.add(dp1);
            i++;
        }
        adapter = new FunAdapter(array);
        layoutmanager = new LinearLayoutManager(getActivity());
        rfun.setLayoutManager(layoutmanager);
        rfun.setAdapter(adapter);
        return view;

    }
}
