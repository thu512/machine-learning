package com.lcj.recycler.net;

import com.lcj.recycler.model.NetData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NetInterface {

    @GET("image?")
    Call<NetData> getImg(@Header("X-Naver-Client-Id") String id,
                         @Header("X-Naver-Client-Secret") String secret,
                         @Query("query") String query,
                         @Query("filter") String filter,
                         @Query("display") int cnt);
}
