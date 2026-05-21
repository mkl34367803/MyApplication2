package com.example.servicetest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvProgress: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val progress = intent.getIntExtra(MyService.EXTRA_PROGRESS, 0)
            tvProgress.text = "进度: $progress%"
            if (progress >= 100) {
                tvStatus.text = "服务状态: 已完成"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvStatus = findViewById(R.id.tv_status)
        tvProgress = findViewById(R.id.tv_progress)
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)

        btnStart.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
            tvStatus.text = "服务状态: 运行中"
            tvProgress.text = "进度: 0%"
        }

        btnStop.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
            tvStatus.text = "服务状态: 已停止"
            tvProgress.text = "进度: 0%"
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(MyService.ACTION_UPDATE_PROGRESS)
        registerReceiver(mReceiver, filter, RECEIVER_NOT_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }
}
