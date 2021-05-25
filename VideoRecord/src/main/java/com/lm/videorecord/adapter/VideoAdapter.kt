package com.lm.videorecord.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lm.videorecord.R
import com.lm.videorecord.bean.VideoBean
import com.lm.videorecord.utils.ImageUtil

class VideoAdapter(data:MutableList<VideoBean>):BaseQuickAdapter<VideoBean,BaseViewHolder>(R.layout.item_vr_video,data) {


    override fun convert(helper: BaseViewHolder, item: VideoBean) {
        if (item.id==0){
            helper.getView<ImageView>(R.id.iv_video_img).setImageResource(R.drawable.im_vr_videorecord)
        }else{
            ImageUtil.loadCover(helper.getView(R.id.iv_video_img),item.videoPath)
        }

    }
}