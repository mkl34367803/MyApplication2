package com.example.toolbartest2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "返回按钮被点击", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_share -> {
                    Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_settings -> {
                    Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_about -> {
                    Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}
