package com.anvesh.saranamayyappa.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.Fun2WinFailure;


/**
 * Created by SRI LATHA on 02-09-2017.
 */

public class FragmentQueryFour extends Fragment implements View.OnClickListener{
    ImageView one,two,three,four;
    ImageView cor,cor1,cor2,cor3;

 Button bt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_query_four,null,false);
        one=view.findViewById(R.id.option_one);
        bt=(Button)view.findViewById(R.id.submit);
        two=view.findViewById(R.id.option_two);
        three=view.findViewById(R.id.option_three);
        four=view.findViewById(R.id.option_four);
        cor=view.findViewById(R.id.one_correct);
        cor1=view.findViewById(R.id.two_correct);
        cor2=view.findViewById(R.id.three_correct);
        cor3=view.findViewById(R.id.four_correct);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                Intent intent= new Intent(getContext(), Fun2WinFailure.class);
                startActivity(intent);
                break;
            case R.id.option_one:
                cor.setVisibility(View.VISIBLE);
            case R.id.option_two:
                cor1.setVisibility(View.VISIBLE);
            case R.id.option_three:
                cor2.setVisibility(View.VISIBLE);
            case R.id.option_four:
                cor3.setVisibility(View.VISIBLE);
        }
    }
}
