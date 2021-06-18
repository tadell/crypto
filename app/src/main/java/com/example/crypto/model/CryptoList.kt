package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoList(

    @SerializedName("status") @Expose
    private var status: Status? = null,

    @SerializedName("data")
    @Expose
    private var data: MutableList<Data?>? = null
) {
}