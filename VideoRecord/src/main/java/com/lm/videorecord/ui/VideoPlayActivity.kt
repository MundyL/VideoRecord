package com.lm.videorecord.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.lm.videorecord.R

class VideoPlayActivity:AppCompatActivity() {


    private lateinit var vd:VideoView
    private lateinit var ivBack:ImageView
    private lateinit var btnCheck:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vr_video_play)

        vd=findViewById(R.id.vd_play)
        ivBack=findViewById(R.id.iv_back)
        btnCheck=findViewById(R.id.btn_check)
        val path=intent.getStringExtra("path")
        if (path!=""){
            var uri= Uri.parse(path)
            vd.setMediaController(MediaController(this))
            vd.setVideoURI(uri)
            vd.start()
        }else{
            finish()
        }
        ivBack.setOnClickListener {
            finish()
        }
        btnCheck.setOnClickListener {
            var intent=Intent()
            intent.putExtra("path",path)
            setResult(RESULT_OK,intent)
            finish()
        }

    }


}