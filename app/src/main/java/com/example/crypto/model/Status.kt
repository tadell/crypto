package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("timestamp")
    @Expose
    val timestamp: String? = null,

    @SerializedName("error_code")
    @Expose
    val errorCode: Int? = null,

    @SerializedName("error_message")
    @Expose
    val errorMessage: Any? = null,

    @SerializedName("elapsed")
    @Expose
    val elapsed: Int? = null,

    @SerializedName("credit_count")
    @Expose
    val creditCount: Int? = null
) {

}
