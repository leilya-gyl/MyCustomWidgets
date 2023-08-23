package com.example.mycustomwidgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.widget.ProgressBar

class RoundedProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr) {

    private var arcRect : RectF? = null

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getColor(R.color.colorOne)
        }
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getColor(R.color.neon_body)
        }
        strokeCap = Paint.Cap.ROUND
        strokeWidth = trackPaint.strokeWidth
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        arcRect?.let { canvas?.drawArc(it, 0f, 360f, false, trackPaint) }
         val ringAngle = (progress / max * 360f).coerceAtMost(360f)
        arcRect?.let { canvas?.drawArc(it, 0f, ringAngle, false, ringPaint) }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val d = trackPaint.strokeWidth / 2f
        arcRect?.set(d, d, w -d, h - d)
    }
}