package com.anvesh.saranamayyappa.utils;

import android.app.Activity;
import android.content.Context;

import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.Uri;
import android.provider.Contacts;
import android.text.TextUtils;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaApplication;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import id.zelory.compressor.Compressor;

public class util {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AyyappaApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static String compressedImageURI(String filePathCompressed, Context context) {

        Log.d("actual file path ", filePathCompressed);
        File file = new File(filePathCompressed);
        File compressedImageFile = Compressor.getDefault(context).compressToFile(file);
        filePathCompressed = compressedImageFile.getPath();


      /*  File compressedImageFile = new Compressor.Builder(context)
                .setMaxWidth(640)
                .setMaxHeight(640)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .build()
                .compressToFile(file);
        filePathCompressed = compressedImageFile.getPath();
        Log.d("compressed file path ", filePathCompressed);*/

        return filePathCompressed;
    }


    public static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }


    public static void hideKeyboard(Activity activity) {

        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showMessage(Context context, String message) {

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public static String convertFromUTCWithTime(String formatString) {

        if (TextUtils.isEmpty(formatString)) {
            return " ";
        }

        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//2017-04-25 14:13:43
        SimpleDateFormat to = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); //Feb 21, 2017
        utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utc_format.parse(formatString);
            Log.e("dateInUTC", " " + utc_format.format(date));
            utc_format.setTimeZone(TimeZone.getDefault());
            String formattedDate = utc_format.format(date);
            String dateInLocal = to.format(date).replace("am", "AM").replace("pm", "PM");
            Log.e("dateInLocal", " " + dateInLocal);
            return dateInLocal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return " ";
    }

    public static String displayOnlyDate(String displayDate) {
        if (TextUtils.isEmpty(displayDate)) {
            return " ";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date date = null;
        try {
            date = simpleDateFormat.parse(displayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
        DateFormat formatter = new SimpleDateFormat("MMM d");
        String dateStr = formatter.format(date);
        return dateStr;
    }

    public static String displayOnlyTime(String displayTime) {
        if (TextUtils.isEmpty(displayTime)) {
            return " ";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date date = null;
        try {
            date = simpleDateFormat.parse(displayTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
      DateFormat formatter = new SimpleDateFormat("hh:mm a");
        String dateStr = formatter.format(date).replace("am", "AM").replace("pm", "PM");
        return dateStr;
    }


    public static String convertFromUTCToMonth(String formatString) {
        if (null == formatString || formatString.isEmpty()) {
            return "";
        }
//        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd ");//2017-04-24 09:41:40.0
        utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = utc_format.parse(formatString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String dayNumberSuffix = getDayNumberSuffix(day);
            SimpleDateFormat to = new SimpleDateFormat("MMM dd'" + dayNumberSuffix + " " + "'yyyy ");
            String dateInLocal = to.format(date);
            return dateInLocal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertFromUTCToMonthWithOutYear(String formatString) {

        if (null == formatString || formatString.isEmpty()) {
            return "";
        }

        SimpleDateFormat to = new SimpleDateFormat("MMM dd_hh:mm a");
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utc_format.parse(formatString);
            String dateInLocal = to.format(date);
            return dateInLocal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertFromUTC(String formatString) {

        if (null == formatString || formatString.isEmpty()) {
            return "";
        }

        SimpleDateFormat to = new SimpleDateFormat("MMM dd, yyyy");//Feb 21, 2017
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2017-04-25 14:13:43
        utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utc_format.parse(formatString);
            String dateInLocal = to.format(date);
            return dateInLocal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*public static String convertFromUTCWithTime(String formatString) {

        if (TextUtils.isEmpty(formatString)) {
            return " ";
        }
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//2017-04-25 14:13:43
        SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd"); // 21 Feb, 2017
        utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utc_format.parse(formatString);
            Log.e("dateInUTC", " " + utc_format.format(date));
            utc_format.setTimeZone(TimeZone.getDefault());
            String formattedDate = utc_format.format(date);
            String dateInLocal = to.format(date);
            Log.e("dateInLocal", " " + dateInLocal);
            return dateInLocal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return " ";
    }*/

    public static String convertFromUTCWithTime1(String formatString) {

        if (TextUtils.isEmpty(formatString)) {
            return " ";
        }
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//2017-04-25 14:13:43
        SimpleDateFormat to = new SimpleDateFormat("hh:mm"); // 21 Feb, 2017
        utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utc_format.parse(formatString);
            Log.e("dateInUTC", " " + utc_format.format(date));
            utc_format.setTimeZone(TimeZone.getDefault());
            String formattedDate = utc_format.format(date);
            String dateInLocal = to.format(date);
            Log.e("dateInLocal", " " + dateInLocal);
            return dateInLocal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return " ";
    }


    /**
     * Parse string to date date.
     *
     * @param formatDateString the format date string
     * @return the date
     */
    public static Date parseStringToDate(String formatDateString) {
        SimpleDateFormat utc_format = new SimpleDateFormat("dd/MM/yyyy ");
        Date date = null;
        try {
            date = utc_format.parse(formatDateString);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
        return date;
    }


    public static String convertToDate(long time) {
//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
//        cal.setTimeInMillis(time);
//        String date = DateFormat.format("MMM dd, yyyy @ hh:mm aa", cal).toString();


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        Date date1 = cal.getTime();
        Log.d("convertToDate", time + " " + date1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy @ hh:mm aa");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String date =dateFormat.format(cal.getTime());

        String dateInUTC = dateFormat.format(date1);

        return dateInUTC;
    }

    public static String convertToUTC(String startDate, String startTime) {
        //Log.d(TAG, startDate + startTime + "");
        if (startDate == null || startTime == null || startDate.isEmpty() || startTime.isEmpty())
            return "";
        SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //from.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        //Date date1=null;
        try {
            date = from.parse(startDate + " " + startTime);
            //date1 = from.parse(startTime);

            utc_format.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateInUTC = utc_format.format(date);
            //String timeInUTC = utc_format.format(date1);

            return dateInUTC;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static int getRandomColor(String name) {
        if (name == null) {
            name = "xyz";
        }
        int chosenColor = 0;
        int[] colors = AyyappaApplication.getInstance().getResources().getIntArray(R.array.categorycolors);

//        int colors[] = new int[]{0xFF2093CD, 0xFFF16364, 0xFF67BF74, 0xFF59A2BE};

        int colourToChoose = name.hashCode() % colors.length;
        if (colourToChoose >= 0) {
            chosenColor = colors[colourToChoose];
        } else {
            Log.d("colourToChoose ", colourToChoose + "");
            chosenColor = colors[-colourToChoose];
        }
        return chosenColor;
    }

    public static String getContactName(Context context, final String phoneNumber) {

        if (phoneNumber == null || phoneNumber.trim().isEmpty())
            return "";

        if (phoneNumber.equals(AyyappaPref.getUserMobileNum())) {
            Log.d("You", phoneNumber);
            return "You";
        }

        Uri uri;
        String contactName = "";
        String[] projection;

        try {
            Uri mBaseUri = Contacts.Phones.CONTENT_FILTER_URL;
            projection = new String[]{Contacts.People.NAME};

            Class<?> c = Class.forName("android.provider.ContactsContract$PhoneLookup");
            mBaseUri = (Uri) c.getField("CONTENT_FILTER_URI").get(mBaseUri);
            projection = new String[]{"display_name"};

            uri = Uri.withAppendedPath(mBaseUri, Uri.encode(phoneNumber));
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            } else
                return phoneNumber;

            cursor.close();
            cursor = null;
        } catch (Exception e) {
            return phoneNumber;
        }

        return contactName;
    }

    public static String validateUsing_libPhoneNumber(String countryCode, String phNumber) {

        try {

            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode.replaceAll("[-+.^:,]", "")));
            Phonenumber.PhoneNumber phoneNumber = null;

            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            if (isValid) {
                String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                //Log.d(TAG, "Phone Number is Valid " + phoneNumber);
                return "+" + phoneNumber.getCountryCode() + phoneNumber.getNationalNumber();
            } else {
                //Log.d(TAG, "Phone Number is Invalid " + phoneNumber);
                return null;
            }
        } catch (NumberParseException e) {
            System.err.println(e);
            e.printStackTrace();

        }
        return null;
    }


    public static class ServiceUrlGenerator {
        StringBuilder serviceUrl;
        boolean isFirst;

        public ServiceUrlGenerator(String serviceEndPoint) {
            serviceUrl = new StringBuilder(serviceEndPoint);
            isFirst = true;
        }

        public void addKeyValue(String key, String value) {
            if (isFirst) {
                serviceUrl.append("?");
                isFirst = false;
            } else {
                serviceUrl.append("&");
            }
            serviceUrl.append(key);
            serviceUrl.append("=");
            serviceUrl.append(value);
        }

        public String getServiceUrl() {
            return serviceUrl.toString();
        }
    }

}
