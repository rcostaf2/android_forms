package com.aroos.metacom.activities.apis;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by murilo on 27/09/2016.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://177.130.1.206/altarede";
    //public static final String API_BASE_URL = "http://177.130.1.206/metacom";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setConverter(new GsonConverter(new Gson()))
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
