package com.app.medicheck.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        TextView mName, mBestBefore;
        private ImageButton mButtonSeeMore;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.product_name);
            mBestBefore = itemView.findViewById(R.id.best_before);
            mButtonSeeMore = itemView.findViewById(R.id.button_see_details);

            mButtonSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Clicked SeeMoreButton", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mButtonSeeMore.getContext(), ProductDetailsActivity.class);
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

    }

    @Override
    public int getItemCount() {
        return (mProductsList == null) ? 0 : mProductsList.size();
    }
}
