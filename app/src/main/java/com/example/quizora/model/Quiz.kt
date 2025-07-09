package com.example.quizora.model

data class Quiz(
    val response_code: Int,
    val results: List<Result>
)