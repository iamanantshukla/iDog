package com.dev334.idog.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : String? = null

)