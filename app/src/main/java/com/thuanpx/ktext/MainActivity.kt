package com.thuanpx.ktext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.thuanpx.ktext.dialog.DialogThemeColor
import com.thuanpx.ktext.dialog.dialog
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog {
            title = "title text"
            message = "content text"
        }
    }
}