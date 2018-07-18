/**
 * Copyright Microsoft Corporation
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anvesh.saranamayyappa.network;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ProgressBar;

import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.utils.ImageUpload;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * This sample illustrates basic usage of the various Blob Primitives provided
 * in the Storage Client Library including CloudBlobContainer, CloudBlockBlob
 * and CloudBlobClient.
 */
public class BlobUploadTask extends AsyncTask<String, Integer, Boolean> {
    //String cloudUrl = AyyappaApplication.getEnvSettings().getImage_url();
    String cloudUrl = "https://fankickdev.blob.core.windows.net/images/";
    private Context ctxt;
    private Fragment fragment;
    String path = "", filename = "";
    ProgressBar pb;
    String contentType = "";
    int count = 0;
    public static boolean isUploadstarted = false;
    int responseCode;

    public BlobUploadTask(Context act, String path, String filename,
                          ProgressBar pb, String contentType, Fragment fragment) {
        this.ctxt = act;
        this.filename = filename;
        this.path = path;
        this.pb = pb;
        this.contentType = contentType;
        this.fragment = fragment;
        Log.d("in pre execute", contentType);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (pb != null) {
            pb.setProgress(5);
        }
        isUploadstarted = true;
    }

    @Override
    protected Boolean doInBackground(String... arg0) {

        try {
            checkTokenAvialable();

            String token = getSasToken();
            Log.d("tokenin upload", token);

            Log.e("cloudUrl + filename ", cloudUrl + filename);
            cloudUrl = cloudUrl + filename;

            System.out.println("in blob uplaod clouDurl" + cloudUrl);
            HttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(cloudUrl + "?" + token);
            Log.e("img link ", "..." + cloudUrl + "?" + token);
            put.addHeader("Content-Type", contentType);
            put.addHeader("x-ms-blob-type", "BlockBlob");

            put.setEntity(new ByteArrayEntity(readContentIntoByteArray(new File(path))));

            HttpResponse response = client.execute(put);
            String result = EntityUtils.toString(response.getEntity());
            responseCode = response.getStatusLine().getStatusCode();

            System.out.println("in rescode" + responseCode);

            System.out.println("in response" + result);

            return false;
        } catch (Exception t) {
            t.printStackTrace();
            return false;
        }

       /* try {
            //checkTokenAvialable();

            //String token = getSasToken();
            //Log.d("tokenin upload", token);

            Log.e("cloudUrl + filename ", cloudUrl + filename);
            cloudUrl = cloudUrl + filename;

            System.out.println("in blob uplaod clouDurl" + cloudUrl);
            HttpClient client = new DefaultHttpClient();
            //HttpPut put = new HttpPut(cloudUrl + "?" + token);
            HttpPut put = new HttpPut(cloudUrl);
            //Log.e("img link ","..."+cloudUrl + "?" + token);
            //put.addHeader("Content-Type", contentType);
            //put.addHeader("x-ms-blob-type", "BlockBlob");

            put.setEntity(new ByteArrayEntity(readContentIntoByteArray(new File(path))));

            HttpResponse response = client.execute(put);
            String result = EntityUtils.toString(response.getEntity());
            responseCode = response.getStatusLine().getStatusCode();

            System.out.println("in rescode" + responseCode);

            System.out.println("in response" + result);

            return false;
        } catch (Exception t) {
            t.printStackTrace();
            return false;
        }*/
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (checkResponseStatus()) {
            if (pb != null) {
                pb.setProgress(100);
                pb.setProgress(0);
            }
            System.out.println("SHRUTHI onPostExecute "+ fragment+" "+ responseCode);
            if (ctxt != null && responseCode == 201 && fragment == null) {
                ((ImageUpload.ImageUploadSuccess) ctxt).imageUploadSuccess(cloudUrl);
            } else if (fragment != null && responseCode == 201) {
                ((ImageUpload.ImageUploadSuccess) fragment).imageUploadSuccess(cloudUrl);
            }

            isUploadstarted = false;
        }
    }

    boolean checkResponseStatus() {
        if (responseCode == 201) {
            Log.d("uploading ", "success");
            return true;
        } else {
            Log.d("count is ", count + "");
            if (count < 2) {
                count++;

                Log.d("uploading ", "failed");
                Log.d("uploading ", "trying again");

                BlobUploadTask task = new BlobUploadTask(ctxt, path, filename, pb, contentType, fragment);
                task.execute("");
            }
        }
        return false;
    }

