package com.example.crypto.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    @Expose var id: Int? = null,

    @SerializedName("name")
    @Expose val name: String? = null,

    @SerializedName("symbol")
    @Expose val symbol: String? = null,

    @SerializedName("slug")
    @Expose
    private val slug: String? = null,

    @SerializedName("circulating_supply")
    @Expose
    private val circulatingSupply: Double? = null,

    @SerializedName("total_supply")
    @Expose
    private val totalSupply: Double? = null,

    @SerializedName("max_supply")
    @Expose
    private val maxSupply: Double? = null,

    @SerializedName("date_added")
    @Expose
    private val dateAdded: String? = null,

    @SerializedName("num_market_pairs")
    @Expose
    private val numMarketPairs: Int? = null,

    @SerializedName("cmc_rank")
    @Expose
    private val cmcRank: Int? = null,

    @SerializedName("last_updated")
    @Expose
    private val lastUpdated: String? = null,

    @SerializedName("quote")
    @Expose var quote: Quote? = null
)
