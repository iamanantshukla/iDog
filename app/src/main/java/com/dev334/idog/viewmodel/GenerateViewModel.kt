package com.dev334.idog.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dev334.idog.repository.DogRepository
import com.dev334.idog.model.Result
import com.dev334.idog.util.LruCacheManager

class GenerateViewModel(private val repository: DogRepository): ViewModel() {
    var bitmap = MutableLiveData<Bitmap>()
    val isLoading = MutableLiveData<Boolean>()

    fun getImage(context: Context){
        isLoading.value = true;
        if(!isOnline(context)){
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
            isLoading.value = false;
            return;
        }
        repository.getImageApiCall { result ->
            when (result) {
                is Result.Success -> {
                    loadBitmapWithGlide(context, result.data)
                }
                is Result.Error -> {
                    // Handle the error case
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    isLoading.value = false
                }
            }
        }
    }

    private fun loadBitmapWithGlide(context: Context, data: String) {
        val lruCacheManager = LruCacheManager.getInstance(context)
        Glide.with(context)
            .asBitmap()
            .load(data)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i("ViewModelDebugger", "onLoadFailed: "+e!!.message)
                    //failed to load image
                    isLoading.postValue(false)
                    bitmap.postValue(null)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource != null) {
                        lruCacheManager.addImageToCache(resource)
                    }
                    isLoading.postValue(false)
                    bitmap.postValue(resource)
                    return false
                }
            })
            .submit()
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}