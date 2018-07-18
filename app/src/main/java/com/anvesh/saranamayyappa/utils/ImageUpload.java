package com.anvesh.saranamayyappa.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;


import com.anvesh.saranamayyappa.network.BlobUploadTask;

import java.util.UUID;



public class ImageUpload {

    private Context context;
    private Fragment fragment;
    private ProgressBar pBar;
    int chooserFileType;
    private boolean profile = false;


    public interface ImageUploadSuccess {
        void imageUploadSuccess(String azureURL);
    }

    public ImageUpload(Context context, ProgressBar progressBar, boolean profile, Fragment fragment) {
        this.context = context;
        this.pBar = progressBar;
        this.profile = profile;
        this.fragment = fragment;
    }
   /* public ImageUpload(Fragment fragment, ProgressBar progressBar, boolean profile) {
        this.fragment = fragment;
        this.pBar = progressBar;
        this.profile = profile;
    }*/


    public boolean callingBlobSuccess(String path, String type) {
        String name = null;
        if (!profile) {
            UUID idOne = UUID.randomUUID();
            name = idOne.toString();
        }

        BlobUploadTask task = new BlobUploadTask(context, path, name, pBar, type, fragment);
        boolean flag = false;
        try {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            Log.d("upload last", "uploaded" + ":" + name + "type:" + type + "path:" + path);
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return flag;
    }
}
