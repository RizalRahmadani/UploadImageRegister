package com.example.uploadimageregister.view

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.uploadimageregister.R
import com.example.uploadimageregister.databinding.ActivityMainBinding
import com.example.uploadimageregister.viewmodel.ViewModelUser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    private var imageMultipart : MultipartBody.Part? = null
    private var imageUri : Uri? = Uri.EMPTY
    private var imageFile : File? = null
    private lateinit var VMUser: ViewModelUser

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        VMUser = ViewModelProvider(this).get(ViewModelUser::class.java)

        binding.addImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnRegister.setOnClickListener {
            register()
        }
    }


    private fun register() {
        val address = binding.etName.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val email = binding.etAlamat.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val fullName = binding.etEmail.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val password = binding.etNoHp.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val phoneNumber = binding.etPassword.text.toString().toRequestBody("multipart/form-data".toMediaType())

        VMUser.postUser(address, email, fullName, imageMultipart!!, password, phoneNumber)
        VMUser.liveDataUser().observe(this){
            if (it != null){
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(ContentValues.TAG, "register failed: $it")
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){ uri : Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = this!!.contentResolver
                val type = contentResolver.getType(it)
                imageUri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.addImage.setImageURI(it)
                Toast.makeText(this, "$imageUri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use    { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultipart = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }
}