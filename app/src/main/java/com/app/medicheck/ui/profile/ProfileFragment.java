package com.app.medicheck.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.medicheck.R;
import com.app.medicheck.ui.home.HomeFragment;
import com.app.medicheck.ui.home.HomeRecyclerViewAdapter;
import com.app.medicheck.ui.home.Products;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

 private RecyclerView mRecyclerView;

        FavouritesRecyclerViewAdapter mFavouritesRecyclerViewAdapter;

        ArrayList<Products> mProductsArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view_profile);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mProductsArrayList = new ArrayList<>();
        createList();

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh_profile);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mProductsArrayList.isEmpty()){
                    //update list
                    mProductsArrayList.clear();
                }
                createList();
                pullToRefresh.setRefreshing(false);
            }
        });
        return root;
    }

/*    public void createList(){
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));
        mProductsArrayList.add(new Products(1, "sinupret", "med", "ingred Sinupret","05/05/2020", "011"));
        mProductsArrayList.add(new Products(2, "Linex", "med", "ingred Linex","01/10/2021", "111"));

        mFavouritesRecyclerViewAdapter = new FavouritesRecyclerViewAdapter(ProfileFragment.this, mProductsArrayList);
        mFavouritesRecyclerViewAdapter = new FavouritesRecyclerViewAdapter(ProfileFragment.this, mProductsArrayList);
        mRecyclerView.setAdapter(mFavouritesRecyclerViewAdapter);
    }*/

    public void createList() {

        ArrayList<Products> favProducts = new ArrayList<>();
        ArrayList<Products> allProducts = HomeFragment.productList;
        List<String> fav = Favourites.getData();

        for (Products p : allProducts) {
            for (String s: fav){
                if (p.getSerialNumber().equals(s)) {
                    favProducts.add(p);
                }
            }
        }


        mFavouritesRecyclerViewAdapter = new FavouritesRecyclerViewAdapter(ProfileFragment.this, favProducts);
        mRecyclerView.setAdapter(mFavouritesRecyclerViewAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            //activity.getSupportActionBar().hide();
            createActionBar();
        }
    }

    public void createActionBar () {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        androidx.appcompat.widget.Toolbar profileToolbar =(Toolbar) activity.findViewById(R.id.fragment_profile_toolbar);
        profileToolbar.inflateMenu(R.menu.profile_toolbar_menu);
        profileToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile_toolbar_item_about:
                        buildAboutDialog();
                        //Toast ToolbarToast = Toast.makeText(getContext(),"We did it!",Toast.LENGTH_SHORT);
                        //ToolbarToast.show();
                        break;
                    case R.id.profile_toolbar_item_buy_products:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://lipsum.com"));
                        startActivity(intent);
                        break;
                        default:
                            break;
                }
                //Toast ToolbarToast = Toast.makeText(getContext(),item.getTitle(),Toast.LENGTH_SHORT);
                //ToolbarToast.show();
                return false;
            }
        });
    }

    public void buildAboutDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.profile_about_title);
        builder.setMessage(R.string.profile_about_message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
