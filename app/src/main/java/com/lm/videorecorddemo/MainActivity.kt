package com.lm.videorecorddemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lm.videorecord.VideoRecorderActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn).setOnClickListener {
            val intent = Intent()
            intent.setClass(this, VideoRecorderActivity::class.java)
            startActivityForResult(intent, 1)
        }

    }
}