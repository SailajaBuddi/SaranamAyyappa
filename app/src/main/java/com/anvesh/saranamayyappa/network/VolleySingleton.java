package com.anvesh.saranamayyappa.network;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anvesh.saranamayyappa.Interface.UpdateVolleyData;
import com.anvesh.saranamayyappa.app.AyyappaApplication;

import org.json.JSONObject;

/**
 * Created by sbogi on 3/13/2017.
 */

public class VolleySingleton {

    private static VolleySingleton instance;
    private static RequestQueue requestQueue;
    public static int MY_SOCKET_TIMEOUT_MS = 30000;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(AyyappaApplication.getInstance());
        }
        return requestQueue;
    }

    public static VolleySingleton getInstance(Context context) {

        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public void addToQueueWithJsonRequest(JSONObject reqObject, String webService, final Context context, final Fragment fragment) {
        int req;

        if (reqObject != null) {
            System.out.println("request before req>>>>>" + reqObject);
            req = Request.Method.POST;
        } else {
            req = Request.Method.GET;
        }
        RequestQueue queue = requestQueue;

        System.out.println("server url before req>>>>>" + webService);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                req, webService, reqObject,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("response<<<<<" + response);
                        if (fragment != null) {
                            ((UpdateVolleyData) fragment).updateFromVolley(response);
                        } else {
                            ((UpdateVolleyData) context).updateFromVolley(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("response in error<<<<<" + error);
                if (fragment != null) {
                    ((UpdateVolleyData) fragment).updateFromVolley(error);
                } else {
                    ((UpdateVolleyData) context).updateFromVolley(error);
                }
            }
        });

        queue.add(jsonRequest);
    }

    public void addToQueueWithJsonRequestAndResultCode(JSONObject reqObject, String webService, final Context context, final Fragment fragment, final int resultCode) {
        int req;

        if (reqObject != null) {
            System.out.println("request before req>>>>>" + reqObject);
            req = Request.Method.POST;
        } else {
            req = Request.Method.GET;
        }
        RequestQueue queue = requestQueue;

        System.out.println("server url before req>>>>>" + webService);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                req, webService, reqObject,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("response<<<<<" + response);
                        if (fragment != null) {
                            ((UpdateVolleyData) fragment).updateFromVolley(response,resultCode);
                        } else {
                            ((UpdateVolleyData) context).updateFromVolley(response,resultCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("response in error<<<<<" + error);
                if (fragment != null) {
                    ((UpdateVolleyData) fragment).updateFromVolley(error,resultCode);
                } else {
                    ((UpdateVolleyData) context).updateFromVolley(error,resultCode);
                }
            }
        });

        queue.add(jsonRequest);
    }

    public void addToQueueWithJsonRequest1(JSONObject reqObject, String webService, final Context context, final Fragment fragment) {
        int req;

        if (reqObject == null) {
            System.out.println("request before req>>>>>" + reqObject);
            req = Request.Method.POST;
        } else {
            req = Request.Method.GET;
        }
        RequestQueue queue = requestQueue;

        System.out.println("server url before req>>>>>" + webService);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                req, webService, reqObject,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("response<<<<<" + response);
                        if (fragment != null) {
                            ((UpdateVolleyData) fragment).updateFromVolley(response);
                        } else {
                            ((UpdateVolleyData) context).updateFromVolley(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("response in error<<<<<" + error);
                if (fragment != null) {
                    ((UpdateVolleyData) fragment).updateFromVolley(error);
                } else {
                    ((UpdateVolleyData) context).updateFromVolley(error);
                }
            }
        });

        queue.add(jsonRequest);
    }


    public void addToQueueWithStringRequest(String webService, final Context context, final Fragment fragment) {
        System.out.println("fragment<<<<<" + fragment + "");

        System.out.println("webService<<<<<" + webService);
        RequestQueue queue = requestQueue;

        StringRequest strreq = new StringRequest(Request.Method.GET,
                webService,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response<<<<<" + response);
                        Log.e("response<<<<<", "" + response);
                       // response = "{\"email\":\"sruthi.kumbham@gmail.com\"}";
                        try{
                        if (fragment != null) {
                            ((UpdateVolleyData) fragment).updateFromVolley(new JSONObject(response));
                            Log.e("response<<<<<", "" + response);

                        } else {
                            ((UpdateVolleyData) context).updateFromVolley(response);
                        }
                        }catch(Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("response in error<<<<<"
                        + error);
                if (fragment != null) {
                    ((UpdateVolleyData) fragment).updateFromVolley(error);
                } else {
                    ((UpdateVolleyData) context).updateFromVolley(error);
                }
            }
        });
        queue.add(strreq);
    }
}
