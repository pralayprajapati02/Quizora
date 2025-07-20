package com.example.quizora.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizora.repository.QuizRepository

class QuizViewModelFactory(private val quizRepository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(quizRepository) as T
    }
}