package com.example.resultsblock.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.example.resultsblock.R
import com.example.resultsblock.databinding.GlowingViewCircleBinding

class CircularLayout : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var _binding: GlowingViewCircleBinding? = null
    val binding: GlowingViewCircleBinding
        get() = _binding!!

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = DataBindingUtil.inflate(inflater, R.layout.glowing_view_circle, this, true)
    }

    override fun onDetachedFromWindow() {
        _binding = null
        super.onDetachedFromWindow()
    }

    fun initViews(value: Int?, type: Int) {
        binding.data = when (type) {
            1 -> { // TOP_PRESSURE
                CustomValues(
                    value = value.toString(),
                    unit = resources.getString(R.string.press_unit),
                    type = resources.getString(R.string.top_press),
                    min = "0",
                    max = resources.getString(R.string.max_tnm)
                )
            }
            2 -> { // LOW_PRESSURE
                CustomValues(
                    value = value.toString(),
                    unit = resources.getString(R.string.press_unit),
                    type = resources.getString(R.string.down_press),
                    min = "0",
                    max = resources.getString(R.string.max_tnm)
                )
            }
            else -> { // PULSE
                CustomValues(
                    value = value.toString(),
                    unit = resources.getString(R.string.pulse_unit),
                    type = resources.getString(R.string.pulse),
                    min = "0",
                    max = resources.getString(R.string.max_tnm)
                )
            }
        }
        binding.progressBar.initViews(value, type)
    }
}