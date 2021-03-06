package com.example.projetihm.producer.tab.products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetihm.R;
import com.example.projetihm.controllers.SaveMaker;
import com.example.projetihm.models.Manager;
import com.example.projetihm.models.Product;
import com.example.projetihm.product.ProductDetailsActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddProductActivity extends AppCompatActivity {
    ImageView imageView;
    FloatingActionButton addPicture;
    Product product;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        addPicture=findViewById(R.id.addPictureButton);
        imageView=findViewById(R.id.productPitcure);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent().getExtras()!=null){
            this.product=getIntent().getExtras().getParcelable("product");
            imageView.setImageBitmap(product.getImg());
            ((TextView)findViewById(R.id.editProductName)).setText(product.getName());
            ((EditText)findViewById(R.id.editPrice)).setText(Double.toString(product.getPrix()));
            ((TextView)findViewById(R.id.editOrigin)).setText(product.getProvenance());
            ((TextView)findViewById(R.id.addDesc)).setText(product.getDesc());
            if(product.isBio())
                ((CheckBox)findViewById(R.id.isBio)).setChecked(true);
            ((Button)findViewById(R.id.addNewProductButton)).setText("Editer");
        }
        addPicture.setOnClickListener(click->{
            if(ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(AddProductActivity.this,new String[]{
                        Manifest.permission.CAMERA
                },100);
            }else testPicture();
        });

        findViewById(R.id.addNewProductButton).setOnClickListener(click->{
            String name= ((TextView)findViewById(R.id.editProductName)).getText().toString();
            Double price=Double.parseDouble(((EditText)findViewById(R.id.editPrice)).getText().toString());
            String origin= ((TextView)findViewById(R.id.editOrigin)).getText().toString();
            String desc= ((TextView)findViewById(R.id.addDesc)).getText().toString();
            CheckBox bio=findViewById(R.id.isBio);
            imageView.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Product createdProduct= new Product(bitmap,name,origin,price,desc,bio.isChecked(),false);
            Manager.onSale.add(createdProduct);
             new MaterialAlertDialogBuilder(this)
                    .setTitle((product==null) ? "Votre Produit a bien ??t?? ajout??." : "Votre Produit a bien ??t?? ??dit??.")
                    .setMessage((product==null)?"Voulez vous voir le produit cr??e ?":"Voulez vous voir le produit ??dit?? ?")
                    .setPositiveButton(R.string.edit_profile_dialog_positive_btn,
                            (dialog, which) -> {
                                Product product=Manager.onSale.get(Manager.onSale.size()-1);
                                SaveMaker.saveArrayToInternalStorage(Manager.getJsonProducts(),"products",getApplicationContext());
                                Intent productIntent= new Intent(getApplicationContext(), ProductDetailsActivity.class);
                                productIntent.putExtra("product",product);
                                finish();
                                startActivity(productIntent);
                            })
                    .setNegativeButton(R.string.edit_profile_dialog_negative_btn,
                            (dialog, which) -> {finish();})
                    .show();

        });
    }

    private void testPicture(){
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100) {
            Bitmap picture =(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(picture);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        if(product!=null)
            Manager.onSale.add(this.product);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
               testPicture();
            }
        }
    }
}