package com.example.quizora.viewModel

import com.example.quizora.R
import com.example.quizora.model.Categories

object Categories {
    private lateinit var dataList : ArrayList<Categories>
    fun getData():ArrayList<Categories>{
        dataList = ArrayList()
        dataList.add(Categories(27, R.color.animal,"Animals", R.drawable.animals,"350\nQuestions"))
        dataList.add(Categories(31, R.color.anime_and_manga,"Anime & Manga", R.drawable.anime_and_manga,"761\nQuestions"))
        dataList.add(Categories(25, R.color.art,"Art", R.drawable.art,"207\nQuestions"))
        dataList.add(Categories(16, R.color.board_and_games,"Board Games", R.drawable.board_games,"246\nQuestions"))
        dataList.add(Categories(10, R.color.books,"Books", R.drawable.books,"496\nQuestions"))
        dataList.add(Categories(32, R.color.cartoon_and_animation,"Cartoon & Animation", R.drawable.cartoon_and_animation,"324\nQuestions"))
        dataList.add(Categories(26, R.color.celebrities,"Celebrities", R.drawable.celebrities,"240\nQuestions"))
        dataList.add(Categories(29, R.color.comic,"Comic", R.drawable.comic,"180\nQuestions"))
        dataList.add(Categories(18, R.color.computer,"Computer", R.drawable.computer,"901\nQuestions"))
        dataList.add(Categories(11, R.color.film,"Film", R.drawable.film,"1081\nQuestions"))
        dataList.add(Categories(30, R.color.gadgets,"Gadgets", R.drawable.gadgets,"150\nQuestions"))
        dataList.add(Categories(9, R.color.general_knowledge,"General Knowledge", R.drawable.general_knowledge,"5207\nQuestions"))
        dataList.add(Categories(22, R.color.geography,"Geography", R.drawable.geography,"811\nQuestions"))
        dataList.add(Categories(23, R.color.history,"History", R.drawable.history,"957\nQuestions"))
        dataList.add(Categories(19, R.color.mathematics,"Mathematics", R.drawable.mathematics,"382\nQuestions"))
        dataList.add(Categories(12, R.color.music,"Music", R.drawable.music,"1220\nQuestions"))
        dataList.add(Categories(13, R.color.musical_and_theaters,"Musical & Theaters", R.drawable.musical_and_theaters,"134\nQuestions"))
        dataList.add(Categories(20, R.color.mythology,"Mythology", R.drawable.mythology,"213\nQuestions"))
        dataList.add(Categories(24, R.color.politics,"Politics", R.drawable.politics,"330\nQuestions"))
        dataList.add(Categories(17, R.color.science_and_nature,"Science & Nature", R.drawable.science_and_nature,"893\nQuestions"))
        dataList.add(Categories(21, R.color.sports,"Sports", R.drawable.sports,"789\nQuestions"))
        dataList.add(Categories(14, R.color.television,"Television", R.drawable.tv,"748\nQuestions"))
        dataList.add(Categories(28, R.color.vehicle,"Vehicle", R.drawable.vehicle,"300\nQuestions"))
        dataList.add(Categories(15, R.color.video_game,"Video Game", R.drawable.video_game,"3929\nQuestions"))
        return dataList
    }

}