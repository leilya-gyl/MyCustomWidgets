package com.example.resultsblock.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.example.resultsblock.R
import com.example.resultsblock.databinding.GlowingViewVerticalBinding

class VerticalLayout : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var _binding: GlowingViewVerticalBinding? = null
    val binding: GlowingViewVerticalBinding
        get() = _binding!!

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = DataBindingUtil.inflate(inflater, R.layout.glowing_view_vertical, this, true)
    }

    override fun onDetachedFromWindow() {
        _binding = null
        super.onDetachedFromWindow()
    }

    fun initViews(value: Float?, type: Int) {
        binding.data = when (type) {
            1 -> { // ALC
                CustomValues(
                    value = value.toString(),
                    unit = resources.getString(R.string.alc_unit),
                    type = resources.getString(R.string.alc_device),
                    min = "0",
                    max = resources.getString(R.string.max_alc)
                )
            }
            else -> { // Therm
                CustomValues(
                    value = value.toString(),
                    unit = resources.getString(R.string.term_unit),
                    type = resources.getString(R.string.temp_device),
                    min = "0",
                    max = resources.getString(R.string.max_t)
                )
            }
        }
        binding.progressView.initViews(value, type)
    }
}