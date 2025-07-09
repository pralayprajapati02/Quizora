package com.example.quizora.activity

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizora.R
import com.example.quizora.adapter.AvatarAdapter
import com.example.quizora.databinding.ActivityAvatarSelectionBinding
import com.example.quizora.viewModel.Avatar

class AvatarSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvatarSelectionBinding
    private lateinit var avatarAdapter: AvatarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAvatarSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var avatarImgValue = 0

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
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else if (userName.isNotEmpty()){
                editor.putInt("user_avatar", R.drawable.green_tshirt_curly_hair_boy)
                editor.putString("user_name", userName.toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Please Fill User Name and save", Toast.LENGTH_LONG).show()
            }
            editor.apply()
            val saveAvatar = sharedPreferences.getInt("user_avatar", 0 )
            Log.d("userAvatarInt", saveAvatar.toString())
            Log.d("userName", userName.toString())
        }

    }
}