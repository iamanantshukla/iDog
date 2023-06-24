package com.dev334.idog.module

import com.dev334.idog.repository.DogRepository
import com.dev334.idog.retrofit.ApiInterface
import com.dev334.idog.util.LruCacheManager
import com.dev334.idog.viewmodel.GenerateViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/image/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiInterface::class.java)
    }
    single<DogRepository>{
        DogRepository(get())
    }
    viewModel{
        GenerateViewModel(get())
    }
}