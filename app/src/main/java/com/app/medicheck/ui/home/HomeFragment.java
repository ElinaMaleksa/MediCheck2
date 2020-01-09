package com.app.medicheck.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
<<<<<<< Updated upstream
=======
import com.app.medicheck.ui.notifications.ContentNotifications;
import com.app.medicheck.ui.notifications.Receiver;
import com.app.medicheck.ui.profile.Favourites;
import com.google.android.material.bottomnavigation.BottomNavigationView;
>>>>>>> Stashed changes

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    BottomNavigationView mBottomNavigationView;
    private RecyclerView mRecyclerView;
    ArrayList<Products> mProductList;
    HomeRecyclerViewAdapter mRecyclerViewAdapter;
    public static final String TAG = "queueItem";
    RequestQueue queue;
    ArrayList<Products> productList;

    Handler handler = new Handler();
    Runnable refresh;
    boolean urlRequestDone = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mBottomNavigationView = root.findViewById(R.id.nav_view);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mProductList = new ArrayList<>();
        createList();

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh);

        //refreshing manually
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

        //automatically load the list
        refresh = new Runnable() {
            public void run() {
                if (urlRequestDone){
                    pullToRefresh.setRefreshing(false);
                    handler.removeCallbacks(refresh);
                    showList();
                }
                else{
                    mBottomNavigationView.setEnabled(false);
                    mBottomNavigationView.setFocusable(false);
                    mBottomNavigationView.setFocusableInTouchMode(false);
                    mBottomNavigationView.setClickable(false);
                    mBottomNavigationView.setContextClickable(false);
                    mBottomNavigationView.setOnClickListener(null);
                    
                    pullToRefresh.setRefreshing(true);
                    handler.postDelayed(refresh, 1000);
                }
            }
        };
        handler.post(refresh);

        return root;
    }

    public void showList(){
        mRecyclerViewAdapter = new HomeRecyclerViewAdapter(HomeFragment.this, productList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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
                        urlRequestDone = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "onErrorResponse: That didn't work!");
            }
        });
        stringRequest.setTag(TAG);
        queue.add(stringRequest);

      showList();
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
