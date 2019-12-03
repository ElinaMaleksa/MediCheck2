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
        mProductList.add(new Products("Sinupret", "1 coated tablet contains:\n\n" +
                "Active substance:\n" +
                "160.00 mg dry extract (3-6:1) of gentian root, primula flowers, sorrel herb,\n" +
                "elder flowers, verbena herb (1:3:3:3:3) 1. Extracting agent: ethanol 51%\n\n" +
                "The other ingredients are:\n" +
                "Spray-dried gum arabic, calcium carbonate, carnauba wax, cellulose\n" +
                "powder, microcrystalline cellulose, chlorophyll powder 25% (E140),\n" +
                "dextrin (from corn starch), glucose syrup, hypromellose, indigocarmine,\n" +
                "aluminium salt (E 132), magnesium stearate (Ph.Eur.) [vegetable],\n" +
                "maltodextrin, riboflavin (E 101), highly dispersed silicon dioxide, highly\n" +
                "dispersed hydrophobic silicon dioxide, stearic acid, sucrose, talc, titanium\n" +
                "dioxide (E 171).\n\n" +
                "Note for diabetics:\n" +
                "One coated tablet contains on average 0.026 carbohydrate exchange units\n" +
                "(CEU). Sinupret extract is gluten-free and lactose-free.","12/12/2020"));
        mProductList.add(new Products("Xyzal", "ingred Xyzal","05/05/2020"));
        mProductList.add(new Products("Linex", "ingred Linex","01/10/2021"));
        //add products to list
        mRecyclerViewAdapter = new RecyclerViewAdapter(HomeFragment.this, mProductList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
