package com.example.crypto.model.test

import com.example.crypto.helper.FileReader.readStringFromFile
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest

object CommonTestDataUtil {

    fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "v1/cryptocurrency/listings/latest?" -> {
                MockResponse().setResponseCode(200).setBody(
                    (readStringFromFile("getCryptoSuccessResponse.json"))
                )
            }

            else -> {
                MockResponse().setResponseCode(404).setBody("{}")
            }
        }
    }
}