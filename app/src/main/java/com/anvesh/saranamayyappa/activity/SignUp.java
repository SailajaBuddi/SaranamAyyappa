package com.anvesh.saranamayyappa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.anvesh.saranamayyappa.utils.ImageUpload;
import com.anvesh.saranamayyappa.utils.util;
import com.facebook.accountkit.PhoneNumber;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity implements UpdateVolleyData, ImageUpload.ImageUploadSuccess,View.OnClickListener {

    EditText name, gmail;
    EditText mno;
    ImageView imageView1;
    CircleImageView iv_profile;
    TextView profile;
    Uri imageUri;
    int imageStatus = 0,flag=1;
    String imagepath = "";
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        listeners();

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag",0);

        name.addTextChangedListener(textWatcher);
        gmail.addTextChangedListener(textWatcher);
        mno.addTextChangedListener(textWatcher);
        name.setText(AyyappaPref.getUserName());

    }

    @Override
    protected void onResume() {
        super.onResume();
        imagepath = AyyappaPref.getProfileImagePath();
        if (!TextUtils.isEmpty(AyyappaPref.getProfileImagePath())) {
            Picasso.with(this)
                    .load(imagepath)
                    .into(iv_profile);
            profile.setVisibility(View.INVISIBLE);
        } else {
            //profile.setVisibility(View.INVISIBLE);
            String username = AyyappaPref.getUserName();
            String fn = username.trim();
            if (fn.length() > 1) {
                String fl = fn.substring(0, 1);
                profile.setText(fl);
                profile.setVisibility(View.VISIBLE);
                //profile.setBackgroundColor(util.getRandomColor(AyyappaPref.getUserId()));
            }

        }
        Log.e("NAME", AyyappaPref.getUserName());
        //name.setText(AyyappaPref.getUserName());

    }
    private void listeners() {
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUp.this, "Enter all Fields", Toast.LENGTH_SHORT).show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CropImage.startPickImageActivity(SignUp.this);
                checkPermissions();
            }
        });

        /*iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(SignUp.this);
            }
        });*/
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void findViews() {
        name = findViewById(R.id.et_FirstName);
        name.setSelection(0);
        name.requestFocus();
        profile = findViewById(R.id.tv_profile);
        gmail = findViewById(R.id.et_gmail);
        mno = findViewById(R.id.et_mobileNo);
        imageView1 = findViewById(R.id.circle1);
        iv_profile = findViewById(R.id.iv_profile);
        pb = findViewById(R.id.progressBar1);
        mno.setText(AyyappaPref.getUserMobileNum());
        mno.setEnabled(false);
        iv_profile.setOnClickListener(this);
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() != 10) {
                check = false;
                mno.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkFieldsForEmptyValues();
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {

            imageUri = CropImage.getPickImageResultUri(this, data);
            imageStatus++;
            System.out.println("Gallery/Camera Image URI imageUri: " + imageUri);
            startCropImageActivity(imageUri);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String compressedImagePath = util.compressedImageURI(resultUri.getPath(), SignUp.this);

                System.out.println("compressedImagePath : " + compressedImagePath + " Crop Image URI : " + resultUri);

                if (util.isNetworkAvailable()) {
                    pb.setVisibility(View.VISIBLE);
                    ImageUpload imgUpload = new ImageUpload(SignUp.this, pb, false, null);

                    if (!imgUpload.callingBlobSuccess(compressedImagePath, "image/jpeg")) {
                        Toast.makeText(getApplicationContext(), "Pic upload failed.", Toast.LENGTH_SHORT).show();
                    }

                    Picasso.with(SignUp.this)
                            .load(new File(compressedImagePath))
                            .into(iv_profile);

                }
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                return;


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void imageView1(View view){
        if (!util.isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            validation();
        }
    }

    private void validation() {
        if (name.getText().toString().length()==0) {
            Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
        } /*else if (gmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter gmail", Toast.LENGTH_LONG).show();
        }*/  else {
            imageView1 = findViewById(R.id.circle1);
            String s5 = iv_profile.getScaleType().toString();
            String s1 = name.getText().toString().trim();
            String s3 = gmail.getText().toString();
            String s4 = mno.getText().toString();
            if (s5.equals("")) {
                iv_profile.setVisibility(View.GONE);
                name.setVisibility(View.VISIBLE);
                imageView1.setEnabled(false);
                imageView1.setImageResource(R.drawable.check1);
            } else if (s1.equals("")) {
                imageView1.setEnabled(false);
                imageView1.setImageResource(R.drawable.check1);
            }
        /* else if (!isValidEmail(s3)) {
            imageView1.setEnabled(false);
            imageView1.setImageResource(R.drawable.check1);
        }*/
            else {
                imageView1.setEnabled(true);
                imageView1.setImageResource(R.drawable.check2);

            }
        }
    }

    private void checkFieldsForEmptyValues() {
            validation();
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signUpService();
                    /*System.out.println("GET Username" + AyyappaPref.getUserName;
                    String fn=name.getText().toString().trim();
                    String fl = fn.substring(0, 1);
                    profile.setText(fl);
                    String username = name.toString();
                    AyyappaPref.saveUserName(username);*/
                    if (flag==0){
                        Toast.makeText(getApplicationContext(), "You have successfuly login", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    @Override
    public void imageUploadSuccess(String azureURL) {
        imagepath = azureURL;
        Log.e("image path is ", imagepath + "");

        AyyappaPref.saveProfileImagePath(imagepath);
        Picasso.with(getApplicationContext())
                .load(AyyappaPref.getProfileImagePath())
                .into(iv_profile);

        Toast.makeText(getApplicationContext(), "Image has been uploaded.", Toast.LENGTH_LONG).show();
        pb.setVisibility(View.GONE);
    }

    /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(SignUp.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onBackPressed();
        return super.onKeyDown(keyCode, event);
    }

    public void signUpService() {
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", AyyappaPref.getUserId());
            jsonObject.put("profileImage", imagepath);
            jsonObject.put("fullName", name.getText().toString());
            jsonObject.put("email", gmail.getText().toString());
             /*   jsonObject.put("eventImageUrl", );*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("jsonObject req ", "" + jsonObject);
        VolleySingleton.getInstance(SignUp.this).addToQueueWithJsonRequest(jsonObject, AyyappaConstants.AYYAPPA_UPDATE_USERLOGINMFB, SignUp.this, null);
    }

    @Override
    public void updateFromVolley(Object response, int resultCode) {  }

    @Override
    public void updateFromVolley(Object result) {
        System.out.println("in status code 1::" + result);
        if (result instanceof JSONObject) {

            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        JSONObject data = ((JSONObject) result).getJSONObject("data");
                        System.out.println("Login Data" + data.toString());
                        System.out.println("in status code 1::" + result);
                        AyyappaPref.saveProfileImagePath(data.getString("profileImage"));
                        AyyappaPref.saveUserName(data.getString("fullName"));
                        //Toast.makeText(getApplicationContext(), "profile_created", Toast.LENGTH_SHORT).show();
                        // updateLoginStatus();
                        if (flag == 1){
                            onBackPressed();
                        }
                        else {
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        System.out.println("in status code else ::" + result);

                        //  Util.hideRefreshProgress(mSwipeRefreshLayout);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_profile:
                iv_profile.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        iv_profile.setClickable(true);
                    }
                }, 2000);
                checkPermissions();
                break;

        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, AyyappaConstants.PERMISSIONS_REQUEST_RCAM_RSTORE);
            } else {
                CropImage.startPickImageActivity(SignUp.this);
            }
        } else {
            CropImage.startPickImageActivity(SignUp.this);
        }
    }
}
