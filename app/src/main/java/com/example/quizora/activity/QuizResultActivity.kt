package com.example.quizora.activity

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizora.R
import com.example.quizora.databinding.ActivityQuizResultBinding

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            supportActionBar?.hide()
        }
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val score = intent.getIntExtra("Score", 0)
        if (score >= (10 * 80 / 100)) {
            binding.laWinAnimation.visibility = View.VISIBLE
            binding.laWinAnimation.playAnimation()
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                if (binding.laWinAnimation.isAnimating) {
                    binding.laWinAnimation.cancelAnimation()
                }
                binding.laWinAnimation.visibility = View.GONE
            }, 3500)
        }


    }

    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}