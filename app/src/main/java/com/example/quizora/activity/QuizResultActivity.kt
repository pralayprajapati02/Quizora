package com.example.quizora.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.CalendarContract.Colors
import android.util.Log
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
        val amount = intent.getIntExtra("amount", 10)
        val categoryNo = intent.getIntExtra("category", 0)
        val difficulty = intent.getStringExtra("difficulty")
        val type = intent.getStringExtra("type")
        val actualCategory = if (categoryNo == 0) null else categoryNo
        val actualDifficulty = if (difficulty == "null") null else difficulty
        val actualType = if (type == "null") null else type
        val score = intent.getIntExtra("Score", 10)
        val noOfQuestion = intent.getIntExtra("totalNoOfQuestion", 49)
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val coinValue = sharedPreferences.getInt("coin",0)

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
                    animateNumber(50,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                in 20..29 -> {
                    animateNumber(100,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                in 30..39 -> {
                    animateNumber(150,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                in 40..49 -> {
                    animateNumber(200,binding.tvNumberOfCoinEarned, editor,coinValue)
                }
                50 -> {
                    animateNumber(250,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
            }
        }else{
            when (noOfQuestion) {
                in 10..19 -> {
                    animateNumberReverse(50,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                in 20..29 -> {
                    animateNumberReverse(100,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                in 30..39 -> {
                    animateNumberReverse(150,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                in 40..49 -> {
                    animateNumberReverse(200,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
                50 -> {
                    animateNumberReverse(250,binding.tvNumberOfCoinEarned,editor,coinValue)
                }
            }
        }

        val message = if (didWin) {
            winMessages.random()
        } else {
            loseMessages.random()
        }
        binding.tvGreetingMessage.text = message


        binding.btnBackToHome.setOnClickListener {
            onBackPressed()
        }

        binding.btnTryAgain.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("amount", amount)
            intent.putExtra("category", actualCategory)
            intent.putExtra("difficulty", actualDifficulty)
            intent.putExtra("type", actualType)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
    }

    private fun animateNumber(target: Int, textView: TextView, editor: SharedPreferences.Editor,coinValue : Int, duration: Long = 1500L) {
        editor.putInt("coin",coinValue+target)
        val animator = ValueAnimator.ofInt(0, target)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            textView.text = String.format(" $value")
        }
        animator.start()
        editor.apply()
    }

    private fun animateNumberReverse(target: Int, textView: TextView, editor : SharedPreferences.Editor,coinValue : Int, duration: Long = 3000L) {
        editor.putInt("coin",coinValue+0)
        val animator = ValueAnimator.ofInt(target,0)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            textView.text = String.format(" $value")
        }
        animator.start()
        editor.apply()
        binding.tv80PerMsg.visibility = View.VISIBLE
    }
}