package com.example.samplecleanarchitecture.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.database.getLongOrNull
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.component.setOnClickShrinkListener
import com.example.samplecleanarchitecture.databinding.ActivityPhotoBinding
import com.example.samplecleanarchitecture.utils.loadImage
import java.io.InputStream

class PhotoActivity : BaseActivity<ActivityPhotoBinding>(ActivityPhotoBinding::inflate){
    //
    private var imageLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri ->
        uri?.let {
            setUpImage(uri)
            checkImageDetail(uri)
        }
    }

    private fun setUpImage(uri: Uri) {
        viewBinding.ivImage.loadImage(uri.toString())
    }

    @SuppressLint("Recycle")
    fun checkImageDetail(uri: Uri){
        try{

            val type = contentResolver.getType(uri)
            Log.e("SIZE","type: $type")

            var fileImageSizeInByte : Long? = 0L
            contentResolver.query(uri,null, null, null)?.use{ cursor ->
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if(cursor.moveToFirst() && sizeIndex != -1){
                    fileImageSizeInByte = cursor.getLongOrNull(sizeIndex)
                }
            }
            fileImageSizeInByte?.let {
                val fileMb = fileImageSizeInByte * 1.0 / (1024.0)
                Log.e("SIZE",fileMb.toString())//result x(KB)
            }

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            val width = options.outWidth
            val height = options.outHeight

            Log.e("SIZE","$width $height")

        }catch (e : Exception){
            Log.e("E",e.message.toString())
        }
    }

    override fun bindView() {
        viewBinding.apply {
            btnChoose.setOnClickShrinkListener {
                imageLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        }
    }
}