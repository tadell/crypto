package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InfoData(

    @SerializedName("id")
    @Expose var id: Int? = null,

    @SerializedName("name")
    @Expose val name: String? = null,

    @SerializedName("symbol")
    @Expose val symbol: String? = null,

    @SerializedName("slug")
    @Expose
    private val slug: String? = null,

    @SerializedName("logo")
    @Expose
    private val logo: String? = null,

    @SerializedName("description")
    @Expose
    private val description: String? = null
)
