package com.example.ali.test.core;

/**
 * Created by Ali on 2/10/2017.
 */

public interface StringResponseListener {
     void notifySuccess(String response);
     void notifyError(String error);
}
