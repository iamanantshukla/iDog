package com.dev334.idog.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dev334.idog.model.ApiResponse
import com.dev334.idog.retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.dev334.idog.model.Result

class DogRepository(private val api: ApiInterface) {

    fun getImageApiCall(onResult: (Result<String>) -> Unit){
        api.getImage().enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val imageResponse = response.body()
                    imageResponse?.let {
                        onResult(Result.Success(it.message.toString()))
                    } ?: onResult(Result.Error("Image URL not found"))
                } else {
                    onResult(Result.Error("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.i("RepositoryDebugger", "onFailure: "+t.message)
            }

        })
    }
}
