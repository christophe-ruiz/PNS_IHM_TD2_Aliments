package com.example.projetihm.producer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import com.example.projetihm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddProductActivity extends AppCompatActivity {
    ImageView imageView;
    FloatingActionButton addPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        addPicture=findViewById(R.id.addPictureButton);
        imageView=findViewById(R.id.productPitcure);
        if(ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddProductActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        addPicture.setOnClickListener(click->{
            Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,100);
        });
        findViewById(R.id.addProductBackButton).setOnClickListener(click->{
            Intent backIntent=new Intent(getApplicationContext(), OrdersActivity.class);
            startActivity(backIntent);
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            Bitmap picture =(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(picture);
        }
    }
}