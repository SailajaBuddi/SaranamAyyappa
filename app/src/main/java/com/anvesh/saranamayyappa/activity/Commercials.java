package com.anvesh.saranamayyappa.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Dumy_Adapter;
import com.anvesh.saranamayyappa.app.AyyappaPref;

public class Commercials extends AppCompatActivity implements View.OnClickListener {

    String s1[];
    TypedArray imgs;
    RecyclerView R1;
    Dumy_Adapter dumy_adapter1;
    int size=6;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercials);
        findViews();
        listiners();
        adapters();

    }

    private void adapters() {

        dumy_adapter1= new Dumy_Adapter(Commercials.this,R.layout.card_comm_vertical,s1,imgs, this,4,6) ;
        R1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        R1.setAdapter((RecyclerView.Adapter) dumy_adapter1);

    }



    private void listiners() {
        back.setOnClickListener(this);
    }

    private void findViews() {
        R1=findViewById(R.id.rv1);
        s1=getResources().getStringArray(R.array.comm);
        imgs = getResources().obtainTypedArray(R.array.im);
        back=findViewById(R.id.back);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.comm_cv:
                Intent intent2 = new Intent(this,Comm_list_type.class);
                String[] commCat=getResources().getStringArray(R.array.commCategory);
                String item = commCat[R1.getChildLayoutPosition(view)];
                AyyappaPref.saveCommCategory(item);
                startActivity(intent2);
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
