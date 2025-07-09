package com.example.quizora.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizora.api.QuizInterface
import com.example.quizora.model.Quiz

class QuizRepository(
    private val quizInterface: QuizInterface,
    private val amount: Int,
    private val category: Int?,
    private val difficulty: String?,
    private val type: String?
) {

    private val quizLiveData = MutableLiveData<Quiz>()

    val quiz: LiveData<Quiz>
        get() = quizLiveData


    suspend fun getQuiz() {
        val result = quizInterface.getQuiz(amount, category, difficulty, type)
        if (result.isSuccessful && result.body() != null) {
            quizLiveData.postValue(result.body())
        }
    }

}