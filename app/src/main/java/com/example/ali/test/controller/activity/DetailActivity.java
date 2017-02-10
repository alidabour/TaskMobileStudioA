package com.example.ali.test.controller.activity;

import android.os.Bundle;
import com.example.ali.test.R;
import com.example.ali.test.core.DownloadActivity;
import com.example.ali.test.core.DownloadActivityInterface;
import com.example.ali.test.core.SuperParser;

public class DetailActivity extends DownloadActivity implements DownloadActivityInterface{
    String url;
    String dataModel;
    SuperParser parser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public String getUrl() {
        return this.url;
    }
    @Override
    public SuperParser getParser() {
        return this.parser;
    }

    @Override
    public String getDataModel() {
        return this.dataModel;
    }
    @Override
    public void setDataModel(String dataModel){
        this.dataModel = dataModel;
    }
    @Override
    public void setUrl(String url){
        this.url=url;
    }
    @Override
    public void setParser(SuperParser parser) {
        this.parser = parser;
    }


}
