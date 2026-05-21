package com.example.servicetest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val mBinder = LocalBinder()
    private var mProgress = 0

    companion object {
        const val TAG = "MyService"
        const val ACTION_UPDATE_PROGRESS = "com.example.servicetest.UPDATE_PROGRESS"
        const val EXTRA_PROGRESS = "extra_progress"
    }

    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: 服务创建")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: 服务启动, startId=$startId")

        // 模拟一个耗时任务
        Thread {
            for (i in 1..10) {
                Thread.sleep(1000)
                mProgress = i * 10
                Log.d(TAG, "任务进度: $mProgress%")

                // 发送广播更新 UI（必须指定包名，否则 RECEIVER_NOT_EXPORTED 接收器收不到）
                val updateIntent = Intent(ACTION_UPDATE_PROGRESS)
                updateIntent.setPackage(packageName)
                updateIntent.putExtra(EXTRA_PROGRESS, mProgress)
                sendBroadcast(updateIntent)
            }
            Log.d(TAG, "任务完成")
            stopSelf()
        }.start()

        // START_STICKY: 服务被杀死后会自动重启
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: 服务绑定")
        return mBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind: 服务解绑")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: 服务销毁")
    }

    fun getProgress(): Int {
        return mProgress
    }
}
