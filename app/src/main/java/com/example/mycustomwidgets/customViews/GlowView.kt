package com.example.mycustomwidgets.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import com.example.mycustomwidgets.R


class GlowView : View {
    private var mCirclePaint: Paint? = null
    private var mAlphaPaint: Paint? = null
    private var mGlowRadius = 0f
    private var mGlowColor = 0
    private val mBounds: Rect = Rect()
    private val mRows: ArrayList<String> = ArrayList()

    private var mWidth = 0f
    private var mHeight = 0f

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

/*
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val circleBitmap =
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val circleCanvas = Canvas(circleBitmap)
        val layout: Layout = getLayout
        var start = 0
        var end: Int
        mRows.clear()
        for (i in 0 until lineCount) {
            end = layout.getLineEnd(i)
            mRows.add(text.substring(start, end))
            start = end
        }
        val fm: Paint.FontMetrics = mTextPaint.getFontMetrics()
        var top: Float = paddingTop + fm.descent - fm.ascent * 0.9f
        var left: Float
        for (row in mRows) {
            if (row.length == 0) {
                continue
            }
            left = paddingLeft.toFloat()
            for (element in row) {
                val curChar = element.toString()
                val curCharWidth: Float = mTextPaint.measureText(curChar)
                if (curChar != " ") {
                    mTextPaint.getTextBounds(curChar, 0, 1, mBounds)
                    circleCanvas.drawCircle(
                        left + (mBounds.right + mBounds.left) / 2,
                        top + (mBounds.bottom + mBounds.top) / 2,
                        mGlowRadius,
                        mCirclePaint
                    )
                }
                left += curCharWidth
            }
            top += lineHeight.toFloat()
        }
        val alphaBitmap = circleBitmap.extractAlpha()
        circleBitmap.recycle()

        canvas.drawBitmap(alphaBitmap, 0, 0, mAlphaPaint)
        alphaBitmap.recycle()

        super.onDraw(canvas)
    }
 */
    var glowRadius: Float
        get() = mGlowRadius
        set(glowRadius) {
            mGlowRadius = glowRadius
            postInvalidate()
        }

    @get:ColorInt
    var glowColor: Int
        get() = mGlowColor
        set(glowColor) {
            mGlowColor = glowColor
            mAlphaPaint?.color = mGlowColor
            postInvalidate()
        }

    private fun init(@Nullable attrs: AttributeSet?) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        if (attrs == null) {
            mGlowRadius = 60f
            mGlowColor = Color.WHITE
        } else {
            @SuppressLint("Recycle") val a: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.GlowView)
            mGlowRadius = a.getDimension(R.styleable.GlowView_glowRadius, 60f)
            mGlowColor = a.getColor(R.styleable.GlowView_glowColor, Color.WHITE)
            mWidth = a.getFloat(R.styleable.GlowView_glowWidth, 10f)
            mHeight = a.getFloat(R.styleable.GlowView_glowHeight, 100f)
        }
        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mAlphaPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mAlphaPaint!!.color = mGlowColor
        mAlphaPaint!!.maskFilter = BlurMaskFilter(mGlowRadius, BlurMaskFilter.Blur.NORMAL)
    }
}