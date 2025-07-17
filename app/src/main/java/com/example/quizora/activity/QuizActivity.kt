package com.example.quizora.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quizora.R
import com.example.quizora.api.QuizInterface
import com.example.quizora.api.QuizUtilities
import com.example.quizora.databinding.ActivityQuizBinding
import com.example.quizora.repository.QuizRepository
import com.example.quizora.utils.Normalizer
import com.example.quizora.viewModel.QuizViewModel
import com.example.quizora.viewModel.QuizViewModelFactory
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizViewModel: QuizViewModel
    private val questionList = ArrayList<String>()
    private val correctAnswerList = ArrayList<String>()
    private val categoryList = ArrayList<String>()
    private val incorrectAnswerList = mutableListOf<List<String>>()
    private val optionsList = mutableListOf<List<String>>()
    private val typesList = ArrayList<String>()
    private val difficultyList = ArrayList<String>()
    private var currentIndex = 0
    private var score = 0
    private var normalOptions = listOf<List<String>>()
    private var normalCategory = listOf<String>()
    private var countDownTimer: CountDownTimer? = null

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
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amount = intent.getIntExtra("amount", 10)
        val categoryNo = intent.getIntExtra("category", 0)
        val difficulty = intent.getStringExtra("difficulty")
        val type = intent.getStringExtra("type")
        val actualCategory = if (categoryNo == 0) null else categoryNo
        val quizInterface = QuizUtilities.getInstance().create(QuizInterface::class.java)
        val quizRepository = QuizRepository(quizInterface, amount, actualCategory, difficulty, type)
        var isFlipped = false
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val coinValue = sharedPreferences.getInt("coin",0)



        quizViewModel =
            ViewModelProvider(this, QuizViewModelFactory(quizRepository))[QuizViewModel::class]

        quizViewModel.quiz.observe(this) {
            it.results.forEach { quiz ->
                val normalQuestion = Normalizer().normalizeHtmlToString(quiz.question)
                questionList.addAll(listOf(normalQuestion))
                val normalCorrectAnswer = Normalizer().normalizeHtmlToString(quiz.correct_answer)
                correctAnswerList.addAll(listOf(normalCorrectAnswer))
                incorrectAnswerList.addAll(listOf(quiz.incorrect_answers))
                categoryList.addAll(listOf(quiz.category))
                typesList.addAll(listOf(quiz.type))
                difficultyList.addAll(listOf(quiz.difficulty))
            }
            for (i in correctAnswerList.indices) {
                val merged = mutableListOf<String>()
                merged.add(correctAnswerList[i])
                merged.addAll(incorrectAnswerList[i])
                merged.shuffle()
                optionsList.add(merged)
            }
            normalOptions = Normalizer().normalizeHtmlMutableList(optionsList)
            normalCategory = Normalizer().cleanCategoryNames(categoryList)
            setQuestionToViews(
                questionList,
                normalCategory,
                normalOptions,
                currentIndex,
                typesList,
                correctAnswerList,
                difficultyList
            )
        }

        binding.btnNextQuestion.isEnabled = false
        binding.btnNextQuestion.setBackgroundResource(R.drawable.disable_button_shape)
        binding.btnNextQuestion.setOnClickListener {
            if (currentIndex + 1 >= questionList.size) {
                val intent = Intent(this, QuizResultActivity::class.java)
                intent.putExtra("Score",score)
                intent.putExtra("totalNoOfQuestion",amount)
                startActivity(intent)
                finish()
                return@setOnClickListener
            }
            val scale = applicationContext.resources.displayMetrics.density
            binding.cvQuestionAndOptions.cameraDistance = 8000 * scale
            val animatorOut =
                ObjectAnimator.ofFloat(binding.cvQuestionAndOptions, "rotationY", 0f, -90f)
            animatorOut.duration = 150
            val animatorIn =
                ObjectAnimator.ofFloat(binding.cvQuestionAndOptions, "rotationY", 90f, 0f)
            animatorIn.duration = 150
            animatorOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    currentIndex++
                    setQuestionToViews(
                        questionList,
                        normalCategory,
                        normalOptions,
                        currentIndex,
                        typesList,
                        correctAnswerList,
                        difficultyList
                    )
                    isFlipped = !isFlipped
                    val allOptionButtons = listOf(
                        binding.btnOption1,
                        binding.btnOption2,
                        binding.btnOption3,
                        binding.btnOption4
                    )
                    allOptionButtons.forEach { btn ->
                        btn.isEnabled = true
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.gray_button_bg)
                    }
                    binding.btnNextQuestion.isEnabled = false
                    binding.btnNextQuestion.setBackgroundResource(R.drawable.disable_button_shape)
                    binding.imvOrangeGlow.alpha = 0F
                    animatorIn.start()
                }
            })
            animatorOut.start()
        }

        binding.imOpenChatBot.setOnClickListener{
            if (coinValue >= 500){
                val generativeModel = GenerativeModel(
                    modelName = "gemini-2.5-pro",  // or another available model
                    apiKey = "AIzaSyBTiZq5SxcS7SpEJzWaE8tVT6fH57Yh7_Q"
                )
                showChatbotDialog(generativeModel,coinValue,editor)
            }else{
                Toast.makeText(this, "You Can Use AI Due to Less Coins", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }



    private fun setQuestionToViews(
        questionList: ArrayList<String>,
        normalCategory: List<String>,
        normalOptions: List<List<String>>,
        currentIndex: Int,
        typesList: ArrayList<String>,
        correctAnswerList: ArrayList<String>,
        difficultyList: ArrayList<String>,
    ) {
        binding.tvCurrentQuestionNumber.text = String.format("${currentIndex + 1} of ${questionList.size}")
        binding.pbProgress.max = questionList.size
        binding.pbProgress.progress = currentIndex + 1
        binding.tvQuestionNo.text = String.format("Question No : ${currentIndex + 1}")
        binding.tvQuestionCategory.text = normalCategory[currentIndex]
        binding.tvQuestion.text = questionList[currentIndex]
        val option = normalOptions[currentIndex]
        println(correctAnswerList[currentIndex])
        binding.btnOption1.visibility = View.VISIBLE
        binding.btnOption2.visibility = View.VISIBLE
        binding.btnOption1.text = String.format("A. ${option[0]}")
        binding.btnOption2.text = String.format("B. ${option[1]}")

        startTimerForDifficulty(difficultyList[currentIndex],correctAnswerList[currentIndex])

        if (typesList[currentIndex] == "multiple") {
            binding.btnOption3.visibility = View.VISIBLE
            binding.btnOption4.visibility = View.VISIBLE
            binding.btnOption3.text = String.format("C. ${option[2]}")
            binding.btnOption4.text = String.format("D. ${option[3]}")
        } else {
            binding.btnOption3.visibility = View.GONE
            binding.btnOption4.visibility = View.GONE
        }

        binding.btnOption1.setOnClickListener(
            buttonClick(
                binding.btnOption1,
                correctAnswerList[currentIndex]
            )
        )
        binding.btnOption2.setOnClickListener(
            buttonClick(
                binding.btnOption2,
                correctAnswerList[currentIndex]
            )
        )
        binding.btnOption3.setOnClickListener(
            buttonClick(
                binding.btnOption3,
                correctAnswerList[currentIndex]
            )
        )
        binding.btnOption4.setOnClickListener(
            buttonClick(
                binding.btnOption4,
                correctAnswerList[currentIndex]
            )
        )
    }

    private fun buttonClick(btnOption: AppCompatButton, correctAnswer: String) = OnClickListener {
        countDownTimer?.cancel()
        val selectedText = btnOption.text.toString().substringAfter(". ").trim()
        val allOptionButtons =
            listOf(binding.btnOption1, binding.btnOption2, binding.btnOption3, binding.btnOption4)

        if (btnOption.isSelected) return@OnClickListener

        btnOption.isSelected = true
        btnOption.isEnabled = false
        allOptionButtons.forEach { it.isEnabled = false }

        if (selectedText == correctAnswer) {
            btnOption.setBackgroundResource(R.drawable.correct_btn_bg)
            score++
        } else {
            btnOption.setBackgroundResource(R.drawable.incorrect_btn_bg)
            allOptionButtons.forEach { btn ->
                val optionText = btn.text.toString().substringAfter(". ").trim()
                if (optionText == correctAnswer) {
                    btn.setBackgroundResource(R.drawable.correct_btn_bg)
                }
            }
        }

        binding.btnNextQuestion.isEnabled = true
        binding.btnNextQuestion.setBackgroundResource(R.drawable.orange_shape)
        binding.imvOrangeGlow.alpha = 1F

    }

    private fun startTimerForDifficulty(difficulty: String, correctAnswer: String) {
        countDownTimer?.cancel() // Cancel any existing timer

        val durationInMillis = when (difficulty.lowercase()) {
            "easy" -> 20_000L
            "medium" -> 40_000L
            "hard" -> 60_000L
            else -> 20_000L
        }

        countDownTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val formatted = String.format("00:%02d", secondsLeft)
                binding.tvTimeLimit.text = formatted

                // Alarm effect if under 10 seconds
                if (secondsLeft <= 10) {
                    binding.tvTimeLimit.setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.red)) // Change to red
                    val scaleUp = ObjectAnimator.ofFloat(binding.tvTimeLimit, "scaleX", 1f, 1.2f).apply {
                        duration = 300
                        repeatMode = ValueAnimator.REVERSE
                        repeatCount = 1
                    }
                    val scaleY = ObjectAnimator.ofFloat(binding.tvTimeLimit, "scaleY", 1f, 1.2f).apply {
                        duration = 300
                        repeatMode = ValueAnimator.REVERSE
                        repeatCount = 1
                    }
                    val imgScaleUp = ObjectAnimator.ofFloat(binding.imgClock, "scaleX", 1f, 1.2f).apply {
                        duration = 300
                        repeatMode = ValueAnimator.REVERSE
                        repeatCount = 1
                    }
                    val imgScaleY = ObjectAnimator.ofFloat(binding.imgClock, "scaleY", 1f, 1.2f).apply {
                        duration = 300
                        repeatMode = ValueAnimator.REVERSE
                        repeatCount = 1
                    }
                    AnimatorSet().apply {
                        playTogether(scaleUp, scaleY)
                        playTogether(imgScaleUp,imgScaleY)
                        start()
                    }
                    // Vibrate
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(200)
                    }

                } else {
                    binding.tvTimeLimit.setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.black)) // Reset color if not alarming
                }
            }

            override fun onFinish() {
                binding.tvTimeLimit.text = "00:00"
                val allOptionButtons =
                    listOf(binding.btnOption1, binding.btnOption2, binding.btnOption3, binding.btnOption4)
                val randomButton = allOptionButtons.random()
                val selectedAnswer  = randomButton.text.toString().substringAfter(". ").trim()

                randomButton.isSelected = true
                randomButton.isEnabled = false
                allOptionButtons.forEach { it.isEnabled = false }

                if (selectedAnswer == correctAnswer) {
                    randomButton.setBackgroundResource(R.drawable.correct_btn_bg)
                    score++
                } else {
                    randomButton.setBackgroundResource(R.drawable.incorrect_btn_bg)
                    allOptionButtons.forEach { btn ->
                        val optionText = btn.text.toString().substringAfter(". ").trim()
                        if (optionText == correctAnswer) {
                            btn.setBackgroundResource(R.drawable.correct_btn_bg)
                        }
                    }
                }
                binding.btnNextQuestion.isEnabled = true
                binding.btnNextQuestion.setBackgroundResource(R.drawable.orange_shape)
                binding.imvOrangeGlow.alpha = 1F
            }
        }.start()
    }

    private fun showChatbotDialog(
        generativeModel: GenerativeModel,
        coinValue: Int,
        editor: SharedPreferences.Editor
    ) {

        val view = LayoutInflater.from(this).inflate(R.layout.chat_bot_dialog_box, null)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val chatContainer = view.findViewById<LinearLayout>(R.id.chatContainer)
        val etMessage = view.findViewById<EditText>(R.id.etMessage)
        val btnSend = view.findViewById<ImageView>(R.id.btnSend)

        btnSend.setOnClickListener {
            val message = etMessage.text.toString()
            val prompt = "Context: $this\n\nQuestion: $message"
            if (message.isNotBlank()) {
                val userMessageLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.END
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val marginInDp = 8
                    val scale = resources.displayMetrics.density
                    val marginInPx = (marginInDp * scale).toInt()
                    params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
                    params.gravity = Gravity.END
                    layoutParams = params
                }
                val userMsg = TextView(this).apply {
                    text = message
                    setBackgroundResource(R.drawable.rounded_bg)
                    setPadding(16, 8, 16, 8)
                    setTextColor(Color.BLACK)

                }
                val userImage = ImageView(this).apply {
                    setImageResource(R.drawable.user)
                }
                userMessageLayout.addView(userMsg)
                userMessageLayout.addView(userImage)
                chatContainer.addView(userMessageLayout)
                etMessage.text.clear()

                GlobalScope.launch(Dispatchers.Main) {
                    // Create a loading TextView
                    val botMessageLayout = LinearLayout(this@QuizActivity).apply {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.START
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        val marginInDp = 8
                        val scale = resources.displayMetrics.density
                        val marginInPx = (marginInDp * scale).toInt()
                        params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
                        params.gravity = Gravity.END
                        layoutParams = params
                    }
                    val loadingText = TextView(this@QuizActivity).apply {
                        setBackgroundResource(R.drawable.rounded_bg)
                        setPadding(16, 8, 16, 8)
                        setTextColor(Color.GRAY)
                        text = "..."
                    }
                    val botImage = ImageView(this@QuizActivity).apply {
                        setImageResource(R.drawable.gemini)
                    }
                    botMessageLayout.addView(botImage)
                    botMessageLayout.addView(loadingText)
                    chatContainer.addView(botMessageLayout)
                    // Animate the loading dots
                    val loadingJob = launch {
                        val dots = listOf(".", "..", "...")
                        var index = 0
                        while (isActive) {
                            loadingText.text = dots[index % dots.size]
                            index++
                            delay(500)
                        }
                    }
                    // Fetch response from Gemini in IO thread
                    val answer = withContext(Dispatchers.IO) {
                        val response = generativeModel.generateContent(prompt)
                        response.text ?: "Sorry, I couldn't understand that."
                    }
                    // Stop loading and update the message
                    loadingJob.cancel()

                    loadingText.text = answer
                    loadingText.setTextColor(Color.BLACK)

                    val newCoinValue = coinValue - 500
                    editor.putInt("coin",newCoinValue)
                    editor.apply()
                }
            }
        }

        dialog.show()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

}