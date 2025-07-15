package com.example.quizora.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        var didWin: Boolean? = null

        val winMessages = listOf(
            "Great job! You nailed it with confidence and skill!",
            "You're a true winner! That was an impressive performance!",
            "Victory is yours! You played smart and it paid off!",
            "Well done! Every answer showed your sharp thinking.",
            "Awesome! You dominated that quiz like a pro!"
        )

        val loseMessages = listOf(
            "Don't worry, you'll get it next time!",
            "Keep trying! Every mistake is a step forward.",
            "You gave it a good shot — now go for a comeback!",
            "Not your best round, but you've got this!",
            "It’s okay to lose — champions are made through practice!"
        )


        val score = intent.getIntExtra("Score", 10)
        val noOfQuestion = intent.getIntExtra("totalNoOfQuestion", 49)
        if (score >= (noOfQuestion * 80 / 100)) {
            didWin = true
            binding.tvScore.setTextColor(ContextCompat.getColor(this, R.color.green))
            binding.tvScore.text = score.toString()
            binding.tvOutOfScore.text = String.format("/ $noOfQuestion")
            binding.imvWinLoseIcon.setImageResource(R.drawable.trophy)
            binding.tvGreetings.text = String.format("CONGRATS!")
            binding.laWinAnimation.visibility = View.VISIBLE
            binding.laWinAnimation.playAnimation()
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                if (binding.laWinAnimation.isAnimating) {
                    binding.laWinAnimation.cancelAnimation()
                }
                binding.laWinAnimation.visibility = View.GONE
            }, 3500)
        }else{
            didWin = false
            binding.tvScore.setTextColor(ContextCompat.getColor(this, R.color.red))
            binding.tvScore.text = score.toString()
            binding.tvOutOfScore.text = String.format("/ $noOfQuestion")
            binding.imvWinLoseIcon.setImageResource(R.drawable.bomb)
            binding.tvGreetings.text = String.format("OH NO!")
        }

        if (didWin){
            when (noOfQuestion) {
                in 10..19 -> {
                    animateNumber(50,binding.tvNumberOfCoinEarned)
                }
                in 20..29 -> {
                    animateNumber(100,binding.tvNumberOfCoinEarned)
                }
                in 30..39 -> {
                    animateNumber(150,binding.tvNumberOfCoinEarned)
                }
                in 40..49 -> {
                    animateNumber(200,binding.tvNumberOfCoinEarned)
                }
                50 -> {
                    animateNumber(250,binding.tvNumberOfCoinEarned)
                }
            }
        }else{
            when (noOfQuestion) {
                in 10..19 -> {
                    animateNumberReverse(50,binding.tvNumberOfCoinEarned,3000L)
                }
                in 20..29 -> {
                    animateNumberReverse(100,binding.tvNumberOfCoinEarned,3000L)
                }
                in 30..39 -> {
                    animateNumberReverse(150,binding.tvNumberOfCoinEarned,3000L)
                }
                in 40..49 -> {
                    animateNumberReverse(200,binding.tvNumberOfCoinEarned,3000L)
                }
                50 -> {
                    animateNumberReverse(250,binding.tvNumberOfCoinEarned,3000L)
                }
            }
        }

        val message = if (didWin) {
            winMessages.random()
        } else {
            loseMessages.random()
        }
        binding.tvGreetingMessage.text = message

    }

    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun animateNumber(target: Int, textView: TextView, duration: Long = 1500L) {
        val animator = ValueAnimator.ofInt(0, target)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            textView.text = String.format(" $value")
        }
        animator.start()
    }

    private fun animateNumberReverse(target: Int, textView: TextView, duration: Long = 1500L) {
        val animator = ValueAnimator.ofInt(target,0)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            textView.text = String.format(" $value")
        }
        animator.start()
    }
}