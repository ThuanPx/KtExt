package com.thuanpx.ktext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test() {
        val textx = "123"
        val texty = "123"

        val test = StringBuilder()
        test.append(textx).append(texty)

        val test2 = "$textx$texty"
    }
}