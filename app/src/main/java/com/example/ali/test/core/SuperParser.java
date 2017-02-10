package com.example.ali.test.core;

import java.util.List;

/**
 * Created by Ali on 2/8/2017.
 */

public interface  SuperParser {
     List<Object> processData(String data,String dataModel);
     void setObjects(String[] objects);
}
