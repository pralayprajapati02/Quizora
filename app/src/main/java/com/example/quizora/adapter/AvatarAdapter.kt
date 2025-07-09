package com.example.quizora.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizora.activity.AvatarSelectionActivity
import com.example.quizora.databinding.AvatarsLayoutBinding
import com.example.quizora.model.Avatar
import com.squareup.picasso.Picasso

class AvatarAdapter(private val context: Context, private var imageList: ArrayList<Avatar>,private val listener: (Intent) -> Unit) :
    RecyclerView.Adapter<AvatarAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: AvatarsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AvatarsLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data = imageList[position]

        Picasso.get()
            .load(data.image)
            .resize(1024, 1024)
            .into(holder.binding.simvAvatar)


        holder.binding.simvAvatar.setOnClickListener {
            val intent = Intent(context, AvatarSelectionActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("selected_img", data.image)
            intent.putExtras(bundle)
            listener(intent)
        }

    }

}