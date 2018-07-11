package com.lcj.recycler.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Net {
    private static final Net ourInstance = new Net();

    public static Net getInstance() {
        return ourInstance;
    }

    private Net() { }

    //retrofit 생성
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    NetInterface netInterface;

    public NetInterface getNetInterface() {
        if(netInterface == null){
            netInterface = retrofit.create(NetInterface.class);
        }
        return netInterface;
    }
}
