package com.app.medicheck.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.medicheck.R;
import com.app.medicheck.ui.home.HomeFragment;
import com.app.medicheck.ui.home.Products;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProfileFragment extends Fragment {
    TextView mEmptyViewProfile;
    FavouritesRecyclerViewAdapter mFavouritesRecyclerViewAdapter;
    ArrayList<Products> mProductsArrayList;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        mEmptyViewProfile = root.findViewById(R.id.empty_view_profile);
        mRecyclerView = root.findViewById(R.id.recycler_view_profile);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mProductsArrayList = new ArrayList<>();
        createList();
        changeViewElementVisibility();

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh_profile);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mProductsArrayList.isEmpty()) {
                    //update list
                    mProductsArrayList.clear();
                }
                changeViewElementVisibility();
                createList();
                pullToRefresh.setRefreshing(false);
            }
        });
        return root;
    }

    public void createList() {
        ArrayList<Products> favProducts = new ArrayList<>();
        ArrayList<Products> allProducts = HomeFragment.productList;
        List<String> fav = Favourites.getData();

        for (Products p : allProducts) {
            for (String s : fav) {
                if (p.getSerialNumber().equals(s)) {
                    favProducts.add(p);
                }
            }
        }
        mFavouritesRecyclerViewAdapter = new FavouritesRecyclerViewAdapter(ProfileFragment.this, favProducts);
        mRecyclerView.setAdapter(mFavouritesRecyclerViewAdapter);
    }

    public void changeViewElementVisibility() {
        if (mFavouritesRecyclerViewAdapter.getItemCount() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyViewProfile.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyViewProfile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
