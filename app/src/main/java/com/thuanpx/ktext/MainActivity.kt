package com.thuanpx.ktext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thuanpx.ktext.context.launchAndRepeatWithViewLifecycle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}