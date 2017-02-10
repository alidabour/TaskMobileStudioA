package com.example.ali.test.controller.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.test.core.DownloadActivityInterface;
import com.example.ali.test.core.DownloadActivity;
import com.example.ali.test.parser.JsonParser;
import com.example.ali.test.R;
import com.example.ali.test.model.Movie;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    DownloadActivity.OnResultListener onResultListenerReviews;
    DownloadActivity.OnResultListener onResultListenerVideos;
    private DownloadActivityInterface downloadActivityInterface;

    TextView title;
    String text = " ";
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            downloadActivityInterface = (DownloadActivityInterface) context;
        }catch (ClassCastException castException) {
            Log.v("Test","Must Implement Interface");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        Bundle args = this.getArguments();
        if (args != null) {
            Movie movie = args.getParcelable("Movie");
            Log.v("Test", "DF Movie : " + movie.getTitle());
        }
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        title = (TextView) view.findViewById(R.id.title);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
        final String APPID_PARAM = "api_key";

        Uri builtUri= Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath("346672")
                .appendPath("reviews")
                .appendQueryParameter(APPID_PARAM,"144eefdfe75e0f8cb5d9f9b68d178670")
                .build();
        downloadActivityInterface.setUrl(builtUri.toString());

        String[] objects = {"author","content"};
        JsonParser jsonParser = new JsonParser();
        jsonParser.setObjects(objects);
        downloadActivityInterface.setParser(jsonParser);

        downloadActivityInterface.setDataModel(Movie.class.getName());

        onResultListenerReviews = new DownloadActivity.OnResultListener(){
            @Override
            public void onSuccess(List<Object> movies) {
                for (Object x:movies){
                    text += " Author : "+((Movie)x).getAuthor()+ "\n";
                    text += " Content :"+((Movie)x).getContent()+"\n";
                }
            }
            @Override
            public void onError(String errorMessage) {

            }
        };
        ((DownloadActivity)getActivity()).fetch(onResultListenerReviews);


        builtUri= Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath("346672")
                .appendPath("videos")
                .appendQueryParameter(APPID_PARAM,"144eefdfe75e0f8cb5d9f9b68d178670")
                .build();
        downloadActivityInterface.setUrl(builtUri.toString());

        String[] objects2 = {"key","name"};
        jsonParser.setObjects(objects2);
        downloadActivityInterface.setParser(jsonParser);

        downloadActivityInterface.setDataModel(Movie.class.getName());
        onResultListenerVideos = new DownloadActivity.OnResultListener(){
            @Override
            public void onSuccess(List<Object> movies) {
                for (Object x:movies){
                    text += " name : "+((Movie)x).getName() + "\n";
                    text += " key :"+((Movie)x).getKey() +"\n";
                }
                title.setText(text);
            }
            @Override
            public void onError(String errorMessage) {

            }
        };
        ((DownloadActivity)getActivity()).fetch(onResultListenerVideos);
    }
}
