package com.demo.controlr.utils;

import com.google.gson.Gson;

import rx.android.BuildConfig;

public final class Contains {

    public static final Gson GSON = new Gson();
    private static native String urlApiDevelopment();
    private static native String urlApiProduct();
    public static final String VALUE_AUTHORIZATION = "VALUE_AUTHORIZATION";
    public static final String VALUE_TWITTER_SESSION = "VALUE_TWITTER_SESSION";

    @SuppressWarnings("ConstantConditions")
    public static  String getUrlApi(){
        String target = "";
        if("development".equalsIgnoreCase(BuildConfig.FLAVOR)){
            target = urlApiDevelopment();
        }else {
            if("product".equalsIgnoreCase(BuildConfig.FLAVOR)){
                target = urlApiProduct();
            }
        }

        return target;
    }
}
