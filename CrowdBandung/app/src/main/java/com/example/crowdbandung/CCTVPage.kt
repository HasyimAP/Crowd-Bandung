package com.example.crowdbandung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CctvPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cctv_page)

        supportActionBar?.hide()
    }
}