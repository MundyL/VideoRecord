package com.lm.videorecord.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


object FileUtil {
     fun getRealPath(context:Context,fileUrl: Uri?): String {
        var fileName: String = ""
        if (fileUrl != null) {
            if (fileUrl.getScheme().toString().compareTo("content") === 0) // content://开头的uri
            {
                val cursor =
                    context.contentResolver.query(fileUrl, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    try {
                        val column_index: Int =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        fileName = cursor.getString(column_index) // 取出文件路径
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    } finally {
                        cursor.close()
                    }
                }
            } else if (fileUrl.scheme?.compareTo("file") === 0) // file:///开头的uri
            {
                  fileUrl.path?.let {
                      fileName=it
                }
            }
        }
        return fileName
    }

}