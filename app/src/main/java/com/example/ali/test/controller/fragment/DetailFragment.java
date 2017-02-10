package com.example.ali.test.controller.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.test.core.DownloadActivityInterface;
import com.example.ali.test.core.DownloadActivity;
import com.example.ali.test.parser.JsonParser;
import com.example.ali.test.R;
import com.example.ali.test.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.image)
    ImageView collapsingImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.poster_img)
    ImageView posterImg;
    @BindView(R.id.releaseDate)
    TextView releaseData;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.review)
    TextView review;
    DownloadActivity.OnResultListener onResultListenerReviews;
    DownloadActivity.OnResultListener onResultListenerVideos;
    private DownloadActivityInterface downloadActivityInterface;
    String id ;
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
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,view);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Movie movie = intent.getParcelableExtra(getString(R.string.movieIntent));
            id = movie.getId();
            collapsingToolbarLayout.setTitle(movie.getTitle());
            title.setText(movie.getTitle());
            Glide.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterUrl()).into(posterImg);
            Glide.with(getContext()).load("http://image.tmdb.org/t/p/w500"+movie.getBackdropPath()).into(collapsingImage);
            releaseData.setText(movie.getReleaseData());
            overview.setText(movie.getOverview());
        }
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
        final String APPID_PARAM = "api_key";

        Uri builtUri= Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(id)
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
                    if(((Movie)x).getAuthor()!=null){
                        text += " Author : "+((Movie)x).getAuthor()+ "\n";
                        text += " Content :"+((Movie)x).getContent()+"\n";
                    }
                }
                review.setText(text);
            }
            @Override
            public void onError(String errorMessage) {

            }
        };
        ((DownloadActivity)getActivity()).fetch(onResultListenerReviews);


        builtUri= Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(id)
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
//                    text += " name : "+((Movie)x).getName() + "\n";
//                    text += " key :"+((Movie)x).getKey() +"\n";
                }
//                title.setText(text);
            }
            @Override
            public void onError(String errorMessage) {

            }
        };
        ((DownloadActivity)getActivity()).fetch(onResultListenerVideos);
    }

}
