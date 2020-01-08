package com.app.medicheck.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.medicheck.MainActivity;
import com.app.medicheck.R;

import java.net.URI;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mProfileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        mProfileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
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
                        Toast ToolbarToast = Toast.makeText(getContext(),"We did it!",Toast.LENGTH_SHORT);
                        ToolbarToast.show();
                        break;
                    case R.id.profile_toolbar_item_buy_products:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://lipsum.com"));
                        startActivity(intent);
                        break;
                        default:
                            break;
                }
                Toast ToolbarToast = Toast.makeText(getContext(),item.getTitle(),Toast.LENGTH_SHORT);
                ToolbarToast.show();
                return false;
            }
        });
    }

    public void buildAboutDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.profile_about_title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}