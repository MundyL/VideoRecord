package com.lm.videorecord.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageUtil {
    //读取视频第一帧
    fun loadCover(imageView: ImageView, url: String?) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(1000000)
                    .centerCrop()
            )
            .load(url)
            .into(imageView)
    }
    fun load(imageView: ImageView, url: String?){
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
    fun loadRound(imageView: ImageView, url: String?){
        Glide.with(imageView.context)
            .load(url)
            .circleCrop()
            .into(imageView)
    }



}