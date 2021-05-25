package com.lm.videorecord.ui

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lm.videorecord.R
import com.lm.videorecord.adapter.VideoAdapter
import com.lm.videorecord.bean.VideoBean

class VideoListActivity:AppCompatActivity() {


    private lateinit var rv:RecyclerView
    private var listVideo= mutableListOf<VideoBean>()
    private lateinit var adapter:VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vr_video_list)
        rv=findViewById(R.id.rv)
        initData()
    }

    private fun initData(){
        listVideo.addAll(getVideoList())
        adapter= VideoAdapter(listVideo)
        rv.layoutManager=GridLayoutManager(this,2)
        rv.adapter=adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            Log.d("lm",listVideo[position].thumbImg)
        }

    }
    private fun getVideoList():MutableList<VideoBean>{
        val list= mutableListOf<VideoBean>()
        //把录像按钮添加到第一个
        list.add(VideoBean(0,"",""))

        val thumpColumns= arrayOf(MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID)

        val media= arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION)

        val cursor=contentResolver.query(
            MediaStore.Video.Media
            .EXTERNAL_CONTENT_URI, media, null, null, null)
        cursor?.let {
            while (it.moveToNext()){
                val id = it.getInt(it
                    .getColumnIndex(MediaStore.Video.Media._ID))
                val thumbCursor=contentResolver.query(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumpColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                        + "=" + id,null,null)
                if (thumbCursor!!.moveToFirst()){
                    list.add(
                        VideoBean(it.getInt(it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)),
                            thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)),
                            it.getString(it.getColumnIndexOrThrow(
                                MediaStore.Video.Media
                                    .DATA))
                        )
                    )
                }
            }
        }
        return list
    }

}