package com.example.freenow.domain.usecases

import com.example.freenow.MockResponseFileReader
import com.google.gson.JsonParser
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetPoiListForBoundsTest{


    lateinit var server : MockWebServer

    @Before
    fun initTest(){
        server = MockWebServer()
    }

    @After
    fun shutdown(){
        server.shutdown()
    }

    @Test
    fun `authentication sends proper body`(){
        server.apply{
            enqueue(MockResponse().setBody(MockResponseFileReader("get_poi_list.json").content))
        }

        val baseUrl = server.url("")

        //we create the AuthenticationManager using the Base Url provided by the Mock Server
//        startKoin(listOf(module {
//            single { AuthenticationManager(baseUrl.url().toString()) }
//        }))

//        get<AuthenticationManager>().apply {
//            authenticateBlocking()
//        }

//        val testBody = LoginBody(AuthenticationManager.username, AuthenticationManager.password)
//        val requestBody = server.takeRequest().body.readUtf8()
//
//        val json = JsonParser().parse(requestBody).asJsonObject
//        assertEquals(json.get("username").toString().replace("\"",""), testBody.username)
    }
}