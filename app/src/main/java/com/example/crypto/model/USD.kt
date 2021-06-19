package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("price")
    @Expose var price: Double? = null,

    @SerializedName("volume_24h")
    @Expose
    private val volume24h: Double? = null,

    @SerializedName("percent_change_1h")
    @Expose
    private val percentChange1h: Double? = null,

    @SerializedName("percent_change_24h")
    @Expose val percentChange24h: Double? = null,

    @SerializedName("percent_change_7d")
    @Expose
    private val percentChange7d: Double? = null,

    @SerializedName("market_cap")
    @Expose val marketCap: Double? = null,

    @SerializedName("last_updated")
    @Expose
    private var lastUpdated: String? = null
) {}
