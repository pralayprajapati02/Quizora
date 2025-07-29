package com.example.quizora.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.R
import com.example.quizora.databinding.ActivitySettingBinding
import androidx.core.content.edit

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

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
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar",0)

        binding.musicPlay.isChecked = sharedPreferences.getBoolean("MUSIC_WHILE_PLAYING", true)
        binding.musicAllTime.isChecked = sharedPreferences.getBoolean("MUSIC_ALL_TIME", false)

        // "Only while Playing" switch
        binding.musicPlay.setOnCheckedChangeListener { _, isChecked ->
            // Optionally disable the global music switch if this one is on
            if (isChecked) binding.musicAllTime.isChecked = false
            sharedPreferences.edit { putBoolean("MUSIC_WHILE_PLAYING", isChecked) }
            //Music
            val playAllTime = sharedPreferences.getBoolean("MUSIC_ALL_TIME", false)
            val playWhileQuiz = sharedPreferences.getBoolean("MUSIC_WHILE_PLAYING", false)

            if (playAllTime || (playWhileQuiz && this is QuizActivity)) {
                MusicManager.start(this)
            } else {
                MusicManager.stop()
            }

            // Only stop if not in all-time mode
            if (!playAllTime) {
                MusicManager.stop()
            }
        }

        // "All the time" switch
        binding.musicAllTime.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.musicPlay.isChecked = false
            sharedPreferences.edit { putBoolean("MUSIC_ALL_TIME", isChecked) }

            val musicIntent = Intent(this, MusicManager::class.java)
            musicIntent.putExtra("ACTION", if (isChecked) "PLAY" else "PAUSE")
            startService(musicIntent)

            //Music
            val playAllTime = sharedPreferences.getBoolean("MUSIC_ALL_TIME", false)
            val playWhileQuiz = sharedPreferences.getBoolean("MUSIC_WHILE_PLAYING", false)

            if (playAllTime || (playWhileQuiz && this is QuizActivity)) {
                MusicManager.start(this)
            } else {
                MusicManager.stop()
            }

            // Only stop if not in all-time mode
            if (!playAllTime) {
                MusicManager.stop()
            }
        }

        binding.simvUserAvatar.setImageResource(userAvatar)
        binding.tvUserName.text = String.format("Hi, $userName")

        binding.cvEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.tvQuizRules.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setView(LayoutInflater.from(this).inflate(R.layout.quiz_rules_dialog_box, null))
                .setCancelable(true)
                .create()

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar",0)
        binding.simvUserAvatar.setImageResource(userAvatar)
        binding.tvUserName.text = String.format("Hi, $userName")

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
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val playAllTime = sharedPreferences.getBoolean("MUSIC_ALL_TIME", false)

        // Only stop if not in all-time mode
        if (!playAllTime) {
            MusicManager.stop()
        }
    }
}