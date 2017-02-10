package com.example.ali.test.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ali.test.core.StringResponseListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ali on 2/8/2017.
 */

public class DownloadAsyncTask extends AsyncTask<URL,Void,String> {
    StringResponseListener stringResponseListener;
    public DownloadAsyncTask(StringResponseListener stringResponseListener){
        this.stringResponseListener = stringResponseListener;
    }
    @Override
    protected String doInBackground(URL... urls) {
        Log.v("Test","Start of Async Task");
        String result=null;
        HttpURLConnection urlConnection ;
        BufferedReader reader ;

        try{
            urlConnection = (HttpURLConnection) urls[0].openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            result=buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        stringResponseListener.notifySuccess(s);
    }
}
