package com.app.medicheck.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.app.medicheck.R;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {
    String nameProduct, bestBeforeProduct, ingredientsProduct, serialNumberProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        nameProduct = intent.getStringExtra("product_name");
        TextView nameProductField = findViewById(R.id.nameDetails);
        nameProductField.setText(nameProduct);

        ingredientsProduct = intent.getStringExtra("product_ingredients");
        TextView ingredientsField = findViewById(R.id.ingredientsDetails);
        ingredientsField.setText(ingredientsProduct);

        bestBeforeProduct = intent.getStringExtra("product_best_before");
        TextView bestBeforeField = findViewById(R.id.bestBeforeDetails);
        bestBeforeField.setText(bestBeforeProduct);

        serialNumberProduct = intent.getStringExtra("product_serial_number");
        TextView serialNumberField = findViewById(R.id.serialNumberDetails);
        serialNumberField.setText(serialNumberProduct);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
