package com.app.medicheck.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.medicheck.R;
import com.app.medicheck.ui.home.HomeFragment;
import com.app.medicheck.ui.home.Products;
import com.app.medicheck.ui.profile.Favourites;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NotificationsFragment extends Fragment {

    NotificationsViewAdapter mNotificationsViewAdapter;
    ArrayList<ContentNotifications> mContentNotificationsArrayList;
    TextView mEmptyViewNotif;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mEmptyViewNotif = root.findViewById(R.id.empty_view_notifications);
        mRecyclerView = root.findViewById(R.id.recycler_view_notifications);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mContentNotificationsArrayList = new ArrayList<>();
        createList();

        if (mNotificationsViewAdapter.getItemCount() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyViewNotif.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyViewNotif.setVisibility(View.VISIBLE);
        }

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh_notifications);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mContentNotificationsArrayList.isEmpty()) {
                    //update list
                    mContentNotificationsArrayList.clear();
                }
                createList();
                pullToRefresh.setRefreshing(false);
            }
        });
        return root;
    }

    public void createList() {
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
                        mContentNotificationsArrayList.add(cN);
                    }
                }
            }
        }

        mNotificationsViewAdapter = new NotificationsViewAdapter(NotificationsFragment.this, mContentNotificationsArrayList);
        mRecyclerView.setAdapter(mNotificationsViewAdapter);
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.profile_toolbar_item_about).setVisible(false);
        menu.findItem(R.id.profile_toolbar_item_buy_products).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
