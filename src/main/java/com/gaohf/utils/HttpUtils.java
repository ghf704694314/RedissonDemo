package com.gaohf.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();
    }

    public static String get(String url){
        String responseText;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        long begin = System.currentTimeMillis();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            logger.error("get to {} error. cost:{}. {}",url,System.currentTimeMillis()-begin,e);
            responseText = "";
        }
        return responseText;
    }

    public static String post(String url,String jsonParams){
        String responseText;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        long begin = System.currentTimeMillis();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            logger.error("post to {} error. cost:{}. {}",url,System.currentTimeMillis()-begin,e);
            responseText = "";
        }
        return responseText;
    }

}