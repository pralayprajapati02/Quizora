package com.example.quizora.utils

sealed class QuizResult {
    object Success : QuizResult()
    object NoQuestions : QuizResult()
    object Failure : QuizResult()
}