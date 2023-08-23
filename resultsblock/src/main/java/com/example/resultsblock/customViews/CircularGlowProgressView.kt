package com.example.resultsblock.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.example.resultsblock.R
import kotlin.math.min

class CircularGlowProgressView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.neon_body) // defaultValue
        style = Paint.Style.STROKE
    }
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.back_color) // defaultValue
        style = Paint.Style.STROKE
    }

    private val rect = RectF()
    private val startAngle = 135f
    private val maxAngle = 270f
    private val maxProgress = 200f

    private var diameter = 0f
    private var angle = 0f

    private var glowColor: Int = ContextCompat.getColor(context, R.color.neon_glow) // defaultValue
    private var glowBackColor: Int = ContextCompat.getColor(context, R.color.colorPrimaryVariant) // defaultValue

    // Gradient Shade colors distribution setting uniform for now
    private var positions: FloatArray? = null

    // Gradient Shade colors
    private var colors = intArrayOf(
        ContextCompat.getColor(context,
            R.color.temp_salon_vertical_start),
        ContextCompat.getColor(context,
            R.color.temp_salon_vertical_center),
        ContextCompat.getColor(context,
            R.color.temp_salon_vertical_end)
    )

    private lateinit var mBodyGradient: SweepGradient

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
//        progressPaint.setShadowLayer(
//            progressPaint.strokeWidth,
//            0f,
//            0f,
//            glowColor
//        )
        val centerX = (width / 2).toFloat()
        val centerY = height.toFloat()
        mBodyGradient = SweepGradient(centerX, centerY, colors, positions)
        progressPaint.shader = mBodyGradient

        backgroundPaint.setShadowLayer(
            backgroundPaint.strokeWidth +20f,
            0f,
            0f,
            glowBackColor
        )
        drawCircle(maxAngle, canvas, backgroundPaint)
        drawCircle(angle, canvas, progressPaint)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        diameter = min(width, height).toFloat()
        updateRect()
    }

    private fun updateRect() {
        val strokeWidth = backgroundPaint.strokeWidth
        rect.set(strokeWidth+25, strokeWidth+25, diameter - strokeWidth-25, diameter - strokeWidth-25)
    }

    private fun drawCircle(angle: Float, canvas: Canvas, paint: Paint) {
        canvas.drawArc(rect, startAngle, angle, false, paint)
    }

    private fun calculateAngle(progress: Float) = (maxAngle / maxProgress * progress)

    private fun setProgress(@FloatRange(from = 0.0, to = 200.0) progress: Float) {
        angle = calculateAngle(progress)
        invalidate()
    }

    private fun setProgressColor(color: Int, _glowColor: Int) {
        progressPaint.color = color
        glowColor = _glowColor
        invalidate()
    }

    fun setProgressBackgroundColor(color: Int, _glowColor: Int) {
        backgroundPaint.color = ContextCompat.getColor(context, color)
        glowBackColor = ContextCompat.getColor(context, _glowColor)
        invalidate()
    }

    private fun setProgressWidth(width: Float) {
        progressPaint.strokeWidth = width
        backgroundPaint.strokeWidth = width
        updateRect()
        invalidate()
    }

    fun setRounded(rounded: Boolean) {
        progressPaint.strokeCap = if (rounded) Paint.Cap.ROUND else Paint.Cap.BUTT
        invalidate()
    }

    fun initViews(value: Int?, type: Int) {

        positions = when(type) {
            1 -> floatArrayOf(0f, calculateAngle(80f), calculateAngle(140f))
            else -> floatArrayOf(0f, calculateAngle(60f), calculateAngle(90f))
        }
        setProgressWidth(20f)
        value?.let { setProgress(it.toFloat()) }
        val colorArray = determineColor(type, value)
        //setProgressColor(color.component1(), color.component2())
        setArcColors(colorArray)
    }

    private fun setArcColors(colorsArray: IntArray) {
        colors = colorsArray
    }

    private fun determineColor(type: Int, progress: Int?): IntArray {
        val (min, max) = when(type) {
            0 -> Point(60, 90)
            1 -> Point(100, 140)
            2 -> Point(60, 90)
            else -> Point(0, 0)
        }
        return if (progress != null) {
            if (progress < min) {
                intArrayOf(ContextCompat.getColor(context, R.color.start_one), ContextCompat.getColor(context, R.color.center_one), ContextCompat.getColor(context, R.color.end_one))
            } else if (progress < max) {
                intArrayOf(ContextCompat.getColor(context, R.color.start_two), ContextCompat.getColor(context, R.color.center_two), ContextCompat.getColor(context, R.color.end_two))
            } else intArrayOf(ContextCompat.getColor(context, R.color.start_three), ContextCompat.getColor(context, R.color.center_three), ContextCompat.getColor(context, R.color.end_three))
        } else intArrayOf(ContextCompat.getColor(context, R.color.start_one), ContextCompat.getColor(context, R.color.center_one), ContextCompat.getColor(context, R.color.end_one))
    }

    /*
    private fun determineColor(type: Int, progress: Int?): Point {
        val (min, max) = when(type) {
            0 -> Point(60, 90)
            1 -> Point(100, 140)
            2 -> Point(60, 90)
            else -> Point(0, 0)
        }
        return if (progress != null) {
            if (progress < min) {
                Point(ContextCompat.getColor(context, R.color.neon_body), ContextCompat.getColor(context, R.color.neon_glow))
            } else if (progress < max) {
                Point(ContextCompat.getColor(context, R.color.neon_body_two), ContextCompat.getColor(context, R.color.neon_glow_two))
            } else Point(ContextCompat.getColor(context, R.color.neon_body_three), ContextCompat.getColor(context, R.color.neon_glow_three))
        } else Point(ContextCompat.getColor(context, R.color.neon_body), ContextCompat.getColor(context, R.color.neon_glow))
    }
     */
}