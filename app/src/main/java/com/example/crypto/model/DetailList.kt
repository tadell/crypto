package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailList(

    @SerializedName("status")
    @Expose
    var status: Status? = null,

    @SerializedName("data")
    @Expose
    var data: MutableList<InfoData>? = null
) {
}
