package com.dev334.idog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev334.idog.repository.DogRepository

class GenerateViewModel(private val repository: DogRepository): ViewModel() {
    var image = MutableLiveData<String>()
    fun getImage(){
        val apiResponse = repository.getImageApiCall().value
        if(apiResponse!!.status == "success"){
            image.value = apiResponse!!.message
        }else{
            image.value = "";
        }
    }
}