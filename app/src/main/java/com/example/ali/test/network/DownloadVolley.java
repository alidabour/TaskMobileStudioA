package com.example.ali.test.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ali.test.core.StringResponseListener;

/**
 * Created by Ali on 2/10/2017.
 */

public class DownloadVolley{
    Context context;
    StringResponseListener responseListener;
    public DownloadVolley(Context context,StringResponseListener responseListener){
        this.context = context;
        this.responseListener = responseListener;
    }

    public void execute(String url){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (responseListener != null) {
                            responseListener.notifySuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.notifyError(error.getMessage());
                }
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
