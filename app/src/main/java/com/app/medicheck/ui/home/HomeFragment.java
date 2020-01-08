package com.app.medicheck.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.medicheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.toolbox.Volley.*;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    ArrayList<Products> mProductList;
    RecyclerViewAdapter mRecyclerViewAdapter;
    public static final String TAG = "queueItem";
    RequestQueue queue;
    ArrayList<Products> productList;

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
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="https://ebiznessvia.000webhostapp.com/api/products/read.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Log.d("", "onResponse: " + response);
                        productList = parseJsonToProductList(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "onErrorResponse: That didn't work!");

            }
        });
        stringRequest.setTag(TAG);
        queue.add(stringRequest);

//        mProductList.add(new Products("Sinupret", "1 coated tablet contains:\n\n" +
//                "Active substance:\n" +
//                "160.00 mg dry extract (3-6:1) of gentian root, primula flowers, sorrel herb,\n" +
//                "elder flowers, verbena herb (1:3:3:3:3) 1. Extracting agent: ethanol 51%\n\n" +
//                "The other ingredients are:\n" +
//                "Spray-dried gum arabic, calcium carbonate, carnauba wax, cellulose\n" +
//                "powder, microcrystalline cellulose, chlorophyll powder 25% (E140),\n" +
//                "dextrin (from corn starch), glucose syrup, hypromellose, indigocarmine,\n" +
//                "aluminium salt (E 132), magnesium stearate (Ph.Eur.) [vegetable],\n" +
//                "maltodextrin, riboflavin (E 101), highly dispersed silicon dioxide, highly\n" +
//                "dispersed hydrophobic silicon dioxide, stearic acid, sucrose, talc, titanium\n" +
//                "dioxide (E 171).\n\n" +
//                "Note for diabetics:\n" +
//                "One coated tablet contains on average 0.026 carbohydrate exchange units\n" +
//                "(CEU). Sinupret extract is gluten-free and lactose-free.","12/12/2020"));
//        mProductList.add(new Products("Xyzal", "ingred Xyzal","05/05/2020"));
//        mProductList.add(new Products("Linex", "ingred Linex","01/10/2021"));
        //add products to list
//        mRecyclerViewAdapter = new RecyclerViewAdapter(HomeFragment.this, mProductList);
        mRecyclerViewAdapter = new RecyclerViewAdapter(HomeFragment.this, productList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    ArrayList<Products> parseJsonToProductList(String json){

        ArrayList<Products> pL = new ArrayList<>();
        try {
            JSONObject wholeJson = new JSONObject(json);
            JSONArray jsonArray = wholeJson.getJSONArray("records");
            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("",""+jsonObject.getInt("id") );
//                Log.d("",""+jsonObject.getString("name") );
//                Log.d("",""+jsonObject.getString("category") );
//                Log.d("",""+jsonObject.getString("ingredients") );
//                Log.d("",""+jsonObject.getString("best_before") );
//                Log.d("",""+jsonObject.getString("serial_number") );

                pL.add(new Products(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("category"),
                        jsonObject.getString("ingredients"),
                        jsonObject.getString("best_before"),
                        jsonObject.getString("serial_number")
                ));

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return  pL;
    }
}
