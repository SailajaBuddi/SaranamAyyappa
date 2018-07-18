package com.anvesh.saranamayyappa.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateComm extends AppCompatActivity implements UpdateVolleyData,ImageUpload.ImageUploadSuccess, View.OnClickListener {

    LinearLayout layout, linearAdd;
    ImageView imageView,back;
    View view;
    EditText editText,mbno,ownername,email,web,fb;
    int PLACE_PICK_REQ = 1;
    FloatingActionButton create;
    TextView tvcount,addrs,toolbar_text;
    int dec = 30,clicked_time_view;
    Uri imageUri;
    int imageStatus = 0;
    String imagepath = "";
    ProgressBar pb;
    private static final int REQUEST_CODE = 1;
    String latitude, longitude;
    private GoogleApiClient mGoogleApiClient;
    String valid,s1,s2,s3,s4,s5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comm);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        findViews();

        editText.addTextChangedListener(watcher);
        ownername.addTextChangedListener(watcher);
        mbno.addTextChangedListener(watcher);
        addrs.addTextChangedListener(watcher);
        email.addTextChangedListener(watcher);

        listeners();
    }




    private void findViews() {
       // Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        editText = (EditText)findViewById(R.id.tv_Name);
        mbno = (EditText)findViewById(R.id.tv_contact);
        ownername = (EditText)findViewById(R.id.tv_ownerName);
        email = (EditText)findViewById(R.id.tv_email);
        web = (EditText)findViewById(R.id.tv_website);
        fb = (EditText)findViewById(R.id.tv_fblink);

        tvcount = (TextView)findViewById(R.id.tv_count);
        toolbar_text=findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Create Commercials");
        back=findViewById(R.id.back);
        back.setOnClickListener(this);

        layout = (LinearLayout)findViewById(R.id.linearimg);
        layout.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.iv_BackGrnd);

        linearAdd = (LinearLayout)findViewById(R.id.linearAdd);
        linearAdd.setOnClickListener(this);

        addrs = (TextView)findViewById(R.id.tv_Addr);
        create = (FloatingActionButton)findViewById(R.id.btn_createEvent);
        pb = (ProgressBar)findViewById(R.id.progressBar1);

    }


    public void listeners(){

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid= checkValidations();
                if(valid.equals("success")) {
                    JSONObject jsonObject = null;
                    String serviceUrl = AyyappaConstants.AYYAPPA_CREATE_SHOP;
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userId", AyyappaPref.getUserId());
                        jsonObject.put("storeName",s1 );
                        jsonObject.put("ownerName", s2);
                        jsonObject.put("storeImageUrl", imagepath);
                        jsonObject.put("phoneNumber", s3);
                        jsonObject.put("storeLocation",addrs.getText().toString());
                        jsonObject.put("latitude", latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("email", s5);
                        jsonObject.put("category", AyyappaPref.getCommCategory());
                        jsonObject.put("facebookLink",fb.getText().toString() );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("CREATE_NEW_SHOP", jsonObject + "");

                    VolleySingleton.getInstance(CreateComm.this).addToQueueWithJsonRequest(jsonObject, serviceUrl, CreateComm.this, null);
                    //addrs.setText(jsonObject.toString());
                    //Toast.makeText(CreateComm.this, "Sucess", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreateComm.this, " "+valid, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void startCropImageActivity(Uri imageUri) {
             CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(7, 5 )
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("sample1");
        //for Address
        if (requestCode == PLACE_PICK_REQ) {
            if (resultCode == RESULT_OK) {
               // Place place = PlacePicker.getPlace(data, getActivity());
                Place place = PlacePicker.getPlace(data,getApplicationContext());
                String address = String.format("%s", place.getAddress());
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
                addrs.setText(address);

            }
        }
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(getApplicationContext(), data);
            imageStatus++;
            // imgEvent.setImageURI(imageUri);
            System.out.println("Gallery/Camera Image URI imageUri: " + imageUri);
            startCropImageActivity(imageUri);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String compressedImagePath = util.compressedImageURI(resultUri.getPath(), CreateComm.this);

                System.out.println("compressedImagePath : " + compressedImagePath + " Crop Image URI : " + resultUri);

                if (util.isNetworkAvailable()) {
                    pb.setVisibility(View.VISIBLE);
                    ImageUpload imgUpload = new ImageUpload(CreateComm.this, pb, false, null);

                    if (!imgUpload.callingBlobSuccess(compressedImagePath, "image/jpeg")) {
                        Toast.makeText(getApplicationContext(), "Pic upload failed.", Toast.LENGTH_SHORT).show();
                    }

                    /*Picasso.with(CreateComm.this)
                            .load(new File(compressedImagePath))
                            .into(imageView);*/

                }
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                return;


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


     private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            valid= checkValidations();
            if(!valid.equals("success")) {
                create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.white)}));
                DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.black));
            }
        }
    };

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() != 10) {
                check = false;
                mbno.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }
   /* private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
*/
   String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
           + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

   private String checkValidations() {
        s1 = editText.getText().toString();
        s2 = ownername.getText().toString();
        s3 = mbno.getText().toString().trim();
        s4 = addrs.getText().toString();
        s5 = email.getText().toString().trim();

        if(s1.equals("")|| s2.equals("")|| s3.equals("")|| s4.equals("")){
            //Toast.makeText(CreateComm.this,"Enter Mandatory Fields",Toast.LENGTH_SHORT).show();
            return "Enter Mandatory Fields";
        }else  if(!isValidMobile(s3) ){
            //Toast.makeText(CreateComm.this,"Phone Number is not Valid",Toast.LENGTH_SHORT).show();
            return "Phone Number is not Valid";
        } else if(!s5.equals("")&&!s5.matches(EMAIL_PATTERN)){
                //Toast.makeText(CreateComm.this,"Invalid Mail",Toast.LENGTH_SHORT).show();
                return "Invalid Mail";

        } else{
            create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.orange)}));
            DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.white));
            return "success";
        }
   }


 @Override
  public void imageUploadSuccess(String azureURL) {
       imagepath=azureURL;
      pb.setVisibility(View.GONE);
     Picasso.with(CreateComm.this)
             .load(imagepath)
             .into(imageView);

     valid= checkValidations();
     if(valid.equals("success")) {
         create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.orange)}));
         DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.white));
     }

     Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
  }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.linearAdd:
                linearAdd.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        linearAdd.setClickable(true);
                    }
                }, 2000);
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(this);
                    startActivityForResult(intent, PLACE_PICK_REQ);
                    //startActivityForResult(builder.build(CreateEvent.this), PLACE_PICK_REQ);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.linearimg:

                layout.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        layout.setClickable(true);
                    }
                }, 2000);
                CropImage.startPickImageActivity(CreateComm.this);
                break;

            case R.id.btn_createEvent:
                break;

        }
    }


    @Override
    public void updateFromVolley(Object result) {
        System.out.println("Create Group::" + result);
        if (result instanceof JSONObject) {

            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        //JSONArray data = ((JSONObject) result).getJSONArray("data");
                        //System.out.println("Login Data" + data.toString());
                        System.out.println("in status code 1::" + result);
                        Toast.makeText(CreateComm.this, "Shop Created", Toast.LENGTH_SHORT).show();


                       Intent intent = new Intent(getApplicationContext(), Comm_list_type.class);
                        startActivity(intent);
                        finish();
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

    @Override
    public void updateFromVolley(Object result, int resultCode) {

    }
}
