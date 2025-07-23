package com.example.quizora.activity

import android.content.Context
import android.media.MediaPlayer
import com.example.quizora.R

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.applicationContext, R.raw.background_music)
            mediaPlayer?.isLooping = true
        }
        if (mediaPlayer?.isPlaying == false) mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}