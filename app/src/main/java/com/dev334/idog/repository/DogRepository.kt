package com.dev334.idog.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dev334.idog.model.ApiResponse
import com.dev334.idog.retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository(private val api: ApiInterface) {

    private val responseBody = MutableLiveData<ApiResponse>()

    fun getImageApiCall(): MutableLiveData<ApiResponse> {
        api.getImage().enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.body()!=null){
                    responseBody.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.i("RepositoryDebugger", "onFailure: "+t.message)
            }

        })
        return responseBody
    }

}
