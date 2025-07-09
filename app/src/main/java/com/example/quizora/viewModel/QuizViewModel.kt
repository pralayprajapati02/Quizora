package com.example.quizora.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizora.model.Quiz
import com.example.quizora.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepository.getQuiz()
        }
    }

    val quiz : LiveData<Quiz>
        get() = quizRepository.quiz

}