package com.example.mycustomwidgets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mycustomwidgets.customViews.CustomColors
import com.example.mycustomwidgets.customViews.CustomValues
import com.example.mycustomwidgets.customViews.VerticalProgressView
import com.example.mycustomwidgets.databinding.ActivityMainBinding
import com.example.mycustomwidgets.databinding.VerticalProgViewBinding

class MainActivity : AppCompatActivity() {
//    private var _binding: ActivityMainBinding? = null
//    private val binding: ActivityMainBinding
//        get() = _binding!!

    private var _binding: VerticalProgViewBinding? = null
    private val binding: VerticalProgViewBinding
        get() = _binding!!

    private lateinit var progressView: View
    private lateinit var progressView2: VerticalProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.vertical_prog_view, null, false)
        setContentView(binding.root)

        binding.progressView.scaleY = 0.3f
//        binding.data = CustomValues(value = "100")
//        binding.colors = CustomColors()
    }
}