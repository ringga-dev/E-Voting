package com.ecampus.bem.ui.upload

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.ecampus.bem.data.adapter.ImagePaslonAdapter
import com.ecampus.bem.data.adapter.UploadRequestBody
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.data.model.GetImagePaslon
import com.ecampus.bem.databinding.ActivityUploadFileBinding
import com.stfalcon.imageviewer.StfalconImageViewer
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.ArrayList
import okhttp3.RequestBody


class UploadFileActivity : AppCompatActivity() {

    var files = ArrayList<String>()
    var multiPart = ArrayList<MultipartBody.Part>()
    lateinit var binding: ActivityUploadFileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecler()
        viewRecycel()



        binding.btnSelectFiles.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        })


    }

    private fun setupRecler() {
        binding.rvFileUser.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = ImagePaslonAdapter(mutableListOf(), this@UploadFileActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && null != data) {
            if (data.clipData != null) {
                val count =
                    data.clipData!!.itemCount //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    getImageFilePath(imageUri)
                }
            }
            if (files.size > 0) {
                uploadFiles()
            }
        }
    }

    @SuppressLint("Range")
    fun getImageFilePath(uri: Uri) {
        val file = File(uri.path)
        val filePath = file.path.split(":").toTypedArray()
        val image_id = filePath[filePath.size - 1]
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(image_id),
            null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            val imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            files.add(imagePath)
            cursor.close()
        }
    }

    private fun uploadFiles() {


        files.forEach { unit ->
            val requestFile = UploadRequestBody(File(unit), "image", this)
            multiPart.add(
                MultipartBody.Part.createFormData(
                    "file[]", File(unit).name, requestFile
                )
            )
        }
        val extras = intent.extras
        val id = RequestBody.create(MultipartBody.FORM, extras?.getString("id").toString())
        RetrofitClient.instance.uploadFile(multiPart, id).enqueue(
            object : Callback<BaseResponData> {
                override fun onResponse(
                    call: Call<BaseResponData>,
                    response: Response<BaseResponData>
                ) {
                    Toast.makeText(
                        this@UploadFileActivity,
                        response.body()?.msg,
                        Toast.LENGTH_SHORT
                    ).show()
                    files.clear()
                    multiPart.clear()
                    viewRecycel()

                }

                override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                    Toast.makeText(this@UploadFileActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.i("error", t.message!!)
                }
            }
        )

        Log.i("error", files.toString())
    }

    private fun viewRecycel(){
        val extras = intent.extras
        val id = extras?.getString("id").toString()

        RetrofitClient.instance.getImagePaslon(id).enqueue(
            object : Callback<BaseRespon<List<GetImagePaslon>>> {
                override fun onResponse(
                    call: Call<BaseRespon<List<GetImagePaslon>>>,
                    response: Response<BaseRespon<List<GetImagePaslon>>>
                ) {

                    binding.rvFileUser.adapter?.let { adapter ->
                        if (adapter is ImagePaslonAdapter) {
                            adapter.setLagu(response.body()?.data!!)
                        }
                    }
                }

                override fun onFailure(call: Call<BaseRespon<List<GetImagePaslon>>>, t: Throwable) {
                    Toast.makeText(this@UploadFileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}