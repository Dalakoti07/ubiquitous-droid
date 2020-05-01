package com.example.ubiquitousdroid.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceProvider{
    private static Retrofit retrofitInstance=null;
    private static OkHttpClient.Builder httpClient;
    {
        initRetrofit();
    }

    public static ApiInterface getRetrofitInstance(){
        if(retrofitInstance!=null){
            return retrofitInstance.create(ApiInterface.class);
        }else{
            initRetrofit();
            return retrofitInstance.create(ApiInterface.class);
        }
    }

    private static void initRetrofit(){
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Interceptor.Chain chain) throws IOException {
                                          Request original = chain.request();
                                          Request request = original.newBuilder()
                                                  .header("Authorization", "Client-ID a73c8ca2ffde994")
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  });
        String API_BASE_URL = "https://api.imgur.com/3/";
        Builder builder =
                new Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );
        retrofitInstance =builder
                .client(httpClient.build())
                .build();
    }
}