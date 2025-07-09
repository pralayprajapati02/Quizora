package com.example.quizora.activity

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.Surface
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar",0)

        if (userName != null && userAvatar!=0) {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        val display = (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val rotation = display.rotation

        val density = resources.displayMetrics.density
        val marginPx = (5 * density).toInt()

        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180 ){

            val layoutParamsTopLeftView = binding.imvCLTopLeft.layoutParams
            layoutParamsTopLeftView.width = (Resources.getSystem().displayMetrics.widthPixels - (marginPx * 3))/ 2
            binding.imvCLTopLeft.layoutParams = layoutParamsTopLeftView

            val layoutParamsTopRightView = binding.imvCLTopRight.layoutParams
            layoutParamsTopRightView.width = (Resources.getSystem().displayMetrics.widthPixels - (marginPx * 3)) / 2
            binding.imvCLTopRight.layoutParams = layoutParamsTopRightView

        }else{

            val layoutParamsTopLeftView = binding.clTopLeft.layoutParams
            layoutParamsTopLeftView.height = ((Resources.getSystem().displayMetrics.heightPixels - (marginPx * 3))/ 1.14).toInt()
            binding.clTopLeft.layoutParams = layoutParamsTopLeftView

            val layoutParamsTopRightView = binding.imvCLTopRight.layoutParams
            layoutParamsTopRightView.height = ((Resources.getSystem().displayMetrics.heightPixels - (marginPx * 3)) / 2.5).toInt()
            binding.imvCLTopRight.layoutParams = layoutParamsTopRightView

            val layoutParamsBottomRightView = binding.imvCLCenterRight.layoutParams
            layoutParamsBottomRightView.height = ((Resources.getSystem().displayMetrics.heightPixels - (marginPx * 3)) / 2.5).toInt()
            binding.imvCLCenterRight.layoutParams = layoutParamsBottomRightView
        }


        binding.btnGetStart.setOnClickListener {
            startActivity(Intent(this, AvatarSelectionActivity::class.java))
            finish()
        }
    }
}