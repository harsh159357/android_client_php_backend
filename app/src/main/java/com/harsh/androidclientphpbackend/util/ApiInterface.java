package com.harsh.androidclientphpbackend.util;


import com.harsh.androidclientphpbackend.model.ApiRequest;
import com.harsh.androidclientphpbackend.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("./")
    Call<ApiResponse> operation(@Body ApiRequest request);

}
