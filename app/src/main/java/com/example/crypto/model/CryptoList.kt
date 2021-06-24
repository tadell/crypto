package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoList(


    //api listing response class

    @SerializedName("data")
    @Expose
    var data: MutableList<Data>? = null,

    @SerializedName("status")
    @Expose
    var status: Status? = null
) {
}