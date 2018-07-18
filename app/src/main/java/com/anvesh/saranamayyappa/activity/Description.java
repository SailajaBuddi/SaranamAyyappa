package com.anvesh.saranamayyappa.activity;

        import android.content.Intent;
        import android.net.Uri;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
        import com.anvesh.saranamayyappa.R;
        import com.anvesh.saranamayyappa.app.AyyappaConstants;
        import com.anvesh.saranamayyappa.app.AyyappaPref;
        import com.anvesh.saranamayyappa.network.VolleySingleton;
        import com.anvesh.saranamayyappa.utils.ImageUpload;
        import com.anvesh.saranamayyappa.utils.util;
        import com.squareup.picasso.Picasso;
        import com.theartofdev.edmodo.cropper.CropImage;
        import com.theartofdev.edmodo.cropper.CropImageView;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.File;

public class Description extends AppCompatActivity implements View.OnClickListener,ImageUpload.ImageUploadSuccess,UpdateVolleyData {
    EditText et_desc;
    TextView tvcount;
    ImageView iv_bArrow,Image,uploadImg;
    Toolbar toolbar;
    int imageStatus = 0;
    String message=" ";
    int flag=0,atF=0;
    Uri imageUrl;
    ProgressBar pb;
    String imagepath = "";
    LinearLayout ll_image;

    ImageView Att_im;
    RelativeLayout Att_lay,rl_Post;
    TextView S_post;
    String classType,serviceUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        message = getIntent().getStringExtra("message");
        classType = getIntent().getStringExtra("classType");

       // flag = intent.getIntExtra("flag",0);

        findViews();
        listeners();
        textwatcher();
        //et_desc.setText(message);
        rl_Post.setVisibility(View.VISIBLE);


        et_desc.setText(message);
    }

    private void findViews() {
        et_desc = findViewById(R.id.et_desc);
        iv_bArrow = findViewById(R.id.back);
        tvcount = findViewById(R.id.tv_count);
        Image=findViewById(R.id.iv_upload);
        uploadImg=findViewById(R.id.uploadImg);

        Att_im=findViewById(R.id.attach);
        S_post=findViewById(R.id.s_post);
        Att_lay=findViewById(R.id.att_lay);
        rl_Post=findViewById(R.id.rl_post);
        pb = findViewById(R.id.progressBar1);
        ll_image=findViewById(R.id.ll_image);
        TextView toolbar_text=findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Create Feed");


    }

    private void textwatcher() {
        et_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvcount.setText(String.valueOf(s.length()));
                /*if (s.length() > 1075)
                {
                    Toast.makeText(Description.this, "Character limit exceeded", Toast.LENGTH_SHORT).show();
                }*/
            }
            @Override
            public void afterTextChanged(Editable s) {
                for (int i = s.length(); i > 0; i--) {

                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");
                }

                checkFieldsForEmptyValues();
            }
        });

    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
               /* .setAspectRatio(1, 1)*/
                /*.setFixAspectRatio(true)*/
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("sample1");

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUrl = CropImage.getPickImageResultUri(this, data);
            imageStatus++;
            // imgEvent.setImageURI(imageUri);
            System.out.println("Gallery/Camera Image URI imageUri: " + imageUrl);
            startCropImageActivity(imageUrl);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String compressedImagePath = util.compressedImageURI(resultUri.getPath(), Description.this);

                System.out.println("compressedImagePath : " + compressedImagePath + " Crop Image URI : " + resultUri);

                if (util.isNetworkAvailable()) {
                    pb.setVisibility(View.VISIBLE);
                    ImageUpload imgUpload = new ImageUpload(Description.this, pb, false, null);

                    if (!imgUpload.callingBlobSuccess(compressedImagePath, "image/jpeg")) {
                        Toast.makeText(getApplicationContext(), "Pic upload failed.", Toast.LENGTH_SHORT).show();
                    }
                    Att_lay.setVisibility(View.GONE);

                    ll_image.setVisibility(View.VISIBLE);
                    /*Picasso.with(Description.this)
                            .load(new File(compressedImagePath))
                            .into(Image);*/

                }
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                return;


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void listeners() {
        uploadImg.setOnClickListener(this);
        S_post.setOnClickListener(this);
        Att_im.setOnClickListener(this);

        iv_bArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void createFeed() {

        JSONObject jsonObject = null;
        classType = getIntent().getStringExtra("classType");
        if (classType.equals("Ufeed")) {
            serviceUrl = AyyappaConstants.AYYAPPA_POST_FEED;
        }else if (classType.equals("gwall")) {
            serviceUrl = AyyappaConstants.AYYAPPA_POST_GURUSWAMY_WALL;
        }else if (classType.equals("gfeed")) {
            serviceUrl = AyyappaConstants.AYYAPPA_POST_GURUSWAMY_FEED;
        }else {
            serviceUrl = AyyappaConstants.AYYAPPA_POST_STORE_FEED;
        }
        jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", AyyappaPref.getUserId());
            jsonObject.put("groupId", AyyappaPref.getGroupId());
            jsonObject.put("storeId", AyyappaPref.getStoreId());
            jsonObject.put("description",et_desc.getText().toString());
            jsonObject.put("imageUrl",imagepath);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("CREATE_NEW_GROUP", jsonObject + "");
        Toast.makeText(this, ""+serviceUrl, Toast.LENGTH_SHORT).show();
        VolleySingleton.getInstance(Description.this).addToQueueWithJsonRequest(jsonObject, serviceUrl, Description.this, null);
    }
    @Override
    public void updateFromVolley(Object result) {

        System.out.println("Create Group::" + result);
        Toast.makeText(this, ""+result.toString(), Toast.LENGTH_SHORT).show();
        if (result instanceof JSONObject) {
            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        //JSONArray data = ((JSONObject) result).getJSONArray("data");
                        System.out.println("in status code 1::" + result);
                        Toast.makeText(Description.this, "Feed Created", Toast.LENGTH_SHORT).show();

                        finish();
                      /*  Intent intent = new Intent(getApplicationContext(), Groups.class);
                        startActivity(intent);
                        finish();*/
                    } else {
                        System.out.println("in status code else ::" + result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }

    }

    private void checkFieldsForEmptyValues() {
        String s1 = et_desc.getText().toString();
      /*  if (s1.equals("")) {
            tv_createEvent.setTextColor(ContextCompat.getColor(Description.this, R.color.grey_light));
        }
        else {
            tv_createEvent.setTextColor(ContextCompat.getColor(Description.this, R.color.black));

        }*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.attach:
                if(atF==0) {
                    Att_lay.setVisibility(View.VISIBLE);
                    atF=1;
                }else {
                    Att_lay.setVisibility(View.GONE);
                    atF=0;
                }
                //   Att.setVisibility(view.GONE);
                break;
            case R.id.s_post:
                Att_lay.setVisibility(View.GONE);
                createFeed();
                break;
            case R.id.uploadImg:
                CropImage.startPickImageActivity(Description.this);
                ll_image.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }

    @Override
    public void imageUploadSuccess(String azureURL) {
        imagepath = azureURL;
        Picasso.with(getApplicationContext())
                .load(imagepath)
                .into(Image);
        pb.setVisibility(View.GONE);

    }
}