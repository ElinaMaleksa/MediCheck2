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
import com.app.medicheck.ui.profile.Favourites;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.RecyclerViewHolder>{
    HomeFragment mHomeFragment;
    ArrayList<Products> mProductsList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mIngredients, mBestBefore, mSerialNumber;
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
                        //add serial number to favourite list
                        Favourites.setData(mSerialNumber.getText().toString());

                    } else {
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public HomeRecyclerViewAdapter(HomeFragment homeFragment, ArrayList<Products> products) {
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
