package com.app.medicheck.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.medicheck.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    HomeFragment mHomeFragment;
    ArrayList<Products> mProductsList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mIngredients, mBestBefore;
        private ImageButton mButtonSeeMore;
        CheckBox mCheckBoxStar;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.product_name);
            mIngredients = itemView.findViewById(R.id.ingredients);
            mBestBefore = itemView.findViewById(R.id.best_before);
            mButtonSeeMore = itemView.findViewById(R.id.button_see_details);
            mCheckBoxStar = itemView.findViewById(R.id.add_item_to_favorite);

            mCheckBoxStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CompoundButton) view).isChecked()){
                        System.out.println("Checked");

                       /* String nameProduct = mName.getText().toString();
                        String bestBefore = mBestBefore.getText().toString();
                        String ingredients = mIngredients.getText().toString();

                        Intent intent = new Intent(mCheckBoxStar.getContext(), ProfileFragment.class);
                        intent.putExtra("product_name", nameProduct);
                        intent.putExtra("product_ingredients", ingredients);
                        intent.putExtra("product_best_before", bestBefore);
                        //mCheckBoxStar.getContext().startActivity(intent);*/
                    } else {
                        System.out.println("Un-Checked");
                    }
                }
            });

            mButtonSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameProduct = mName.getText().toString();
                String bestBefore = mBestBefore.getText().toString();
                String ingredients = mIngredients.getText().toString();

                Intent intent = new Intent(mButtonSeeMore.getContext(), ProductDetailsActivity.class);
                intent.putExtra("product_name", nameProduct);
                intent.putExtra("product_ingredients", ingredients);
                intent.putExtra("product_best_before", bestBefore);
                mButtonSeeMore.getContext().startActivity(intent);

            }
        });
        }
    }

    public RecyclerViewAdapter(HomeFragment homeFragment, ArrayList<Products> products) {
        this.mHomeFragment = homeFragment;
        this.mProductsList = products;
    }

    @Override
    @NonNull
      public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          LayoutInflater layoutInflater = LayoutInflater.from(mHomeFragment.getContext());
          View view = layoutInflater.inflate(R.layout.product_list_item, viewGroup, false);

          return new RecyclerViewHolder(view);
      }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        holder.mName.setText(mProductsList.get(i).getName());
        holder.mBestBefore.setText(mProductsList.get(i).getBestBefore());
        holder.mIngredients.setText(mProductsList.get(i).getIngredients());

    }

    @Override
    public int getItemCount() {
        return (mProductsList == null) ? 0 : mProductsList.size();
    }
}
