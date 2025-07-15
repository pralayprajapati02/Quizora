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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var quizViewModel : QuizViewModel
    private lateinit var categoryAdapter :CategoryAdapter
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

        if(!InternetConnectivity().isInternetAvailable(this)){
            Toast.makeText(this, "Please Check Your Internet Connectivity", Toast.LENGTH_SHORT).show()
        }

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "")
        val userAvatar = sharedPreferences.getInt("user_avatar",0)
        val coinValue = sharedPreferences.getInt("coin",0)

        binding.tvUserCoins.text = coinValue.toString()

        binding.simvUserAvatar.setImageResource(userAvatar)
        binding.tvUserName.text = userName

        categoryList = com.example.quizora.viewModel.Categories.getData()

        categoryAdapter = CategoryAdapter(this,categoryList)
        binding.rvCategory.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.rvCategory.adapter = categoryAdapter

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.randomQuiz->{
                    val intent = Intent(this, QuizActivity::class.java)
                    intent.putExtra("amount", 10)
                    intent.putExtra("category", null as Int?)
                    intent.putExtra("difficulty", null as String?)
                    intent.putExtra("type", null as String?)
                    this.startActivity(intent)
                    finish()
                    true
                }
                R.id.customQuiz->{
                    val intent = Intent(this, QuizResultActivity::class.java)
                    intent.putExtra("Score", 10)
                    this.startActivity(intent)
                    finish()
                    true
                }
                R.id.setting->{
                    true
                }
                else -> false
            }

        }


    }

    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
    }
}