package com.example.quizora.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizora.activity.QuizActivity
import com.example.quizora.databinding.CategoriesListBinding
import com.example.quizora.model.Categories
import kotlin.collections.ArrayList

class CategoryAdapter(
    var context: Context,
    private var categoryList: ArrayList<Categories>,
) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: CategoriesListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = CategoriesListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = categoryList[position]

        holder.binding.tvCategoryName.text = data.categoryName
        holder.binding.tvCategoryQuestionNo.text = data.noOfQuestions
        val color = ContextCompat.getColor(holder.itemView.context, data.cardColor)
        holder.binding.clCategory.setBackgroundColor(color)
        holder.binding.imgCategoryIcon.setImageResource(data.categoryIcon)
        holder.binding.imgCategoryIcon.animation

        holder.binding.cvCategory.setOnClickListener {
            val intent = Intent(context, QuizActivity::class.java)
            intent.putExtra("amount", 10)
            intent.putExtra("category", data.categoryNo)
            intent.putExtra("difficulty", null as String?)
            intent.putExtra("type", null as String?)
            context.startActivity(intent)
        }

    }

}