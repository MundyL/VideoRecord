package com.lm.videorecord

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.lm.videorecord.interfaces.VideoChoiceCallBack
import com.lm.videorecord.ui.VideoListActivity

class VideoRecordHelper(private val context: Context) {

    companion object{
        var videoChoiceCallBack:VideoChoiceCallBack?=null
        var duration:Int=30
    }


    /**
     * 拍摄时间
     */
    fun duration(time:Int):VideoRecordHelper{
        if (time>0){
            duration=time
        }
        return this
    }


    fun onResult(callBack: VideoChoiceCallBack):VideoRecordHelper{
        videoChoiceCallBack=callBack
        return this
    }



    fun start(){
        val intent = Intent()
        intent.setClass(context, VideoListActivity::class.java)
        context.startActivity(intent)
    }
}