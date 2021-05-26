package com.lm.videorecord.ui

import android.Manifest
import android.R.attr
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lm.videorecord.R
import com.lm.videorecord.VideoRecordHelper
import com.lm.videorecord.adapter.VideoAdapter
import com.lm.videorecord.bean.VideoBean
import com.lm.videorecord.compress.VideoCompress
import com.lm.videorecord.utils.FileUtil
import com.lm.videorecord.utils.PermissionUtils
import java.util.*


class VideoListActivity:AppCompatActivity() {


    companion object{
        const val CODE_RECORD=1
        const val CODE_CHOICE=2
    }

    //动态权限
    //6.0之后读写权限要同时申请
    val PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private var permissionUtils: PermissionUtils? = null
    private lateinit var rv:RecyclerView
    private lateinit var ivBack:ImageView
    private var listVideo= mutableListOf<VideoBean>()
    private lateinit var adapter:VideoAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vr_video_list)
        rv=findViewById(R.id.rv)
        ivBack=findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            finish()
        }
        permissionUtils = PermissionUtils(this)
        if (!permissionUtils!!.checkPermission(PERMISSIONS)) {
            permissionUtils!!.requestPermissions()
        } else {
            initData()
        }
    }



    private fun initData(){
        listVideo.addAll(getVideoList())
        listVideo.forEach {
            Log.d("lm", "${it}")
        }
        adapter= VideoAdapter(listVideo)
        rv.layoutManager=GridLayoutManager(this, 2)
        rv.adapter=adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            if (position==0){
                startRecord()
            }else{
                val intent=Intent(this, VideoPlayActivity::class.java)
                intent.putExtra("path", listVideo[position].videoPath)
                startActivityForResult(intent, CODE_CHOICE)
            }
        }

    }
    private fun startRecord(){
        var intent=Intent()
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VideoRecordHelper.duration);
        startActivityForResult(intent, CODE_RECORD)
    }

    private fun getVideoList():MutableList<VideoBean>{
        val list= mutableListOf<VideoBean>()
        //把录像按钮添加到第一个
        list.add(VideoBean(0, ""))

        val cursor: Cursor? = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val vdPath: String = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
            list.add(VideoBean(1,vdPath))
        }
        return list
    }

    private fun showLoading(){
        findViewById<View>(R.id.v_compress).visibility=View.VISIBLE
        findViewById<View>(R.id.pg).visibility=View.VISIBLE
        findViewById<View>(R.id.tv_compress).visibility=View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when(requestCode){
                CODE_CHOICE -> {

                    data?.getStringExtra("path")?.let {
                        startCompress(it)
                    }
                }
                CODE_RECORD -> {
                    data?.data?.let {
                        startCompress(FileUtil.getRealPath(this,it))
                    }
                }
            }
        }

    }



    private fun startCompress(path:String){
        val dir=cacheDir
        val sp=path.split("/")
        val fileName="${dir}/compress_${sp[sp.size-1]}"
        showLoading()
        VideoCompress.compressVideoLow(
            path,
            fileName,
            object : VideoCompress.CompressListener {
                override fun onStart() {

                }

                override fun onSuccess() {
                    VideoRecordHelper.videoChoiceCallBack?.let {
                        it.onChoice(fileName)
                        finish()
                    }

                }

                override fun onFail() {
                    VideoRecordHelper.videoChoiceCallBack?.let {
                        it.onChoice(path)
                        finish()
                    }

                }


                override fun onProgress(percent: Float) {
                    Log.d("lm", "onProgress:${percent}")
                }
            })
    }


    //权限申请回调
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionUtils.PERMISSION_REQUEST_CODE -> if (permissionUtils!!.isAgreePermission(
                    grantResults
                )
            ) {
                initData()
            } else {
                finish()
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        VideoRecordHelper.videoChoiceCallBack=null
    }
}