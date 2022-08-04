package com.example.uploadimagetoserver;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    Button btn_pickImage, btn_upload;
    String Url = "https://alshoubaki.herokuapp.com/";
    Uri uri;
    Bitmap bitmap;
    String encodedImage;
    private static Retrofit retrofit ;
    String path;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.main_iv);
        btn_pickImage = findViewById(R.id.main_btn_pickImage);
        btn_upload = findViewById(R.id.main_btn_uploadImage);


        if (  ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE
            );
        }else{
            Toast.makeText(getApplicationContext(), "you have the permission", Toast.LENGTH_SHORT).show();
        }
        btn_pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (path != null){
                    uploadFile();
                }

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            uri = data.getData();




            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                iv.setImageBitmap(bitmap);
                path = RealPathUtil.getRealPath(getApplicationContext() , uri);
//                path= getStringImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void uploadFile() {


        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Create a file object using file path
        File filePath = new File(path);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), filePath);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", filePath.getName(), requestBody);

        retorfit getResponse = retrofit.create(retorfit.class);
        Call<upload> call = getResponse.uploadphoto(file);
        call.enqueue(new Callback<upload>() {
            @Override
            public void onResponse(Call<upload> call, Response<upload> response) {
                Log.d("response", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<upload> call, Throwable t) {

            }
        });


        }



    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission

    }

}





