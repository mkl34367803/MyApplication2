package com.example.toolbartest3

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        tvResult = findViewById(R.id.tv_result)

        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            showResult("点击了导航按钮（汉堡菜单）")
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                showResult("点击了【搜索】按钮")
                true
            }
            R.id.action_favorite -> {
                showResult("点击了【收藏】按钮")
                true
            }
            R.id.action_share -> {
                showResult("点击了【分享】按钮")
                true
            }
            R.id.action_settings -> {
                showResult("点击了溢出菜单中的【设置】")
                true
            }
            R.id.action_about -> {
                showResult("点击了溢出菜单中的【关于】")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showResult(message: String) {
        tvResult.text = message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}