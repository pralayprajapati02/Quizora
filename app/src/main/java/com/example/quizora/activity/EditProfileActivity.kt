package com.example.quizora.activity

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Surface
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizora.R
import com.example.quizora.adapter.AvatarAdapter
import com.example.quizora.databinding.ActivityEditProfileBinding
import com.example.quizora.viewModel.Avatar

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var avatarAdapter: AvatarAdapter

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
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar",0)
        val editor = sharedPreferences.edit()
        var avatarImgValue = 0

        binding.simvAvatar.setImageResource(userAvatar)
        binding.ettUsername.setText(userName)
        binding.tvCharacterCount.text = "${userName?.length}/12"

        val display = (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val rotation = display.rotation

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270 ){

            val layoutParamsTopTextView = binding.tvChooseAvatar.layoutParams
            layoutParamsTopTextView.width = ((Resources.getSystem().displayMetrics.widthPixels) / 2)
            binding.tvChooseAvatar.layoutParams = layoutParamsTopTextView

        }

        // Set a text change listener for the EditText
        binding.ettUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not needed for this case
            }
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Update the TextView with the current character count
                val currentLength = s.length
                binding.tvCharacterCount.text = "$currentLength/12"
            }
            override fun afterTextChanged(s: Editable) {
                // Not needed for this case
            }
        })

        avatarAdapter = AvatarAdapter(this,Avatar.getData()){ intent->
            val bundle = intent.extras
            if (bundle != null) {
                avatarImgValue = bundle.getInt("selected_img")
                binding.simvAvatar.setImageResource(avatarImgValue)
            }
        }
        binding.rvAvatar.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvAvatar.adapter = avatarAdapter

        binding.btnSave.setOnClickListener {
            val userName = binding.ettUsername.text
            if (avatarImgValue>0 && userName.isNotEmpty()){
                editor.putInt("user_avatar", avatarImgValue)
                editor.putString("user_name", userName.toString())
                editor.apply()
                onBackPressedDispatcher.onBackPressed()
            }else if (userName.isNotEmpty()){
                editor.putInt("user_avatar", userAvatar)
                editor.putString("user_name", userName.toString())
                editor.apply()
                onBackPressedDispatcher.onBackPressed()
            }else{
                Toast.makeText(this, "Please Fill User Name and save", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Music
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
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