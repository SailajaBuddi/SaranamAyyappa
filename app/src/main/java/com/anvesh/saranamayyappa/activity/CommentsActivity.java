package com.anvesh.saranamayyappa.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;


/**
 * Created by ismuser16 on 05-Sep-17.
 */

public class CommentsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView close;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        close=(ImageView)findViewById(R.id.comment_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
