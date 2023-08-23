package com.example.resultsblock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var topPress: CircularProgressBar
    private lateinit var downPress: CircularProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topPress = findViewById<CircularProgressBar>(R.id.top_press)
        downPress = findViewById<CircularProgressBar>(R.id.down_press)
    }

    override fun onStart() {
        super.onStart()
        topPress.setProgressWithAnimation(130f, 2000)
        downPress.setProgressWithAnimation(80f, 2000)
    }
}