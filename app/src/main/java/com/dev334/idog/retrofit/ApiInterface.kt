package com.dev334.idog.retrofit

import com.dev334.idog.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("random")
    fun getImage() : Call<ApiResponse>
}