    private byte[] readContentIntoByteArray(File file) {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            for (int i = 0; i < bFile.length; i++) {
                //  System.out.print((char) bFile[i]);
//                publishProgress(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        System.out.print("in progress");
        pb.setProgress(values[0]);
    }

    boolean checkTokenAvialable() {

       /* genrateSasToken();
        return false;*/

        String token = getSasToken();
//        String token = "";
        String et = getsasExpiryTime();

        if (token == "" || token == null || et == "" || et == null || et.equals("")) {
            Log.e("token details empty", et);
            genrateSasToken();
            return false;

        } else {
            Log.d("token already availeble", getSasToken());
            if (isTokenExpired(getsasExpiryTime())) {
                Log.e("token expired", token);
                genrateSasToken();
                return false;
            } else {
                Log.e("token not  expired", et);
                return true;
            }
        }
    }

    boolean isTokenExpired(String dateString) {
        System.out.println("FANKICK dateString " + dateString);
        boolean flag = false;
        long ts = System.currentTimeMillis();
        Date localTime = new Date(ts);
        String format = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        // Convert Local Time to UTC (Works Fine)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date localTimeInUTC = new Date(sdf.format(localTime));
        String myName = dateString;
        char[] myNameChars = myName.toCharArray();

        System.out.println("char is" + myNameChars + "::" + myName);
        if (myNameChars.length > 0) {
            myNameChars[10] = ' ';
            myNameChars[19] = ' ';

            myName = String.valueOf(myNameChars);
        }
        String string = myName;
        System.out.println("String to formatted " + myName);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        Date tokenTimeInUTC = null;
        try {
            tokenTimeInUTC = format1.parse(string);
            System.out.println(tokenTimeInUTC);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (tokenTimeInUTC.after(localTimeInUTC)) {

            flag = false;
            long diffInMillies = tokenTimeInUTC.getTime() - localTimeInUTC.getTime();
            List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
            Collections.reverse(units);
            Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
            long milliesRest = diffInMillies;
            for (TimeUnit unit : units) {
                long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
                long diffInMilliesForUnit = unit.toMillis(diff);
                milliesRest = milliesRest - diffInMilliesForUnit;
                result.put(unit, diff);
            }
            System.out.println("Shruthi UTC " + tokenTimeInUTC.toString() + " Local " + localTimeInUTC.toString() + " Diff " + result);
        } else {
            flag = true;
            System.out.println("Token Expired");
        }
        return flag;
    }

    public String getSasToken() {
        return AyyappaPref.getSasAccessToken();
    }

    public String getsasExpiryTime() {
        return AyyappaPref.getSasTokenExpiryTime();
    }

    public String genrateSasToken() {
        String response = null;
        JSONObject reqObject = new JSONObject();
        Log.e("request", reqObject.toString());
        URL url = null;
        try {
            url = new URL("https://dev.fankick.io/rest/" + AyyappaConstants.GENERATE_SAS_TOKEN_WS_EP);
            Log.e("requesturl", url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type",
                    "application/json");
            InputStream ios = conn.getInputStream();
            int i = 0;
            char c;
            StringBuffer buffer = new StringBuffer();
            while ((i = ios.read()) != -1) {
                c = (char) i;
                buffer.append(c);
            }
            response = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            updateData(new JSONObject(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public void saveSasTokenDetails(String sasToken, String expireDate) {
        AyyappaPref.saveSasAccessToken(sasToken);

        AyyappaPref.saveSasTokenExpiryTime(expireDate);
    }

    public void updateData(JSONObject resultJson) {
        String sasToken = "";
        String expiresIn = "";

        Log.e("response", resultJson.toString());

        try {
            expiresIn = resultJson.has("expiryTime") ? resultJson
                    .getString("expiryTime") : "";

            sasToken = resultJson.has("token") ? resultJson
                    .getString("token") : null;
            Log.e("Tokenm", "" + sasToken);
            Log.i("new token genrated", "This is the access Token: "
                    + sasToken + ". It will expires in "
                    + expiresIn + " secs");
            saveSasTokenDetails(sasToken, expiresIn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}