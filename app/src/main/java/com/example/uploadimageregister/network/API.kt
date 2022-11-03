package com.example.uploadimageregister.network

import com.example.uploadimageregister.model.DataUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface API  {
    @POST("/auth/register")
    @Multipart
    fun register(
        @Part("address") address : RequestBody,
        @Part("email") email: RequestBody,
        @Part("fullName") fullName : RequestBody,
        @Part image : MultipartBody.Part,
        @Part("password") password : RequestBody,
        @Part("phoneNumber") phoneNumber : RequestBody
    ) : Call<DataUser>
}