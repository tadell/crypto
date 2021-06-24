package com.example.crypto

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.crypto.helper.OkHttpProvider
import com.example.crypto.model.test.CommonTestDataUtil
import com.example.crypto.view.MainActivity
import com.jakewharton.espresso.OkHttp3IdlingResource
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CryptoListUiTest {

    private lateinit var testScenario: ActivityScenario<MainActivity>
    private val mockWebServer = MockWebServer()

    companion object {
        private lateinit var startIntent: Intent

    }

    @Before
    fun setup() {
        mockWebServer.start(8000)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                OkHttpProvider.instance
            )
        )
    }

    @Test
    fun visibility_friends_list_button() {
        mockWebServer.dispatcher = (object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return CommonTestDataUtil.dispatch(request) ?: MockResponse().setResponseCode(200)
            }
        })
        startIntent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                MainActivity::class.java
            )
        testScenario = ActivityScenario.launch(startIntent)
        check_friends_list_click()
    }


    private fun check_friends_list_click() {
        onView(withId(R.id.rv_crypto_list))
            .check(matches(isDisplayed()))
    }

    @After
    fun afterTestsRun() {
        mockWebServer.shutdown()
    }
}