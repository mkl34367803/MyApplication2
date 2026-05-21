package com.example.playvideotest

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: FitVideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView = findViewById(R.id.videoView)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnReplay = findViewById<Button>(R.id.btnReplay)

        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.jk}")
        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mp ->
            videoView.setVideoSize(mp.videoWidth, mp.videoHeight)
        }

        btnPlay.setOnClickListener {
            videoView.start()
        }

        btnPause.setOnClickListener {
            videoView.pause()
        }

        btnReplay.setOnClickListener {
            videoView.seekTo(0)
            videoView.start()
        }
    }
}
