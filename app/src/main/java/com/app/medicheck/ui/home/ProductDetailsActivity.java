package com.app.medicheck.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.app.medicheck.R;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

  /*  Intent intent = getIntent();
    Products product = intent.getParcelableExtra("Products");

    String name = product.getName();
    String bestBefore = product.getBestBefore();

    TextView nameProduct = findViewById(R.id.nameDetails);
    nameProduct.setText(name);

    TextView bestBeforeProduct = findViewById(R.id.bestBeforeDetails);
    bestBeforeProduct.setText(bestBefore);*/
    }


}
