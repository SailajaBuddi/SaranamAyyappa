package com.anvesh.saranamayyappa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;

public class StoryDetails extends AppCompatActivity {

    ImageView back;
    TextView toolbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        back=(ImageView)findViewById(R.id.back);
        toolbar_text=findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Ayyappa Birth Stort");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
