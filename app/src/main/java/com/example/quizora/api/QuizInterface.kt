package com.example.quizora.api

import com.example.quizora.model.Quiz
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuizInterface {

    @GET("/api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String?
    ): Response<Quiz>

}