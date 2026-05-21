package com.example.playaudiotest

import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var note1: TextView
    private lateinit var note2: TextView
    private lateinit var note3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        note1 = findViewById(R.id.note1)
        note2 = findViewById(R.id.note2)
        note3 = findViewById(R.id.note3)

        mediaPlayer = MediaPlayer()
        val afd = assets.openFd("T.R.Y、郑源 - 爱情里没有谁对谁错.mp3")
        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        mediaPlayer.prepare()

        findViewById<android.widget.Button>(R.id.btnPlay).setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                startNoteAnimations()
            }
        }

        findViewById<android.widget.Button>(R.id.btnPause).setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                stopNoteAnimations()
            }
        }

        findViewById<android.widget.Button>(R.id.btnStop).setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
            stopNoteAnimations()
        }
    }

    private fun startNoteAnimations() {
        note1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce))
        note2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce2))
        note3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce3))
    }

    private fun stopNoteAnimations() {
        note1.clearAnimation()
        note2.clearAnimation()
        note3.clearAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
