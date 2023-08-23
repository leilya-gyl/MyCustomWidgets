package com.example.mycustomwidgets.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.mycustomwidgets.R
import com.example.mycustomwidgets.databinding.CircleGlowingViewBinding

class CircularLayout : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var _binding: CircleGlowingViewBinding? = null
    val binding: CircleGlowingViewBinding
        get() = _binding!!
    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = DataBindingUtil.inflate(inflater, R.layout.circle_glowing_view, this, true)
    }
}

@BindingAdapter("app:customStrokeWidth")
fun CircularLayout.setCustomWidth(width: Float) {
    binding.progressBar.setProgressWidth(width)
}

@BindingAdapter("app:initialData")
fun CircularLayout.setInitialData(data: CustomValues) {
    binding.data = data
}

@BindingAdapter("app:viewColors")
fun CircularLayout.setColor(customColors: CustomColors) {
    binding.color = customColors
}