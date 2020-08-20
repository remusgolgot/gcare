package com.gcare.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    public static final String dateFormat = "yyyy-MM-dd";
    public static final Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

}
