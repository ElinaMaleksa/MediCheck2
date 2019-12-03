package com.app.medicheck.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.medicheck.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    ArrayList<Products> mProductList;
    RecyclerViewAdapter mRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mProductList = new ArrayList<>();
        createList();

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mProductList.isEmpty()){
                    //update list
                    mProductList.clear();
                }
                createList();
                pullToRefresh.setRefreshing(false);
            }
        });
        return root;
    }

    public void createList() {
        mProductList.add(new Products("Sinupret","12/12/2020"));
        mProductList.add(new Products("Xyzal","05/05/2020"));
        mProductList.add(new Products("Linex","01/10/2021"));
        //add products to list
        mRecyclerViewAdapter = new RecyclerViewAdapter(HomeFragment.this, mProductList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
