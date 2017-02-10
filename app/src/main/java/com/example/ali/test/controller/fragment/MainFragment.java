package com.example.ali.test.controller.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.test.core.DownloadActivityInterface;
import com.example.ali.test.core.DownloadActivity;
import com.example.ali.test.parser.JsonParser;
import com.example.ali.test.controller.activity.MainActivity;
import com.example.ali.test.R;
import com.example.ali.test.adapter.MovieRecycleAdapter;
import com.example.ali.test.model.Movie;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    MovieRecycleAdapter movieRecycleAdapter;
    DownloadActivity.OnResultListener onResultListener;
    private DownloadActivityInterface downloadActivityInterface;
    public MainFragment() {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
        final String SORT = "sort_by";
        final String APPID_PARAM = "api_key";

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(SORT, "popularity.desc")
                .appendQueryParameter(APPID_PARAM, "144eefdfe75e0f8cb5d9f9b68d178670")
                .build();

        downloadActivityInterface.setUrl(builtUri.toString());

        JsonParser jsonParser = new JsonParser();
        String[] objects = {"poster_path","id","release_date","backdrop_path","overview","title"};
        jsonParser.setObjects(objects);
        downloadActivityInterface.setParser(jsonParser);

        downloadActivityInterface.setDataModel(Movie.class.getName());

        onResultListener = new DownloadActivity.OnResultListener(){

            @Override
            public void onSuccess(List<Object> result) {
                movieRecycleAdapter = new MovieRecycleAdapter(getContext(), result, new MovieRecycleAdapter.OnClickHandler() {
                    @Override
                    public void onClick(Movie movieResult) {
                        DetailFragment detailFragment = new DetailFragment();
                        Bundle args = new Bundle();
                        args.putParcelable("Movie",movieResult);
                        detailFragment.setArguments(args);
                        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction()
//                        .detach(MainFragment.this)
//                        .hide(MainFragment.this)
//                        .remove(MainFragment.this)
                                .replace(R.id.fragment,detailFragment)
                                .commit();
                    }
                });
                recyclerView.setAdapter(movieRecycleAdapter);
            }
            @Override
            public void onError(String errorMessage) {
                Log.v("MainFragment","OnError "+ errorMessage);
            }
        };

        ((DownloadActivity)getActivity()).fetch(onResultListener);


    }



}
