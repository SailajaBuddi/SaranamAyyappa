package com.anvesh.saranamayyappa.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by rbolli on 3/17/2017.
 */

/**
 * Convert a JsonElement into a list of objects or an object with Google Gson.
 * <p>
 * The JsonElement is the response object for a {@link Method} GET call.
 *
 * @author https://plus.google.com/+PabloCostaTirado/about
 */
public class GsonGetRequest<T> extends Request<T> {
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;
    private JSONObject parameters = null;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url           URL of the request to make
     * @param type          is the type of the object to be returned
     * @param listener      is the listener for the right answer
     * @param errorListener is the listener for the wrong answer
     */
    public GsonGetRequest(String url, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener, JSONObject parameters) {
        super(parameters == null ? Method.GET : Method.POST, url, errorListener);

        this.gson = new GsonBuilder().create();
        this.type = type;
        this.listener = listener;
        this.parameters = parameters;


    }

    public GsonGetRequest(String url, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener, JSONObject parameters, int editNDelete) {
        super(editNDelete == AyyappaConstants.FEED_EDIT ? Method.PUT : Method.DELETE, url, errorListener);

        this.gson = new GsonBuilder().create();
        this.type = type;
        this.listener = listener;
        this.parameters = parameters;

        //Util.showLog("GsonGetRequest-url", url);
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            //Util.showLog("req params", parameters.toString());
            return parameters.toString().getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            //Util.showLog("error", e.toString());
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
//        //Util.showLog2("response", "" + response);
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //Util.showLog("json response", "data" + json);
            return (Response<T>) Response.success(gson.<T>fromJson(json, type), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(
                VolleySingleton.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return super.setRetryPolicy(defaultRetryPolicy);

    }
}