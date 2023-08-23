package com.example.mycustomwidgets.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.mycustomwidgets.R
import kotlin.math.min

class GlowVerticalProgress : View {
    constructor(context: Context) : super(context) {
        initAttrs(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    private val rect = RectF()
    private var mWidth = width
    private var mHeight = height
    private var padding = min(mWidth, mHeight) / 3f
    private var mStrokeWidth = mWidth / 3f
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
        color = ContextCompat.getColor(context, R.color.colorBack)
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.FILL
    }
    private var glowColor = ContextCompat.getColor(context, R.color.neon_glow)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        frontPaint.setShadowLayer(padding - 20, 0f, 0f, glowColor)
        canvas?.drawRect(rect, backPaint)
        val progressDx = calculateProgressDx(progress)
        canvas?.drawRect(RectF(padding, mHeight - padding - progressDx, mWidth - padding, mHeight - padding), frontPaint)
    }

    private fun calculateProgressDx(progressF: Float) = (mHeight - 2 * padding) * progressF / maxValue

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        mWidth = width
        mHeight = height
        padding = min(mWidth, mHeight) / 3f
        mStrokeWidth = mWidth / 3f
        updateRect()
    }

    private fun updateRect(){
        rect.set(padding, padding, mWidth - padding, mHeight - padding)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.GlowVerticalProgress)
        val value = ta.getFloat(R.styleable.GlowVerticalProgress_testValue, 0f)
        val type = ta.getInt(R.styleable.GlowVerticalProgress_testType, 0)

        maxValue = if (type == 1) 200f else 400f
        progress = if (type == 1) value * 1000f else value * 10f

        frontPaint.color = chooseBodyColor(type, value)
        glowColor = chooseGlowColor(type, value)
        ta.recycle()
    }

    private fun chooseBodyColor(type: Int, progress: Float): Int {
        return if (type == 1) {
            if (progress < 0.05f) ContextCompat.getColor(context, R.color.neon_body_two)
            else ContextCompat.getColor(context, R.color.neon_body_three)
        } else {
            if (progress < 35.2f) {
                ContextCompat.getColor(context, R.color.neon_body)
            } else if (progress < 37f) {
                ContextCompat.getColor(context, R.color.neon_body_two)
            } else ContextCompat.getColor(context, R.color.neon_body_three)
        }
    }

    private fun chooseGlowColor(type: Int, progress: Float): Int {
        return if (type == 1) {
            if (progress < 0.05f) ContextCompat.getColor(context, R.color.neon_glow_two)
            else ContextCompat.getColor(context, R.color.neon_glow_three)
        } else {
            if (progress < 35.2f) {
                ContextCompat.getColor(context, R.color.neon_glow)
            } else if (progress < 37f) {
                ContextCompat.getColor(context, R.color.neon_glow_two)
            } else ContextCompat.getColor(context, R.color.neon_glow_three)
        }
    }
}