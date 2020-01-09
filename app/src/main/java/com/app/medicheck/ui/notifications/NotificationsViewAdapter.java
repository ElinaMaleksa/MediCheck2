package com.app.medicheck.ui.notifications;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.app.medicheck.R;
import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsViewAdapter extends RecyclerView.Adapter<NotificationsViewAdapter.RecyclerViewHolder>{

    NotificationsFragment mNotificationsFragment;
    ArrayList<ContentNotifications> mContentNotificationsList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mName_N, mBestBefore_N, mWarning_N;


        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            mName_N = itemView.findViewById(R.id.product_name_not);
            mBestBefore_N = itemView.findViewById(R.id.best_before_not);
            mWarning_N = itemView.findViewById(R.id.warning_to_expire);

        }
    }

    public NotificationsViewAdapter (NotificationsFragment notificationsFragment, ArrayList<ContentNotifications> contentNotifications) {
        this.mNotificationsFragment = notificationsFragment;
        this.mContentNotificationsList = contentNotifications;
    }

    @Override
    @NonNull
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mNotificationsFragment.getContext());
        View view = layoutInflater.inflate(R.layout.notification_list_item, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        holder.mName_N.setText(mContentNotificationsList.get(i).getNameNot());
        holder.mBestBefore_N.setText(mContentNotificationsList.get(i).getBestBeforeNot());
        if (mContentNotificationsList.get(i).getDaysDiff() <= 0){
            holder.mWarning_N.setText("Has expired");
        }else {
            holder.mWarning_N.setText("Will expire after "  +mContentNotificationsList.get(i).getDaysDiff() + " days");
        }
    }
    @Override
    public int getItemCount() {
        return (mContentNotificationsList == null) ? 0 : mContentNotificationsList.size();
    }
}

