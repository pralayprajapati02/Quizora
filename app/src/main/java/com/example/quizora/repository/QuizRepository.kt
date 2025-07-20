package com.example.quizora.repository

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizora.api.QuizInterface
import com.example.quizora.model.Quiz
import com.example.quizora.utils.QuizResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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


    suspend fun getQuiz(): QuizResult {
        val result = quizInterface.getQuiz(amount, category, difficulty, type)
        if (result.isSuccessful && result.body() != null) {
            val quizResponse = result.body()!!
            val questionList = ArrayList(quizResponse.results)
            return if (questionList.isNotEmpty()) {
                quizLiveData.postValue(result.body())
                QuizResult.Success
            } else {
                QuizResult.NoQuestions
            }
        }
        return QuizResult.Failure
    }
}