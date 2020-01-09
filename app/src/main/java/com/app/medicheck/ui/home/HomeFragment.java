package com.app.medicheck.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.app.medicheck.ui.notifications.ContentNotifications;
import com.app.medicheck.ui.notifications.Receiver;
import com.app.medicheck.ui.profile.Favourites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.android.volley.toolbox.Volley.*;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    ArrayList<Products> mProductList;
    HomeRecyclerViewAdapter mRecyclerViewAdapter;
    public static final String TAG = "queueItem";
    RequestQueue queue;
    public static ArrayList<Products> productList;

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
                        setAlarm();

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
        //mRecyclerViewAdapter = new RecyclerViewAdapter(HomeFragment.this, mProductList);
        mRecyclerViewAdapter = new HomeRecyclerViewAdapter(HomeFragment.this, productList);
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

        public void setAlarm(){
        AlarmManager alarms = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        getActivity().registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        //intent.putExtra("param", "My scheduled action");

        //set alarm time (after that much ms it will trigger notification)
        //System.currentTimeMillis()+10000

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
