package com.example.uploadimagetoserver;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface retorfit {
    @Multipart
    @POST("api/uploadImage")
   Call<upload> uploadphoto(@Part MultipartBody.Part file);
}
