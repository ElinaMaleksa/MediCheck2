package com.app.medicheck.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.app.medicheck.R;


import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

        private RecyclerView mRecyclerView;

        NotificationsViewAdapter mNotificationsViewAdapter;

        ArrayList<ContentNotifications> mContentNotificationsArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view_notifications);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mContentNotificationsArrayList = new ArrayList<>();
        createList();

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh_notifications);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mContentNotificationsArrayList.isEmpty()){
                    //update list
                    mContentNotificationsArrayList.clear();
                }
                createList();
                pullToRefresh.setRefreshing(false);
            }
        });
        return root;
    }

       public void createList(){

        mContentNotificationsArrayList.add(new ContentNotifications(1, "ingred Xyzal","05/05/2020", "7 dienas līdz termiņa beigām"));
        mContentNotificationsArrayList.add(new ContentNotifications(2, "ingred Linex","01/10/2021", "7 dienas līdz termiņa beigām"));
           mContentNotificationsArrayList.add(new ContentNotifications( 1, "Sinupret", "12/12/2020", "3 dienas līdz termiņa beigām"));

        mNotificationsViewAdapter = new NotificationsViewAdapter(NotificationsFragment.this, mContentNotificationsArrayList);
                   mNotificationsViewAdapter = new NotificationsViewAdapter(NotificationsFragment.this, mContentNotificationsArrayList);
           mRecyclerView.setAdapter(mNotificationsViewAdapter);
       }
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }


}
