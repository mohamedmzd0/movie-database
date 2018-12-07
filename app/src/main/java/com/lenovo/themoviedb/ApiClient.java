package com.lenovo.themoviedb;

import android.os.Environment;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    final static String BASE_URL = "http://api.themoviedb.org/3/";
    static int cacheSize = 10 * 1024 * 1024;
    static String path = Environment.getExternalStorageDirectory().toString() + "/myApp/";
    static Cache cache = new Cache(new File(path), cacheSize);
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(cache)
            .build();
    static Retrofit retrofit = null;

    public static Retrofit getAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
