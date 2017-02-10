package com.example.ali.test.parser;

import com.example.ali.test.core.SuperParser;
import com.example.ali.test.model.Movie;
import com.example.ali.test.model.MovieGsonResponse;
import com.example.ali.test.model.MovieResult;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Ali on 2/10/2017.
 */

public class GsonParser implements SuperParser {
    @Override
    public List<Object> processData(String data, String dataModel) {
        Gson gson = new Gson();
        Object e= gson.fromJson(data,dataModel.getClass());
        List<MovieResult> results = ((MovieGsonResponse)e).getResults();
        return  (List<Object>)(Object)results;
//        List<Object> objects = (Object)((MovieGsonResponse)e).getResults();
    }

    @Override
    public void setObjects(String[] objects) {

    }
}
