package com.example.uploadimageregister.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uploadimageregister.model.DataUser
import com.example.uploadimageregister.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel(){

    var user : MutableLiveData<DataUser?> = MutableLiveData()

    fun liveDataUser () : MutableLiveData<DataUser?> {
        return user
    }

    fun postUser(address : RequestBody, email : RequestBody, fullName : RequestBody, image : MultipartBody.Part, password : RequestBody, phoneNumber : RequestBody){
        RetrofitClient.instance.register(address,  email, fullName, image, password, phoneNumber)
            .enqueue(object : Callback<DataUser> {
                override fun onResponse(
                    call: Call<DataUser>,
                    response: Response<DataUser>,
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }else{
                        user.postValue(null)
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<DataUser>, t: Throwable) {
                    user.postValue(null)
                    error(t)
                }

            })
    }
}