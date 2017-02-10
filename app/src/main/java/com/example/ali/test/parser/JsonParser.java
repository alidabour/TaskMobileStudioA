package com.example.ali.test.parser;

import android.net.ParseException;
import android.util.Log;

import com.example.ali.test.core.SuperParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ali on 2/8/2017.
 */

public class JsonParser implements SuperParser {
    String json;
    String[] objects;
    List<HashMap<String,String>> maps = new ArrayList<>();

    //SuperParser abstract methods
    @Override
    public List<Object> processData(String data,String dataModel) {
        this.json = data;
        try {
            if(this.json != null){

                parseJson(new JSONObject(this.json));
                List<Object> moviesObjects = new ArrayList<>();
                for (HashMap<String, String> x : maps) {
                    Object object = null;
                    Class<?> cla;
                    Constructor<?> constructor;
                    try {
                        cla = Class.forName(dataModel);
                        constructor = cla.getConstructor(HashMap.class);
                        object = constructor.newInstance(x);
                    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        Log.v("Test", "Mission error :" + e);
                    }
                    moviesObjects.add(object);
                }
                return moviesObjects;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void setObjects(String[] objects) {
        this.objects=objects;
    }

    //Parse Json
    public  void getArray(Object object2) throws ParseException, JSONException {
//        Log.v("JSONTest","getArray :"+object2);
        JSONArray jsonArr = (JSONArray) object2;
        for (int k = 0; k < jsonArr.length(); k++) {
            if (jsonArr.get(k) instanceof JSONObject) {
                parseJson((JSONObject) jsonArr.get(k));
            } else {
//                Log.v("JSONTest","jsonArr.get(k): "+jsonArr.get(k));
            }

        }
    }

    public void parseJson(JSONObject jsonObject) throws ParseException, JSONException {
        Iterator<String> iterator = jsonObject.keys();
        HashMap<String,String> map = new HashMap<>();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (jsonObject.get(obj.toString()) instanceof JSONArray) {
                getArray(jsonObject.get(obj.toString()));
            } else {
                if (jsonObject.get(obj.toString()) instanceof JSONObject) {
                    parseJson((JSONObject) jsonObject.get(obj.toString()));
                } else {
                    if( Arrays.asList(objects).contains(obj.toString())){
                    }
                    map.put(obj.toString(),jsonObject.get(obj.toString()).toString());
                }
            }
        }
        maps.add(map);
    }

}