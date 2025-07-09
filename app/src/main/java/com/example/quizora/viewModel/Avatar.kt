package com.example.quizora.viewModel

import com.example.quizora.R
import com.example.quizora.model.Avatar

object Avatar {
    private lateinit var dataList : ArrayList<Avatar>
    fun getData():ArrayList<Avatar>{
        dataList = ArrayList<Avatar>()
        dataList.add(Avatar(R.drawable.green_tshirt_curly_hair_boy))
        dataList.add(Avatar(R.drawable.yello_tshirt_scarf_hair_girl))
        dataList.add(Avatar(R.drawable.pink_tshirt_braid_hair_girl))
        dataList.add(Avatar(R.drawable.green_tshirt_normal_hair_boy))
        dataList.add(Avatar(R.drawable.blue_tshirt_afro_hair_girl))
        dataList.add(Avatar(R.drawable.orange_tshirt_open_hair_girl))
        dataList.add(Avatar(R.drawable.blue_tshirt_normal_beard_hair_boy))
        dataList.add(Avatar(R.drawable.green_tshir_top_knot_braid_hair_girl))
        dataList.add(Avatar(R.drawable.white_pink_tshirt_top_knot_hair_girl))
        dataList.add(Avatar(R.drawable.white_tshirt_curly_hair_boy))
        dataList.add(Avatar(R.drawable.blue_tshirt_bubble_braid_hair_girl))
        dataList.add(Avatar(R.drawable.yello_tshirt_purple_hair_girl))
        dataList.add(Avatar(R.drawable.blue_tshirt_bald_hair_boy))
        return dataList
    }
}