package com.example.quizora.viewModel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizora.model.Quiz
import com.example.quizora.repository.QuizRepository
import com.example.quizora.utils.QuizResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    private val _quizStatus = MutableLiveData<QuizResult>()
    val quizStatus: LiveData<QuizResult> get() = _quizStatus

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = quizRepository.getQuiz()
            _quizStatus.postValue(result)
        }
    }

    val quiz : LiveData<Quiz>
        get() = quizRepository.quiz

}