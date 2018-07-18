package com.anvesh.saranamayyappa.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.anvesh.saranamayyappa.model.EnvironmentSettings;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AyyappaApplication extends MultiDexApplication {


    public static final int DEV = 0;
    public static final int QA = 1;
    public static final int PROD = 2;

    public static int env = DEV;
   /* public static int env = QA;
    public static int env = PROD;*/

    private static EnvironmentSettings envSettings;
    private static AyyappaApplication currentApplication = null;
    public static int windowHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        currentApplication = this;

        loadEnvironmentValues();
        setOrientationPortraitForALlActivities();
        windowManager();

    }

    public static AyyappaApplication getInstance() {
        return currentApplication;
    }

    public static EnvironmentSettings getEnvSettings() {
        return envSettings;
    }

    private void windowManager() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        windowHeight = display.getHeight();
    }


    private void loadEnvironmentValues() {
        Log.v("loadEnvironmentValues", "loadEnvironmentValues=");
        try {
            String settingsFile = null;
            switch (env) {
                case DEV:
                    settingsFile = "app_settings_dev";
                    break;
                case QA:
                    settingsFile = "app_settings_qa";
                    break;
                case PROD:
                    settingsFile = "app_settings_prod";
                    break;
            }
            InputStream inputStream = this.getAssets().open(settingsFile);
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            envSettings = gson.fromJson(jsonReader, EnvironmentSettings.class);

            Log.d("URL", getEnvSettings().getBase_url());

        } catch (IOException e) {
            Log.d("EnvironmentSettings", "Error loading env values", e);
        }
    }

    private void setOrientationPortraitForALlActivities() {

        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // new activity created; make its orientation to portrait
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


  /*  public static final int DEV = 0;
    public static final int QA = 1;
    public static final int PROD = 2;

    public static int env = DEV;

   // private static EnvironmentSettings envSettings;

    private static AyyappaApplication currentApplication = null;

    LinkedHashSet<MenuList> menuLists;

    public int windowWidth;

    //    ArrayList<MenuList> menuLists;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        MultiDex.install(this);
        currentApplication = this;

//        menuLists = new ArrayList<>();
        menuLists = new LinkedHashSet<>();

        loadEnvironmentValues();

        setOrientationPortraitForALlActivities();

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        windowWidth = size.x;

        sAnalytics = GoogleAnalytics.getInstance(this);


    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AyyappaApplication getInstance() {
        return currentApplication;
    }

    private void loadEnvironmentValues() {
        Log.v("loadEnvironmentValues", "loadEnvironmentValues=");
        try {
            String settingsFile = null;
            switch (env) {
                case DEV:
                    settingsFile = "app_settings_dev";
                    break;
                case QA:
                    settingsFile = "app_settings_qa";
                    break;
                case PROD:
                    settingsFile = "app_settings_prod";
                    break;
            }
            InputStream inputStream = this.getAssets().open(settingsFile);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
           // envSettings = gson.fromJson(jsonReader, EnvironmentSettings.class);



        } catch (IOException e) {
            Log.d("EnvironmentSettings", "Error loading env values", e);
        }
    }

 *//* // public static EnvironmentSettings getEnvSettings() {
        return envSettings;
    }*//*

    private void setOrientationPortraitForALlActivities() {

        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // new activity created; make its orientation to portrait
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

    }

    public void setSection(String sectionId, String sectionDisplayName) {
        menuLists.add(new MenuList(sectionId, sectionDisplayName));
    }

    public void setSection(String sectionId, String sectionDisplayName, String serviceUrl) {
        menuLists.add(new MenuList(sectionId, sectionDisplayName, serviceUrl));
    }

    *//*
        public ArrayList<MenuList> getSections() {
            return menuLists;
        }*//*
    public LinkedHashSet<MenuList> getSections() {
        return menuLists;
    }

    public void clearSections() {
        if (menuLists != null) {
            menuLists.clear();
        }
    }

    public static boolean isConnectingToInternet() {
        ConnectivityManager connecivity = (ConnectivityManager) AyyappaApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connecivity != null) {
            NetworkInfo[] info = connecivity.getAllNetworkInfo();
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void share(int id, Context context, int from) {
        System.out.println("inside share");
        String url = "", sharetype = "";
        if (from == 0) {
            //0 Events related share
            sharetype = "event";
            url = AyyappaConstants.SHARE_EVENT + id;
        }  else if (from == 1) {
            //2 Message Centre related share
            sharetype = "msgcenter";
            url = AyyappaConstants.SHARE_MESSAGE + id;
        }
        System.out.println("share url " + url);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                url);
        sendIntent.setType("text/plain");
        //sendIntent.setType("image/jpg");
        //  context.startActivity(sendIntent);
        context.startActivity(Intent.createChooser(sendIntent, ""));
        //Call share service
        // VolleySingleton.getInstance(FKickApplication.getInstance()).addToQueueWithStringRequest(FKickConstants.SHARE_COINS + "?" + FKickConstants.userId_PARAM + "=" + FanKickPref.getUserId() + "&" + FKickConstants.shareId_PARAM + "=" + id + "&" + FKickConstants.SHARE_TYPE + "=" + sharetype, FKickApplication.getInstance(), null);
    }


    public static void shareToSocialMedia(String id, Context context, int from) {
        System.out.println("inside share");
        String url = "", sharetype = "";
        if (from == 0) {
            //0 Events related share
            sharetype = "event";
            url = AyyappaConstants.SHARE_EVENT + id;
        } else if (from == 3) {
            //2 Message Centre related share
            sharetype = "msgcenter";
            url = AyyappaConstants.SHARE_MESSAGE + id;
        } else if (from == 4) {
            //2 Message Centre related share
            sharetype = "fanClub";
            url = AyyappaConstants.EVENT + id;
        }
        System.out.println("share url " + url);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, ""));
    }

    public static void inviteApp(String referralCode, Context context) {
        String url = "";
        url = AyyappaConstants.APP_INVITE + referralCode;
        Log.e("inviteApp url","..."+url);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                url);
        sendIntent.setType("text/plain");
        //sendIntent.setType("image/jpg");
        //  context.startActivity(sendIntent);
        context.startActivity(Intent.createChooser(sendIntent, ""));

    }


    public static String displayDateInFormat(String text) {

        Log.e("text", "..." + text);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        simpleDateFormat.setTimeZone(utcZone);

        Date myDate = null;
        try {
            myDate = simpleDateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String formattedDate = simpleDateFormat.format(myDate);

        Log.e("Time: ", formattedDate);

        StringTokenizer st1 = new StringTokenizer(formattedDate, " ");
        StringTokenizer st2 = new StringTokenizer(st1.nextToken(), "-");
        StringTokenizer st3 = new StringTokenizer(st1.nextToken(), ":");

        int year = Integer.parseInt(st2.nextToken());
        int month = Integer.parseInt(st2.nextToken());
        int date1 = Integer.parseInt(st2.nextToken());

        String timeStr = st3.nextToken();
        String minStr = st3.nextToken();

        Log.e("timeStr", "..." + timeStr);


        int time = Integer.parseInt(timeStr);

        if (new Date().getMonth() == (month - 1) && new Date().getDate() == date1) {

            if (time > 11) {
                if (time > 12)
                    time = time - 12;

                Log.e("time", "..." + time);

                return time + ":" + minStr + " PM";
            } else {
                return timeStr + ":" + minStr + " AM";
            }

        }

        String stringDate = util.convertFromUTC(text);

        st1 = new StringTokenizer(stringDate, ",");

        if (new Date().getYear() == (year - 1900)) {
            return st1.nextToken();
        }

        Log.e("noti Date", "..." + stringDate);

        return stringDate;
    }

    @Override
    public void updateFromVolley(Object result) {
        //Share service response
        System.out.println("result " + result);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

*/
}

