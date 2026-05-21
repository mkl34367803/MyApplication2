package com.example.selectmultiphoto

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val photos = mutableListOf<Uri>()
    private lateinit var adapter: PhotoAdapter

    private val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            if (uris.isNotEmpty()) {
                photos.clear()
                photos.addAll(uris)
                adapter.notifyDataSetChanged()
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

        val rvPhotos = findViewById<RecyclerView>(R.id.rv_photos)
        adapter = PhotoAdapter(photos)
        rvPhotos.layoutManager = GridLayoutManager(this, 3)
        rvPhotos.adapter = adapter

        findViewById<Button>(R.id.btn_select_photos).setOnClickListener {
            pickMultipleMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    private class PhotoAdapter(private val items: List<Uri>) :
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

        class ViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = ImageView(parent.context).apply {
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    360
                ).apply {
                    setMargins(8, 8, 8, 8)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.imageView.setImageURI(items[position])
        }

        override fun getItemCount() = items.size
    }
}
