

package com.anvesh.saranamayyappa.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class CreateEvent extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,ImageUpload.ImageUploadSuccess,UpdateVolleyData {

    TextView rsvp_date, rsvp_time, toolbar_text, fromDate, fromTime, toDate, toTime, addrs, tinvite, contactsDisplay,limit;
    SwitchCompat switchCompat;
    LinearLayout rsvp_layout, dateStartEvent, timeStartEvent, enddateEvent, endtimeEvent, llrsvpdate, llrsvptime, ll_category, ll_image;
    DatePickerDialog rsvpDatePickerDialog, fromDatePickerDialog, toDatePickerDialog;
    ImageView back, iv_backgrnd;
    SimpleDateFormat dateFormatter;
    FloatingActionButton create;
    TimePickerDialog fromTimePickerDialog, toTimePickerDialog, rsvpTimePickerDialog;
    ProgressBar pb;
    Uri imageUri;
    int imageStatus = 0;
    int mHour, mMinute;
    int fromHour, fromMinute, toHour, toMinute;
    String latitude, longitude;
    int PLACE_PICK_REQ = 999;
    ArrayList<Contact> selectedContactsNames = new ArrayList<>();
    private static final int REQUEST_CODE = 1;
    List<Member> modelObjMemberList = new ArrayList<>();
    String membersCount = "";
    ArrayList<String> phoneList = null;
    String imagePath;
    EditText editText;
    String s1, s2, s3, s4, s5, s6, s7,valid,s8,s9;
    String imagepath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        rsvp_layout = findViewById(R.id.rsvp_layout);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        switchCompat = (SwitchCompat) findViewById(R.id.switchcompat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    //Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                    rsvp_layout.setVisibility(View.VISIBLE);

                } else {
                    //Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                    rsvp_layout.setVisibility(View.GONE);
                }

            }
        });


      /* fromTime.addTextChangedListener(watcher);
        fromDate.addTextChangedListener(watcher);
        toTime.addTextChangedListener(watcher);
        toDate.addTextChangedListener(watcher);
       editText.addTextChangedListener(watcher);
       addrs.addTextChangedListener(watcher);*/


        findViews();
        listeners();

        fromDatePicker();
        toDatePicker();
        fromTimePicker();
        toTimePicker();

        rsvpDatePicker();
        rsvpTimePicker();

        tinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToContacts();
            }

        });
    }

    public void findViews() {
        switchCompat = (SwitchCompat) findViewById(R.id.switchcompat);
        editText = (EditText) findViewById(R.id.tv_PujaName);
        rsvp_date = (TextView) findViewById(R.id.tv_rsvpDate);
        rsvp_time = (TextView) findViewById(R.id.tv_rsvpTime);
        llrsvpdate = (LinearLayout) findViewById(R.id.ll_rsvpDate);
        llrsvpdate.setOnClickListener(this);
        llrsvptime = (LinearLayout) findViewById(R.id.ll_rsvpTime);
        llrsvptime.setOnClickListener(this);
        rsvp_time = (TextView) findViewById(R.id.tv_rsvpTime);
        fromDate = (TextView) findViewById(R.id.tv_fromDate);
        fromTime = (TextView) findViewById(R.id.tv_fromTime);
        toDate = (TextView) findViewById(R.id.tv_toDate);
        toTime = (TextView) findViewById(R.id.tv_toTime);
        dateStartEvent = (LinearLayout) findViewById(R.id.ll_dateStartEvent);
        dateStartEvent.setOnClickListener(this);
        timeStartEvent = (LinearLayout) findViewById(R.id.ll_timeStartEvent);
        timeStartEvent.setOnClickListener(this);
        enddateEvent = (LinearLayout) findViewById(R.id.ll_enddateEvent);
        enddateEvent.setOnClickListener(this);
        endtimeEvent = (LinearLayout) findViewById(R.id.ll_endtimeEvent);
        endtimeEvent.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        toolbar_text = findViewById(R.id.toolbar1_text);
        toolbar_text.setText("Create Puja");
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        ll_image = findViewById(R.id.linearimg);
        ll_image.setOnClickListener(this);
        ll_category = findViewById(R.id.linearAdd);
        ll_category.setOnClickListener(this);
        iv_backgrnd = findViewById(R.id.iv_BackGrnd);
        addrs = (TextView) findViewById(R.id.tv_Addr);
        tinvite = findViewById(R.id.tv_Invite);
        contactsDisplay = findViewById(R.id.txt_selected_contacts);
        create = (FloatingActionButton) findViewById(R.id.btn_createEvent);
        create.setOnClickListener(this);
        limit = findViewById(R.id.tv_limit);

    }
    public void listeners(){
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid = checkValidations();
                if(valid.equals("success")) {

                    JSONObject jsonObject = null;
                    String serviceUrl = AyyappaConstants.AYYAPPA_CREATE_OR_UPDATEPUJA;
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userId", AyyappaPref.getUserId());
                        jsonObject.put("eventTitle",s1);
                        jsonObject.put("eventImageUrl",imagepath);
                       // jsonObject.put("invitedBy",tinvite.getText().toString());
                       // String s =fromDate.getText().toString() + " " + fromTime.getText().toString();
                        jsonObject.put("eventStartDateTime", convertToUTC(fromDate.getText().toString(),fromTime.getText().toString()+":"+"00"));
                        jsonObject.put("eventEndDateTime", convertToUTC(toDate.getText().toString() , toTime.getText().toString() + ":" + "00"));
                        jsonObject.put("eventLocation", addrs.getText().toString());
                        jsonObject.put("latitude", latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("phoneNumbers", new JSONArray(phoneList));
                        jsonObject.put("rsvpClosingDateTime",convertToUTC(rsvp_date.getText().toString(),rsvp_time.getText().toString()+":"+"00") );
                        jsonObject.put("membersLimit",limit.getText().toString());

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    Log.e("CREATE_NEW_EVENT", jsonObject + "");

                  //addrs.setText(jsonObject.toString());
                    VolleySingleton.getInstance(CreateEvent.this).addToQueueWithJsonRequest(jsonObject, serviceUrl, CreateEvent.this, null);
                    //Toast.makeText(CreateEvent.this, "Sucess", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CreateEvent.this, " "+valid, Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(CreateComm.this, "Enter all Fields", Toast.LENGTH_SHORT).show();
                //checkPermissions();
            }
        });

    }
    public static String convertToUTC(String startDate, String startTime) {
        if (startDate == null || startTime == null || startDate.isEmpty() || startTime.isEmpty())
            return "";
        SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //from.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        //Date date1=null;
        try {
            date = from.parse(startDate + " " + startTime);
            //date1 = from.parse(startTime);
            utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateInUTC = utc_format.format(date);
            //String timeInUTC = utc_format.format(date1);
            System.out.println("GEMINI"+dateInUTC);
            return dateInUTC;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
    public void rsvpDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        rsvpDatePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                rsvp_date.setText(dateFormatter.format(newDate.getTime()));
                System.out.println("FROM DATE" + rsvp_date.getText().toString());

                if (rsvp_date.getText().toString().compareTo(toDate.getText().toString()) > 0 /*||
                        rsvp_date.getText().toString().compareTo(fromDate.getText().toString()) < 0*/)
                {
                    Toast.makeText(getApplicationContext(), "Close Date is invalid", Toast.LENGTH_SHORT).show();
                    rsvp_date.setText("");
                    rsvp_time.setText("");
                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void rsvpTimePicker() {

        //rsvp_time.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        /*mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);*/

        rsvpTimePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                fromHour = hourOfDay;
                fromMinute = minute;

                String min = "";
                String hour = "";
                if (fromHour < 10) {
                    hour = "0" + fromHour;
                } else {
                    hour = String.valueOf(fromHour);
                }
                if (fromMinute < 10) {
                    min = "0" + fromMinute;

                } else {
                    min = String.valueOf(fromMinute);

                }

                String aTime = new StringBuilder().append(hour).append(':')
                        .append(min).toString();
                rsvp_time.setText(aTime);
                if (toDate.getText().toString().compareTo(rsvp_date.getText().toString()) == 0) {
                    if (toTime.getText().toString().compareTo(aTime.trim()) <= 0) {
                        Toast.makeText(CreateEvent.this, "Close Time is invalid", Toast.LENGTH_SHORT).show();
                        rsvp_time.setText("");
                    } else {
                        rsvp_time.setText(aTime);
                    }
                }


                //fromTime.setText(hourOfDay + ":" + minute);
                System.out.println("FROM TIME" + rsvp_time.getText().toString());
            }
        }, mHour, mMinute, false);


    }


    public void fromDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                fromDate.setText(dateFormatter.format(newDate.getTime()));
                //toDate.setText(""); toTime.setText(""); fromTime.setText("");
                //Toast.makeText(getContext(), "Date is invalid", Toast.LENGTH_SHORT).show();
                if (fromDate.getText().toString().compareTo(toDate.getText().toString()) > 0) {
                    //Toast.makeText(getContext(), "Date is invalid", Toast.LENGTH_SHORT).show();
                    toDate.setText("");
                    toTime.setText("");
                    fromTime.setText("");
                }


                System.out.println("FROM DATE" + fromDate.getText().toString());


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void fromTimePicker() {
        Calendar c = Calendar.getInstance();
       /* mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);*/

        fromTimePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                fromHour = hourOfDay;
                fromMinute = minute;

                String min = "";
                String hour = "";
                if (fromHour < 10) {
                    hour = "0" + fromHour;
                } else {
                    hour = String.valueOf(fromHour);
                }
                if (fromMinute < 10) {
                    min = "0" + fromMinute;

                } else {
                    min = String.valueOf(fromMinute);

                }

                String aTime = new StringBuilder().append(hour).append(':')
                        .append(min).toString();
                fromTime.setText(aTime);
                if (fromTime.getText().toString().compareTo(toTime.getText().toString()) > 0) {
                    //Toast.makeText(getContext(), "Date is invalid", Toast.LENGTH_SHORT).show();
                    // toDate.setText("");
                    toTime.setText("");
                }
                System.out.println("FROM TIME" + fromTime.getText().toString());
            }
        }, mHour, mMinute, false);
    }

    public void toDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDate.setText(dateFormatter.format(newDate.getTime()));
                System.out.println("TO DATE" + toDate.getText().toString());

                if (fromDate.getText().toString().compareTo(toDate.getText().toString()) > 0) {
                    Toast.makeText(CreateEvent.this, "End Date is invalid", Toast.LENGTH_SHORT).show();
                    toDate.setText("");
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void toTimePicker() {
        Calendar c = Calendar.getInstance();
       /* mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);*/
        toTimePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              /*  Calendar newDate = Calendar.getInstance();
                newDate.set(hourOfDay, minute);*/
                //toTime.setText(timeFormatter.format(newDate.getTime()));
                toHour = hourOfDay;
                toMinute = minute;
                String min = "";
                String hour = "";

                if (toHour < 10) {
                    hour = "0" + toHour;
                } else {
                    hour = String.valueOf(toHour);
                }
                if (toMinute < 10) {
                    min = "0" + toMinute;
                } else {
                    min = String.valueOf(toMinute);

                }
                // Append in a StringBuilder
               /*String aTime = new StringBuilder().append(toHour).append(':')
                        .append(min).append(" ").append(timeSet).toString();*/
                String aTime = new StringBuilder().append(hour).append(':')
                        .append(min).toString();
                toTime.setText(aTime);
                if (fromDate.getText().toString().compareTo(toDate.getText().toString()) == 0) {
                    if (fromTime.getText().toString().compareTo(aTime.trim()) >= 0) {
                        Toast.makeText(CreateEvent.this, "End Time is invalid", Toast.LENGTH_SHORT).show();
                        toTime.setText("");
                    } else {
                        toTime.setText(aTime);
                    }
                } else {
                    toTime.setText(aTime);
                }
                //toTime.setText(aTime);
                //toTime.setText(hourOfDay + ":" + minute);
                System.out.println("TO TIME" + toTime.getText().toString());
            }
        }, mHour, mMinute, false);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(7, 5)
                .setFixAspectRatio(true)
                .start(this);
    }

     TextWatcher watcher = new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
             valid = checkValidations();
            if(valid.equals("success")){
                create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.orange)}));
                DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.white));

            }
         }
     };

    private String checkValidations() {

        s1 = editText.getText().toString();
        s2 = fromDate.getText().toString();
        s3 = toDate.getText().toString();
        s4 = fromTime.getText().toString();
        s5 = toTime.getText().toString();
        s6 = addrs.getText().toString();

        if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("")) {
            return "Enter Mandatory Fields";
        }
        else{
            create.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.orange)}));
            DrawableCompat.setTint(create.getDrawable(),getResources().getColor(R.color.white));
            return "success";
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                String compressedImagePath = util.compressedImageURI(resultUri.getPath(), CreateEvent.this);

                System.out.println("compressedImagePath : " + compressedImagePath + " Crop Image URI : " + resultUri);

                if (util.isNetworkAvailable()) {
                    pb.setVisibility(View.VISIBLE);
                    ImageUpload imgUpload = new ImageUpload(CreateEvent.this, pb, false, null);

                    if (!imgUpload.callingBlobSuccess(compressedImagePath, "image/jpeg")) {
                        Toast.makeText(getApplicationContext(), "Pic upload failed.", Toast.LENGTH_SHORT).show();
                    }
                    /*Picasso.with(CreateEvent.this)
                            .load(new File(compressedImagePath))
                            .into(iv_backgrnd);*/
                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                return;


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        else if ((resultCode == RESULT_OK) && (requestCode == REQUEST_CODE)) {
            ArrayList<Contact> contacts = (ArrayList<Contact>) data.getSerializableExtra("data");
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
            membersCount = "" + selectedContactsNames.size();
            contactsDisplay.setText(membersCount +" Phone contacts you added");
            phoneList = new ArrayList<>();
            for (int i = 0; i < selectedContactsNames.size(); i++) {
                phoneList.add(selectedContactsNames.get(i).getNumber());
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
    }
    private void checkPermissions() {

        CropImage.startPickImageActivity(CreateEvent.this);

    }

    @Override
    public void onClick(View view) {

       switch (view.getId()){
           case R.id.linearAdd:
               PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
               try {
                   startActivityForResult(builder.build(this), PLACE_PICK_REQ);
               } catch (GooglePlayServicesRepairableException e) {
                   e.printStackTrace();
               } catch (GooglePlayServicesNotAvailableException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.linearimg:
               ll_image.setClickable(false);
               new Handler().postDelayed(new Runnable() {
                   public void run() {
                       ll_image.setClickable(true);
                   }
               }, 2000);
               checkPermissions();
               break;


           case R.id.back:
               onBackPressed();
               break;
           case R.id.btn_createEvent:
               break;
           case R.id.ll_rsvpDate:
               if (fromDate.getText().toString().trim().isEmpty() || fromTime.getText().toString().trim().isEmpty()||
                       toDate.getText().toString().trim().isEmpty()|| toTime.getText().toString().trim().isEmpty()) {
                   Toast toast = Toast.makeText(CreateEvent.this, "Please enter Event Timings", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                   toast.show();
               }
               else
                   rsvp_date.setVisibility(View.VISIBLE);
               rsvpDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
               rsvpDatePickerDialog.show();
               break;

           case R.id.ll_rsvpTime:
               if (rsvp_date.getText().toString().trim().isEmpty()) {
                   Toast toast = Toast.makeText(CreateEvent.this, "Please enter close Date", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                   toast.show();
               }
               else
                   rsvp_time.setVisibility(View.VISIBLE);
               rsvpTimePickerDialog.show();

               break;

           case R.id.ll_dateStartEvent:
               fromDate.setVisibility(View.VISIBLE);
               fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
               fromDatePickerDialog.show();
               break;
           case R.id.ll_timeStartEvent:
               if (fromDate.getText().toString().trim().isEmpty()) {
                   Toast toast = Toast.makeText(CreateEvent.this, "Please enter Start Date", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                   toast.show();
               }
               else
                   fromTime.setVisibility(View.VISIBLE);
               fromTimePickerDialog.show();

               break;

           case R.id.ll_enddateEvent:
               if (fromDate.getText().toString().trim().isEmpty() || fromTime.getText().toString().trim().isEmpty()) {
                   Toast toast = Toast.makeText(CreateEvent.this, "Please enter start date and start time", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                   toast.show();
               }
               else
                   toDate.setVisibility(View.VISIBLE);
               toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
               toDatePickerDialog.show();
               break;
           case R.id.ll_endtimeEvent:
               if (toDate.getText().toString().trim().isEmpty()) {
                   Toast toast = Toast.makeText(CreateEvent.this, "Please enter End Date", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                   toast.show();
               }
               else
                   toTime.setVisibility(View.VISIBLE);
               toTimePickerDialog.show();
               break;

       }

    }

    public void goToContacts() {
        Intent intent = new Intent(CreateEvent.this, ContactsActivity.class);
        intent.putExtra(AyyappaConstants.SELECTED_CONTACTS, selectedContactsNames);
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


    }

    @Override
    public void imageUploadSuccess(String azureURL) {
        imagepath = azureURL;
        Log.e("image path is ", imagepath + "");
        Toast.makeText(getApplicationContext(), "Image has been uploaded.", Toast.LENGTH_LONG).show();
        Picasso.with(CreateEvent.this)
                .load(azureURL)
                .into(iv_backgrnd);

        pb.setVisibility(View.GONE);


    }

    @Override
    public void updateFromVolley(Object result) {
        System.out.println("Create Event::" + result);
        if (result instanceof JSONObject) {

            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        //JSONArray data = ((JSONObject) result).getJSONArray("data");
                        //System.out.println("Login Data" + data.toString());
                        System.out.println("in status code 1::" + result);
                        Toast.makeText(CreateEvent.this, "Event Created", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(CreateEvent.this,NearByPuja.class);
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
