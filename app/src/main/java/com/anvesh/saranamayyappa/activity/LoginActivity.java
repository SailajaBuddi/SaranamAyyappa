package com.anvesh.saranamayyappa.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.network.VolleySingleton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements UpdateVolleyData, View.OnClickListener {

    public static int APP_REQUEST_CODE = 1;
    Button button;
    LoginButton loginButton;
    CallbackManager callbackManager;
    FrameLayout loginfb;
    String type;
    String mobilenum = "";
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        findView();
        AccountKit.initialize(getApplicationContext(), new AccountKit.InitializeCallback() {
            @Override
            public void onInitialized() {
            }
        });
    }
    public void findView() {
        button = (Button) findViewById(R.id.btn_MobileNo);
        button.setOnClickListener(this);
       /* loginfb = (FrameLayout) findViewById(R.id.fb);
        loginfb.setOnClickListener(this);*/
    }
    private void fbinitialize() {
        //Toast.makeText(LoginActivity.this, "LOGIN INTO FACEBOOK", Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Toast.makeText(LoginActivity.this, "LOGIN  FACEBOOK", Toast.LENGTH_SHORT).show();
                        final String token = loginResult.getAccessToken().getToken();
                        String id = loginResult.getAccessToken().getUserId();
                        Log.e("ID", id);
                        Log.e("AccessToken", loginResult.getAccessToken().getToken());
                        fbLoginService(AyyappaConstants.FB_TYPE, id, token);
                        Intent intent = new Intent(getApplicationContext(), SignUp.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onCancel() {
                    }
                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }


    private void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage = "";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                //finish();
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    //finish();
                    getUserAccount(loginResult.getAccessToken().getToken());
                    showProgressDialog();
                   /* progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show(LoginActivity.this,"","Loading...");*/
                    //finish();
                }
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this,R.style.MyAlertDialogStyle);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage("Loading..");
        progressBar = (ProgressBar)findViewById(R.id.circular_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
      /*  progressBar.setBackgroundColor(getResources().getColor(R.color.blue));*/
        progressBar.getProgress();
        progressDialog.show();

    }

    private void getUserAccount(final String token) {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();
                System.out.println("accountKitId " + accountKitId);
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();
                //System.out.println("Order Phone No " + phoneNumberString);
                mobilenum = phoneNumber.toString();
                AyyappaPref.saveUserMobileNum(phoneNumberString);
                //AyyappaPref.saveCountryCode("+" + phoneNumber.getCountryCode());
                //AyyappaPref.saveUserId("5a61c5a426775a260e84b108");
                System.out.println("GET Phone" + AyyappaPref.getUserMobileNum());
                System.out.println("GET USER ID" + AyyappaPref.getUserId());
                fbLoginService(AyyappaConstants.MOBILE_Type, token, "");
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                Log.e("AccountKit", accountKitError.toString());
                // Handle Error
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_MobileNo:
                type = AyyappaConstants.MOBILE_Type;
                phoneLogin();
                break;
         /*   case R.id.fb:
                type = AyyappaConstants.FB_TYPE;
                fbinitialize();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                break;
*/
        }

    }

  /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }*/


    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS}, AyyappaConstants.PERMISSIONS_REQUEST_RC_RS_RP);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
                Log.d("Already have ", "Permissions");
                phoneLogin();
            }
        } else {
            Log.d("No Need To Take ", "Permissions");
            phoneLogin();
        }
    }


    public void fbLoginService(String type, String mobileNumber, String token) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();

            jsonObject.put("type", type);
            jsonObject.put("mobileNumber", mobileNumber);
            if (type.equalsIgnoreCase(AyyappaConstants.FB_TYPE)) {
                jsonObject.put("token", token);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("jsonObject req ", "" + jsonObject);

        VolleySingleton.getInstance(LoginActivity.this).addToQueueWithJsonRequest(jsonObject, AyyappaConstants.AYYAPPA_USER_LOGINMFB, LoginActivity.this, null);

        System.out.println("Login Service" + jsonObject);


    }

    @Override
    public void updateFromVolley(Object response, int resultCode) {   }

    @Override
    public void updateFromVolley(Object result) {
        System.out.println("in status code 1::" + ((JSONObject) result));
        if (result instanceof JSONObject) {

            if (((JSONObject) result).has(AyyappaConstants.STATUS_CODE)) {
                try {
                    if (((JSONObject) result).getInt(AyyappaConstants.STATUS_CODE) == 1) {
                        updateLoginStatus();
                        JSONObject data = ((JSONObject) result).getJSONObject("data");
                        System.out.println("Login Data" + data.toString());
                        System.out.println("in status code 1::" + ((JSONObject) result));
                        AyyappaPref.saveUserId(data.getString("userId"));
                        System.out.println("USERID" + AyyappaPref.getUserId());
                        //Log.e("UserId" ,"AyyappaPref.getUserId()" );
                        //progressDialog.dismiss();
                       Intent intent = new Intent(this, SignUp.class);
                        startActivity(intent);
                        finish();
                    } else {
                        System.out.println("in status code else ::" + ((JSONObject) result));

                        //  Util.hideRefreshProgress(mSwipeRefreshLayout);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    public void updateLoginStatus() {

        Log.e("updateLoginStatus", "123");

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(mobilenum, "ZZ");
            AyyappaPref.saveCountryCode("+" + numberProto.getCountryCode());
            Log.e("country code is ", numberProto.getCountryCode() + "");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        AyyappaPref.saveLoginStatus(true);
    }

  /*  @Override
    public void onBackPressed() {
        *//*moveTaskToBack(true);*//*
        super.onBackPressed();
        finish();
    }*/

}
