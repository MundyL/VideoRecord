package com.lm.videorecorddemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.lm.videorecord.VideoRecordHelper
import com.lm.videorecord.interfaces.VideoChoiceCallBack
import com.lm.videorecord.ui.VideoListActivity
import com.lm.videorecord.ui.VideoPlayActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn).setOnClickListener {
            VideoRecordHelper(this)
                .onResult(object : VideoChoiceCallBack {
                    override fun onChoice(path: String) {
                        Log.d("lm","path=${path}")
                    }
                })
                .start()
        }

    }
}