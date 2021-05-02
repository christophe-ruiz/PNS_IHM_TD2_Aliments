package com.example.projetihm.models;

import android.app.Activity;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import com.example.projetihm.R;
import com.example.projetihm.producer.tab.products.ProductsActivity;
import com.example.projetihm.product.ProductDetailsActivity;

public class CustomDeleteProductPopUp extends Dialog implements View.OnClickListener {
    private String title;
    private String subTitle;
    private Button yesButton,noButton;
    private TextView titleView, subTitleView;
    private Activity myActivity;
    private Product product;

    public CustomDeleteProductPopUp(Activity activity, Product product) {
        super(activity);
        this.myActivity=activity;
        setContentView(R.layout.my_pop_up_template);
        this.title="Mon Titre";
        this.subTitle="Mon Sous-Titre";
        this.yesButton=findViewById(R.id.yesButton);
        this.noButton=findViewById(R.id.noButton);
        this.yesButton.setOnClickListener(this);
        this.noButton.setOnClickListener(this);
        this.titleView=findViewById(R.id.title);
        this.subTitleView=findViewById(R.id.subTitle);
        this.product=product;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }

    public void setTitleSize(float size){
        this.titleView.setTextSize(size);
    }

    public void setSubTitleSize(float size){
        this.subTitleView.setTextSize(size);
    }

    public void build(){
        titleView.setText(title);
        subTitleView.setText(subTitle);
        show();
    }

   @Override
    public void onClick(View v) {
        if (v.equals(this.noButton)) {
            this.dismiss();
        }
        else {
            for(Product product : Manager.onSale){
                if(product.equals(this.product)){
                    this.product=product;
                }
            }
            Manager.onSale.remove(product);
            this.dismiss();
            this.myActivity.finish();
        }
    }
}
