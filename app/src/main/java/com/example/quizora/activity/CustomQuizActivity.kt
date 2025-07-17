package com.example.quizora.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SpinnerAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.quizora.databinding.ActivityCustomQuizBinding
import com.example.quizora.viewModel.Categories

class CustomQuizActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCustomQuizBinding
    private var categoryList = Categories.getData()
    private var categoriesList = ArrayList<String>()
    private var categoriesNoList = ArrayList<Int>()
    private var amount = 10
    private var category:Int? = null
    private var difficulty:String? = null
    private var type:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCustomQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        categoriesList.add("Any")
        categoriesNoList.add(0)
        for (i in categoryList){
            categoriesList.add(i.categoryName)
            categoriesNoList.add(i.categoryNo)
        }
        val difficultyList = listOf("Any", "Easy","Medium", "Hard")
        val typeList = listOf("Any","Multiple Choice", "True/false")


        binding.categorySpinner.adapter = getSpinnerAdapter(categoriesList)
        binding.difficultiesSpinner.adapter = getSpinnerAdapter(difficultyList)
        binding.typesSpinner.adapter = getSpinnerAdapter(typeList)

        seekbar()
        categorySpinner()
        difficultySpinner()
        typeSpinner()


        binding.btnStartQuiz.setOnClickListener {
            Log.d("amount","$amount")
            Log.d("category","$category")
            Log.d("difficulty","$difficulty")
            Log.d("type","$type")
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("amount", amount)
            intent.putExtra("category", category)
            intent.putExtra("difficulty", difficulty)
            intent.putExtra("type", type.toString())
            startActivity(intent)
        }


    }

    private fun seekbar(){
        binding.seekBarAmount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                amount = progress
                val text = "$amount"
                binding.amount.text = text
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun categorySpinner(){
        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = if (position == 0) null else categoriesNoList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun difficultySpinner() {
        binding.difficultiesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                difficulty = when(position){
                    0 -> null
                    1 -> "easy"
                    2 -> "medium"
                    else -> "hard"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun typeSpinner(){
        binding.typesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                type = when(position){
                    0 -> null
                    1 -> "multiple"
                    else -> "boolean"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun getSpinnerAdapter(list: List<String>):SpinnerAdapter {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }


}
