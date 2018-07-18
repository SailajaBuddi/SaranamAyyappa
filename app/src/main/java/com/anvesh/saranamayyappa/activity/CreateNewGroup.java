package com.anvesh.saranamayyappa.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextWatcher;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.anvesh.saranamayyappa.model.Contact;
import com.anvesh.saranamayyappa.model.Member;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CreateNewGroup extends AppCompatActivity implements ImageUpload.ImageUploadSuccess, View.OnClickListener ,UpdateVolleyData,NavigationView.OnNavigationItemSelectedListener{


    ArrayList<Contact> selectedContactsNames = new ArrayList<>();
    private static final int REQUEST_CODE = 1;
    LinearLayout descEvent, ll_image, ll_category;
    ImageView iv_circular, iv_add,back;
    EditText groupname,desc;
    Uri imageUri;
    int imageStatus = 0;
    TextView toolbar_text, addrs,tinvite,contactsDisplay,tadmin;
    private int REQUEST_INVITE = 1;
    FloatingActionButton create;
    ProgressBar pb;
    String imagepath = "";
    String latitude, longitude,valid;
    int PLACE_PICK_REQ = 999;
    List<Member> modelObjMemberList = new ArrayList<>();
    String membersCount = "";
    ArrayList<String> phoneList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);

        findViews();
        listiners();



    }

    private void listiners() {
        groupname.addTextChangedListener(watcher);
        addrs.addTextChangedListener(watcher);
        ll_category.setOnClickListener(this);
        ll_image.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        back.setOnClickListener(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid= checkValidations();
                if(valid.equals("success")) {
                    JSONObject jsonObject = null;
                    String serviceUrl = AyyappaConstants.AYYAPPA_CREATE_NEW_GROUP;
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userId", AyyappaPref.getUserId());
                        jsonObject.put("groupName",groupname.getText().toString());
                        jsonObject.put("description",desc.getText().toString() );
                        jsonObject.put("category", "GuruSwamy");
                        jsonObject.put("hashTag", "#app");
                        jsonObject.put("latitude",latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("locationName",addrs.getText().toString());
                        jsonObject.put("groupImageUrl", imagepath);
                        jsonObject.put("inviteMember", membersCount);
                        jsonObject.put("phoneNumbers",new JSONArray(phoneList));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("CREATE_NEW_GROUP", jsonObject + "");

                    VolleySingleton.getInstance(CreateNewGroup.this).addToQueueWithJsonRequest(jsonObject, serviceUrl, CreateNewGroup.this, null);
                    //addrs.setText(jsonObject.toString());
                    //Toast.makeText(CreateComm.this, "Sucess", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreateNewGroup.this, " "+valid, Toast.LENGTH_SHORT).show();
                }

            }
        });

        tinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToContacts();
            }

        });

      /*  tadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToContacts();
            }
        });*/
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
            if(valid.equals("success")) {
                create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.orange)}));
                DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.white));
            }
        }
    };

    private void findViews() {
        groupname = findViewById(R.id.et_groupName);
        desc=findViewById(R.id.tv_enterdescEvent);
        pb = findViewById(R.id.progressBar1);
        ll_image = findViewById(R.id.ll_image);
        ll_category = findViewById(R.id.ll_category);
        iv_circular = findViewById(R.id.iv_circular);
        create = findViewById(R.id.btn_createGroup);
        descEvent = findViewById(R.id.ll_descEvent);
        toolbar_text = findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Create New Group");
        addrs = (TextView) findViewById(R.id.tv_Addr);
        iv_add = (ImageView) findViewById(R.id.iv_Add);
        tinvite = findViewById(R.id.tv_Invite);
        tadmin = findViewById(R.id.tv_admin);
        //txt_selected_contacts1 = findViewById(R.id.txt_selected_contacts1);
        contactsDisplay = findViewById(R.id.txt_selected_contacts);
        back=findViewById(R.id.back);
    }


    private String checkValidations() {
        String s1 = groupname.getText().toString();
        String s2 = addrs.getText().toString();
        if (s1.equals("")) {
                return "Enter Group Name";
        } else if (s2.equals("")) {
            return "Choose Group Location";
        } else if (imagepath.equals("")) {
               return "Please Upload Group Image";
        } else {
                return "success";
        }
    }

    @Override
    public void imageUploadSuccess(String azureURL) {
        imagepath = azureURL;
         Picasso.with(CreateNewGroup.this)
                            .load(imagepath)
                            .into(iv_circular);
        valid= checkValidations();
        if(valid.equals("success")) {
            create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.orange)}));
            DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.white));
        }
         pb.setVisibility(View.GONE);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("sample1");
        //for Address

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
                String compressedImagePath = util.compressedImageURI(resultUri.getPath(), CreateNewGroup.this);

                System.out.println("compressedImagePath : " + compressedImagePath + " Crop Image URI : " + resultUri);

                if (util.isNetworkAvailable()) {
                    pb.setVisibility(View.VISIBLE);
                    ImageUpload imgUpload = new ImageUpload(CreateNewGroup.this, pb, false, null);

                    if (!imgUpload.callingBlobSuccess(compressedImagePath, "image/jpeg")) {
                        Toast.makeText(getApplicationContext(), "Pic upload failed.", Toast.LENGTH_SHORT).show();
                    }

                   /* Picasso.with(CreateNewGroup.this)
                            .load(new File(compressedImagePath))
                            .into(iv_circular);*/

                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        else if (requestCode == PLACE_PICK_REQ) {
            if (resultCode == RESULT_OK) {
                // Place place = PlacePicker.getPlace(data, getActivity());
                Place place = PlacePicker.getPlace(this, data);
                String address = String.format("%s", place.getAddress());
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
                addrs.setText(address);

            }
        }
        else if ((resultCode == RESULT_OK) && (requestCode == REQUEST_CODE)) {
            ArrayList<Contact> contacts =  (ArrayList<Contact>) data.getSerializableExtra("data");
            if (contacts != null)
                selectedContactsNames.clear();
            ArrayList<Contact> tempContacts = new ArrayList<>();
            tempContacts.addAll(contacts);
            for (Member member : modelObjMemberList) {
                for (int i = 0; i < tempContacts.size(); i++) {
                    Contact contact = tempContacts.get(i);
                    if (member.getMobileNumber().contains(contact.getNumber())) {
                        contact.isSelected = false;
                        tempContacts.set(i, contact);
                        Toast.makeText(this, contact.getNumber() + "has already a member", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            for (Contact contact : tempContacts) {
                if (contact.isSelected) {
                    selectedContactsNames.add(contact);
                }
            }

         /*   if (!(displaySelectedContactsAdapter == null)) {
                displaySelectedContactsAdapter.notifyDataSetChanged();
            } else {
                setContactsAdapter();
            } */
            membersCount = "" + selectedContactsNames.size();

            contactsDisplay.setText(membersCount +" Phone contacts you added");
           // txt_selected_contacts1.setText(membersCount +" Phone contacts you added");

            phoneList = new ArrayList<>();
            for (int i = 0; i < selectedContactsNames.size(); i++) {
                phoneList.add(selectedContactsNames.get(i).getNumber());
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_category:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                   /* Intent intent = builder.build(this);
                    startActivityForResult(intent, PLACE_PICK_REQ);*/
                    startActivityForResult(builder.build(this), PLACE_PICK_REQ);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.ll_image:
                ll_image.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ll_image.setClickable(true);
                    }
                }, 2000);
                CropImage.startPickImageActivity(CreateNewGroup.this);
                break;

            case R.id.btn_createEvent:
                break;

        }
    }
    public void goToContacts() {
        Intent intent = new Intent(CreateNewGroup.this, ContactsActivity.class);
        intent.putExtra(AyyappaConstants.SELECTED_CONTACTS, selectedContactsNames);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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
                        Toast.makeText(CreateNewGroup.this, "Group Created", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(getApplicationContext(), Groups.class);
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