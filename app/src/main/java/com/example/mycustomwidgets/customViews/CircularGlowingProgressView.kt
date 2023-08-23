package com.example.mycustomwidgets.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.mycustomwidgets.R
import kotlin.math.min

class CircularGlowingProgressView : View {
    constructor(context: Context) : super(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val rect = RectF()
    private val startAngle = 135f
    private val maxAngle = 270f
    private val maxProgress = 200

    private var diameter = 0f
    private var angle = 0f

    private var glowColor = ContextCompat.getColor(context, R.color.neon_glow) // defaultValue
    private var glowBackColor = ContextCompat.getColor(context, R.color.purple_500) // defaultValue
    private var progressColor = ContextCompat.getColor(context, R.color.neon_body) // defaultValue
    private var backgroundProgressColor = ContextCompat.getColor(context, R.color.colorOne) // defaultValue
    private var progressWidth = 30F // defaultValue

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = progressColor
        strokeWidth = progressWidth
        style = Paint.Style.STROKE
    }
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundProgressColor
        strokeWidth = progressWidth
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        progressPaint.setShadowLayer(
            progressPaint.strokeWidth,
            0f,
            0f,
            glowColor
        )
//        backgroundPaint.setShadowLayer(
//            backgroundPaint.strokeWidth,
//            0f,
//            0f,
//            glowBackColor
//        )
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

    private fun calculateAngle(progress: Float) = maxAngle / maxProgress * progress

    fun setProgress(@FloatRange(from = 0.0, to = 200.0) progress: Float) {
        angle = calculateAngle(progress)
        invalidate()
    }

    fun setProgressColor(color: Int, _glowColor: Int) {
        progressPaint.color = ContextCompat.getColor(context, color)
        glowColor = ContextCompat.getColor(context, _glowColor)
        invalidate()
    }

    fun setProgressBackgroundColor(color: Int, _glowColor: Int) {
        backgroundPaint.color = ContextCompat.getColor(context, color)
        glowBackColor = ContextCompat.getColor(context, _glowColor)
        invalidate()
    }

    fun setProgressWidth(width: Float) {
        progressWidth = width
        progressPaint.strokeWidth = width
        backgroundPaint.strokeWidth = width
        updateRect()
        invalidate()
    }

    fun setRounded(rounded: Boolean) {
        progressPaint.strokeCap = if (rounded) Paint.Cap.ROUND else Paint.Cap.BUTT
        invalidate()
    }
}

@BindingAdapter("app:customProgress")
fun CircularGlowingProgressView.setCustomProgress(progress: String) {
    setProgress(progress.toFloat())
}
@BindingAdapter("app:customColors")
fun CircularGlowingProgressView.setCustomColors(customColors: CustomColors) {
    setProgressColor(customColors.color, customColors.glow_color)
    setProgressBackgroundColor(customColors.back_color, customColors.back_glow_color)
}