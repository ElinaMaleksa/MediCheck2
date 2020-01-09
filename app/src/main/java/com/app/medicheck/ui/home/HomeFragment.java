package com.app.medicheck.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.app.medicheck.MainActivity;
import com.app.medicheck.R;

import com.app.medicheck.ui.notifications.ContentNotifications;
import com.app.medicheck.ui.notifications.Receiver;
import com.app.medicheck.ui.profile.Favourites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    HomeRecyclerViewAdapter mRecyclerViewAdapter;
    SwipeRefreshLayout pullToRefresh;
    TextView mEmptyViewHome;

    public static final String TAG = "queueItem";
    RequestQueue queue;
    public static ArrayList<Products> productList;

    Handler handler = new Handler();
    Runnable refresh;
    boolean urlRequestDone = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mEmptyViewHome= root.findViewById(R.id.empty_view_home);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pullToRefresh = root.findViewById(R.id.pullToRefresh);

        createList();

        //refreshing manually
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createList();
                pullToRefresh.setRefreshing(false);
            }
        });

        //automatically load the list
        refresh = new Runnable() {
            public void run() {
                if (urlRequestDone || mRecyclerViewAdapter.getItemCount()>0){
                    pullToRefresh.setRefreshing(false);
                    handler.removeCallbacks(refresh);
                    showList();
                    hideBottomBar(false);
                    setAlarm();
                    changeViewElementVisibility();
                }
                else{
                    changeViewElementVisibility();
                    hideBottomBar(true);
                    pullToRefresh.setRefreshing(true);
                    handler.postDelayed(refresh, 1000);
                }
            }
        };
        handler.post(refresh);

        return root;
    }

    public void changeViewElementVisibility(){
        if (mRecyclerViewAdapter.getItemCount()>0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyViewHome.setVisibility(View.GONE);
        }
        else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyViewHome.setVisibility(View.VISIBLE);
        }
    }
    public void hideBottomBar(boolean isHidden){
        MainActivity.navView.setVisibility(isHidden ? View.GONE : View.VISIBLE);
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

        public void setAlarm(){
        AlarmManager alarms = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        getActivity().registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");

        ArrayList<Products> allProducts = HomeFragment.productList;
        List<String> fav = Favourites.getData();
        ContentNotifications cN;

        for (Products p : allProducts) {
            for (String s : fav) {
                if (p.getSerialNumber().equals(s)) {

                    String[] dateComponents = p.getBestBefore().split("-");
                    int year = Integer.parseInt(dateComponents[0]);
                    int month = Integer.parseInt(dateComponents[1]);
                    int day = Integer.parseInt(dateComponents[2]);

                    Calendar cal = Calendar.getInstance();
                    cal.set(java.util.Calendar.YEAR, year);
                    cal.set(java.util.Calendar.MONTH, month - 1);
                    cal.set(java.util.Calendar.DAY_OF_MONTH, day);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    cal.set(java.util.Calendar.MILLISECOND, 0);

                    long msDiff = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                    int expWarningTime = 14;

                    if (daysDiff < expWarningTime) {
                        cN = new ContentNotifications(p.getId(), p.getName(), p.getBestBefore(), daysDiff);
                        long timeInMilliSec = calculateMilliseconds(cN.getBestBeforeNot()).getTimeInMillis();

                        intent.putExtra("title", "Expiration date soon");
                        intent.putExtra("name", cN.getNameNot());
                        intent.putExtra("days", Long.toString(daysDiff));
                        intent.putExtra("id", Integer.toString(cN.getIdNot()));
                        PendingIntent operation = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                        alarms.set(AlarmManager.RTC_WAKEUP, 5000, operation) ;
                    }
                }
            }
        }
    }

     private Calendar calculateMilliseconds(String expDate){
        String [] dateComponents = expDate.split("-");
        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int day = Integer.parseInt(dateComponents[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 23);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }
}
