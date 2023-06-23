package com.dev334.idog.retrofit

import com.dev334.idog.model.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/")
    fun getImage() : Call<ApiResponse>
}