package com.example.resultsblock.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.example.resultsblock.R

class VerticalGlowProgressView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val rect = RectF()
    private var mWidth = width
    private var mHeight = height
    private var mStrokeWidth = 20f
    private var padding = (mWidth - mStrokeWidth) / 2f
    private var progress = 0f
    private var maxValue = 400f

    private var frontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = mStrokeWidth
        color = ContextCompat.getColor(context, R.color.neon_body)
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.FILL
    }
    private var backPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = mStrokeWidth
        color = ContextCompat.getColor(context, R.color.back_color)
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.FILL
    }
    private var glowColor = ContextCompat.getColor(context, R.color.neon_glow)

    // Gradient Shade colors distribution setting uniform for now
    private var positions: FloatArray? = null

    // Gradient Shade colors
    private val colors = intArrayOf(
        ContextCompat.getColor(context,
            R.color.temp_salon_vertical_start),
        ContextCompat.getColor(context,
            R.color.temp_salon_vertical_center),
        ContextCompat.getColor(context,
            R.color.temp_salon_vertical_end)
    )

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //frontPaint.setShadowLayer(frontPaint.strokeWidth, 0f, 0f, glowColor)

        frontPaint.shader = LinearGradient(0f, mHeight.toFloat(), 0f, 0f, colors, positions, Shader.TileMode.MIRROR)

        backPaint.setShadowLayer(backPaint.strokeWidth +20f, 0f, 0f, ContextCompat.getColor(context, R.color.colorPrimaryVariant))
        canvas?.drawRect(rect, backPaint)
        val progressDx = calculateProgressDx(progress)
        canvas?.drawRect(RectF(padding, mHeight - padding - progressDx, mWidth - padding, mHeight - padding), frontPaint)
    }

    private fun calculateProgressDx(progressF: Float) = (mHeight - 2 * padding) * progressF / maxValue

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        mWidth = width
        mHeight = height
        padding = (mWidth - mStrokeWidth) / 2f
        mStrokeWidth = 20f
        updateRect()
    }

    private fun updateRect(){
        rect.set(padding, padding, mWidth - padding, mHeight - padding)
    }

    fun initViews(value: Float?, type: Int) {
        positions = when(type) {
            1 -> floatArrayOf(0f, calculateProgressDx(0f), calculateProgressDx(16f))
            else -> floatArrayOf(0f, calculateProgressDx(352f), calculateProgressDx(371f))
        }

        maxValue = if (type == 1) 200f else 400f
        if (value != null) {
            progress = if (type == 1) value.times(1000f) else value.times(10f)
        }

//        val color = determineColor(type, progress)
//        frontPaint.color = color.component1()
//        glowColor = color.component2()
    }

    private fun determineColor(type: Int, progress: Float): Point {
        val (min, max) = when(type) {
            0 -> Point(352, 371)
            1 -> Point(0, 16)
            else -> Point(0, 0)
        }
        return if (progress < min) {
            Point(ContextCompat.getColor(context, R.color.neon_body), ContextCompat.getColor(context, R.color.neon_glow))
        } else if (progress < max) {
            Point(ContextCompat.getColor(context, R.color.neon_body_two), ContextCompat.getColor(context, R.color.neon_glow_two))
        } else Point(ContextCompat.getColor(context, R.color.neon_body_three), ContextCompat.getColor(context, R.color.neon_glow_three))
    }
}