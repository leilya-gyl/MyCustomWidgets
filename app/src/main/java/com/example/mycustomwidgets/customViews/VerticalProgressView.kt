package com.example.mycustomwidgets.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.mycustomwidgets.R

class VerticalProgressView : androidx.appcompat.widget.AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mContext: Context? = null
    private var paint: Paint? = null
    private var rectf: RectF? = null

    private var maximalValue = 1
    private var level = 0
    private var mWidth = 0
    private var mHeight = 0

    private var glowColor: Int = ContextCompat.getColor(context, R.color.neon_glow) // defaultValue

    init {
        mContext = context
        paint = Paint()
        rectf = RectF()
        paint!!.color = Color.GRAY
        paint!!.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        val dif = mHeight.toFloat() / maximalValue.toFloat()
        val newHeight = mHeight - (dif * level).toInt()
        rectf?.set(0f, newHeight.toFloat(), mWidth.toFloat(), mHeight.toFloat())
        //rectf!![0f, newHeight.toFloat(), width.toFloat()] = height.toFloat()
        paint?.setShadowLayer(
            mWidth.toFloat(),
            0f,
            0f,
            glowColor
        )
        canvas.drawRect(rectf!!, paint!!)
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mWidth = w
        this.mHeight = h
    }

    fun setMaximalValue(maximalValue: Int) {
        this.maximalValue = maximalValue
        invalidate()
    }

    fun setLevel(level: Int) {
        this.level = level
        invalidate()
    }

    fun setColorResource(_color: Int) {
        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mContext!!.resources.getColor(_color, mContext!!.theme)
        } else {
            ContextCompat.getColor(mContext!!, R.color.neon_body)
        }
        setColor(color)
    }

    fun setColor(color: Int) {
        if (paint != null) {
            paint!!.color = color
            invalidate()
        }
    }
}