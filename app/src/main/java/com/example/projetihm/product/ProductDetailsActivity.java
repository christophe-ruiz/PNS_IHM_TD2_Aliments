package com.example.projetihm.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetihm.R;
import com.example.projetihm.models.CustomDeleteProductPopUp;
import com.example.projetihm.models.CustomProductPopUp;
import com.example.projetihm.models.Manager;
import com.example.projetihm.models.Product;
import com.example.projetihm.producer.tab.products.AddProductActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class ProductDetailsActivity extends AppCompatActivity {
    Product product;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.product=getIntent().getParcelableExtra("product");

        ((TextView)findViewById(R.id.productTitle)).setText(product.getName());
        ((TextView)findViewById(R.id.productPrice)).setText(product.getPrix() +" €");
        ((TextView)findViewById(R.id.productDesc)).setText(product.getDesc());
        ((TextView)findViewById(R.id.productOrigin)).setText(product.getProvenance());
        ((ImageView)findViewById(R.id.productImg)).setImageBitmap(product.getImg());
        if(!product.isBio()){
            TextView bio = findViewById(R.id.productBio);
            bio.setVisibility(View.INVISIBLE);
        }
        if(product.isBio() || product.isLabel()){
            TextView noLabel = findViewById(R.id.noLabel);
            noLabel.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.edit){
            Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
            intent.putExtra("product",this.product);
            for(Product product : Manager.onSale){
                if(product.equals(this.product)){
                    this.product=product;
                }
            }
            Manager.onSale.remove(product);
            finish();
            startActivity(intent);
        }else if(item.getItemId()==R.id.delete){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Supression du produit.")
                    .setMessage("Etes-vous sûr de supprimer le produit ?")
                    .setPositiveButton(R.string.edit_profile_dialog_positive_btn,
                            (dialog, which) -> {
                                for(Product product : Manager.onSale){
                                    if(product.equals(this.product)){
                                        this.product=product;
                                    }
                                }
                                Manager.onSale.remove(product);
                                finish();
                            })
                    .setNegativeButton(R.string.edit_profile_dialog_negative_btn,
                            (dialog, which) -> {})
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}