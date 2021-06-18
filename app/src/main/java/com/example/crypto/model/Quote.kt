package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("USD")
    @Expose
    private var uSD: USD? = null
) {}
