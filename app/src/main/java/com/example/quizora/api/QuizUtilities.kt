package com.example.quizora.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuizUtilities {

    private val BASE_URL = "https://opentdb.com/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}