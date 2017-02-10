package com.example.ali.test.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.ali.test.network.DownloadAsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
public abstract class DownloadActivity extends AppCompatActivity {
    public OnResultListener onResultListener;
    public StringResponseListener responseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public final void fetch(final OnResultListener onResultListener)  {
        this.onResultListener = onResultListener;
        responseListener = new StringResponseListener() {
            @Override
            public void notifySuccess(String response) {
                List<Object> moviesObjects = getParser().processData(response,getDataModel());
                onResultListener.onSuccess(moviesObjects);
                Log.v("Test","Response :" + response);
            }
            @Override
            public void notifyError(String error) {
                onResultListener.onError(error);
            }
        };

        //Volley
//        DownloadVolley downloadVolley = new DownloadVolley(getApplicationContext(), responseListener);
//        downloadVolley.execute(getUrl());

        //AsyncTask
        DownloadAsyncTask d = new DownloadAsyncTask(responseListener);
        try {
            d.execute(new URL(getUrl()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public abstract String getUrl();
    public abstract SuperParser getParser();
    public abstract String getDataModel();

    public interface OnResultListener {
        void onSuccess(List<Object> response);
        void onError(String errorMessage);
    }

}
