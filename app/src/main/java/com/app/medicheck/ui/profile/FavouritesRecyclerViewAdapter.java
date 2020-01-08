package com.app.medicheck.ui.profile;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.medicheck.R;
import com.app.medicheck.ui.home.ProductDetailsActivity;
import com.app.medicheck.ui.home.Products;

import java.util.ArrayList;
import java.util.List;

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.RecyclerViewHolder>{
    ProfileFragment mProfileFragment;
    ArrayList<Products> mProductsList;
    List<String> myFav;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView mName, mIngredients, mBestBefore, mSerialNumber;
        private ImageButton mButtonSeeMore;
        CheckBox mCheckBoxStar;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.product_name);
            mIngredients = itemView.findViewById(R.id.ingredients);
            mBestBefore = itemView.findViewById(R.id.best_before);
            mSerialNumber = itemView.findViewById(R.id.serial_number);
            mButtonSeeMore = itemView.findViewById(R.id.button_see_details);
            mCheckBoxStar = itemView.findViewById(R.id.add_item_to_favorite);

            mCheckBoxStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CompoundButton) view).isChecked()){
                        System.out.println("Checked");
                        //add serial number to favourite list
                        Favourites.setData(mSerialNumber.getText().toString());
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
                        Favourites.removeItem(mSerialNumber.getText().toString());

                    }
                }
            });

            mButtonSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameProduct = mName.getText().toString();
                    String bestBefore = mBestBefore.getText().toString();
                    String ingredients = mIngredients.getText().toString();
                    String serialNumber = mSerialNumber.getText().toString();

                    Intent intent = new Intent(mButtonSeeMore.getContext(), ProductDetailsActivity.class);
                    intent.putExtra("product_name", nameProduct);
                    intent.putExtra("product_ingredients", ingredients);
                    intent.putExtra("product_best_before", bestBefore);
                    intent.putExtra("product_serial_number", serialNumber);
                    mButtonSeeMore.getContext().startActivity(intent);
                }
            });
        }
    }

    public FavouritesRecyclerViewAdapter(ProfileFragment profileFragment, ArrayList<Products> products) {
        this.mProfileFragment = profileFragment;
        this.mProductsList = products;
    }

    @Override
    @NonNull
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mProfileFragment.getContext());
        View viewFav = layoutInflater.inflate(R.layout.product_list_item, viewGroup, false);

        return new RecyclerViewHolder(viewFav);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        holder.mName.setText(mProductsList.get(i).getName());
        holder.mBestBefore.setText(mProductsList.get(i).getBestBefore());
        holder.mIngredients.setText(mProductsList.get(i).getIngredients());
        holder.mSerialNumber.setText(mProductsList.get(i).getSerialNumber());

        List <String> fav = Favourites.getData();
        for (String s : fav) {
            if (s.equals(mProductsList.get(i).getSerialNumber())) {
                holder.mCheckBoxStar.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mProductsList == null) ? 0 : mProductsList.size();
    }
}
