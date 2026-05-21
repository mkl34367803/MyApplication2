package com.example.playvideotest

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class FitVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : VideoView(context, attrs, defStyleAttr) {

    private var videoWidth = 0
    private var videoHeight = 0

    fun setVideoSize(width: Int, height: Int) {
        videoWidth = width
        videoHeight = height
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (videoWidth == 0 || videoHeight == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val videoRatio = videoWidth.toFloat() / videoHeight.toFloat()

        val resultWidth: Int
        val resultHeight: Int

        when {
            widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY -> {
                val layoutRatio = width.toFloat() / height.toFloat()
                if (videoRatio > layoutRatio) {
                    resultWidth = width
                    resultHeight = (width / videoRatio).toInt()
                } else {
                    resultHeight = height
                    resultWidth = (height * videoRatio).toInt()
                }
            }
            widthMode == MeasureSpec.EXACTLY -> {
                resultWidth = width
                resultHeight = (width / videoRatio).toInt()
            }
            heightMode == MeasureSpec.EXACTLY -> {
                resultHeight = height
                resultWidth = (height * videoRatio).toInt()
            }
            else -> {
                resultWidth = videoWidth
                resultHeight = videoHeight
            }
        }

        setMeasuredDimension(resultWidth, resultHeight)
    }
}
