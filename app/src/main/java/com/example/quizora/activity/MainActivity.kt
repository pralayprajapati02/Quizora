package com.example.quizora.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizora.R
import com.example.quizora.adapter.CategoryAdapter
import com.example.quizora.api.QuizInterface
import com.example.quizora.api.QuizUtilities
import com.example.quizora.databinding.ActivityMainBinding
import com.example.quizora.model.Categories
import com.example.quizora.repository.QuizRepository
import com.example.quizora.utils.InternetConnectivity
import com.example.quizora.viewModel.QuizViewModel
import com.example.quizora.viewModel.QuizViewModelFactory
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private var categoryList = ArrayList<Categories>()

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!InternetConnectivity().isInternetAvailable(this)) {
            Toast.makeText(this, "Please Check Your Internet Connectivity", Toast.LENGTH_SHORT)
                .show()
        }

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar", 0)
        val coinValue = sharedPreferences.getInt("coin", 0)
        val isMusicGlobal = sharedPreferences.getBoolean("music_global", false)

        if (isMusicGlobal) {
            val musicIntent = Intent(this, MusicManager::class.java)
            musicIntent.putExtra("ACTION", "PLAY")
            startService(musicIntent)
        }

        binding.cvUserCoin.setOnClickListener {
            showBalloon()
        }

        binding.tvUserCoins.text = coinValue.toString()

        binding.simvUserAvatar.setImageResource(userAvatar)
        binding.tvUserName.text = userName

        categoryList = com.example.quizora.viewModel.Categories.getData()

        categoryAdapter = CategoryAdapter(this, categoryList)
        binding.rvCategory.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvCategory.adapter = categoryAdapter

        binding.simvUserAvatar.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }


        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    true
                }

                R.id.randomQuiz -> {
                    val intent = Intent(this, QuizActivity::class.java)
                    intent.putExtra("amount", 10)
                    intent.putExtra("category", null as Int?)
                    intent.putExtra("difficulty", null as String?)
                    intent.putExtra("type", null as String?)
                    this.startActivity(intent)
                    true
                }

                R.id.customQuiz -> {
                    this.startActivity(Intent(this, CustomQuizActivity::class.java))
                    true
                }

                R.id.setting -> {
                    this.startActivity(Intent(this, SettingActivity::class.java))
                    true
                }

                else -> false
            }

        }


    }

    private fun showBalloon() {
        val balloon = Balloon.Builder(this)
            .setArrowSize(10)
            .setWidthRatio(0.6f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPosition(0.85f)
            .setCornerRadius(15f)
            .setMarginRight(10)
            .setPadding(8)
            .setTextGravity(0)
            .setText("You can earn coins by playing and completing quizzes!\uD83E\uDE99✨\n\nUse coins to unlock the AI ChatBot, which can help you answer tough questions correctly.\uD83E\uDD16\uD83D\uDCAC")
            .setTextColorResource(R.color.black)
            .setBackgroundColorResource(R.color.orange)
            .setTextSize(15f)
            .setDismissWhenClicked(true)
            .setLifecycleOwner(this)
            .build()

        balloon.showAlignBottom(binding.cvUserCoin)
    }

    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar", 0)
        val coinValue = sharedPreferences.getInt("coin", 0)
        binding.tvUserCoins.text = coinValue.toString()
        binding.bottomNavigationView.selectedItemId = R.id.home
        binding.simvUserAvatar.setImageResource(userAvatar)
        binding.tvUserName.text = userName

        //Music
        val playAllTime = sharedPreferences.getBoolean("MUSIC_ALL_TIME", false)
        val playWhileQuiz = sharedPreferences.getBoolean("MUSIC_WHILE_PLAYING", false)

        if (playAllTime || (playWhileQuiz && this is QuizActivity)) {
            MusicManager.start(this)
        } else {
            MusicManager.stop()
        }
    }

    override fun onPause() {
        super.onPause()
        MusicManager.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.release()
    }


}