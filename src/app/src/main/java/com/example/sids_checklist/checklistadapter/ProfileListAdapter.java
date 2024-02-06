package com.example.sids_checklist.checklistadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    private List<ProfileModel> profileList;
    private Profile_DatabaseHandler profile_db;
    private Context context;

    public ProfileListAdapter(Profile_DatabaseHandler profile_db, Context context, List<ProfileModel> profileList){
        this.profile_db = profile_db;
        this.context = context;
        this.profileList = profileList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View profileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card, parent, false);
        return new ViewHolder(profileView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        profile_db.openDatabase();
        ProfileModel profile = profileList.get(position);
        holder.username.setText(profile.getUsername());
        holder.age.setText(profile.getAge());
    }

    public int getItemCount(){return profileList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView age;

        ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.profile_username);
            age = view.findViewById(R.id.profile_age);
        }
    }


}
