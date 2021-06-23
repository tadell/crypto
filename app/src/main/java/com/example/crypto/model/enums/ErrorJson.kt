package com.example.crypto.model.enums

import com.example.crypto.model.ApiExceptionBody
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorJson(
    val status: ApiExceptionBody
)