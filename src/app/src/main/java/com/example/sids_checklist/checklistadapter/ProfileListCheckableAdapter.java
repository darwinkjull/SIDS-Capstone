package com.example.sids_checklist.checklistadapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistprofiles.Profile_DateHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.security.auth.callback.Callback;

public class ProfileListCheckableAdapter extends  RecyclerView.Adapter<ProfileListCheckableAdapter.ViewHolder>{
    private List<ProfileModel> profileList;
    private Profile_DatabaseHandler profile_db;
    private Context context;
    private HashSet<String> itemsChecked; // Use hashset to get unique list of elements

    public ProfileListCheckableAdapter(Profile_DatabaseHandler profile_db, Context context, List<ProfileModel> profileList){
        this.profile_db = profile_db;
        this.context = context;
        this.profileList = profileList;
        itemsChecked = new HashSet<String>();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View profileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card_checkable, parent, false);
        return new ViewHolder(profileView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        profile_db.openDatabase();
        ProfileModel profile = profileList.get(position);
        Profile_DateHandler profile_date = new Profile_DateHandler(profile);

        holder.username.setText(profile.getUsername());
        holder.age.setText(profile_date.getWeeks());
        int colorID = context.getResources().getIdentifier(profile.getProfile_color(), "color", context.getPackageName());
        if (colorID != 0){holder.icon.setColorFilter(ContextCompat.getColor(context, colorID), PorterDuff.Mode.SRC_IN);}

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                itemsChecked.add(profile.getUsername());
            }
            else {
                itemsChecked.remove(profile.getUsername());
            }
        });
    }

    public int getItemCount(){return profileList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView age;
        ImageView icon;
        CheckBox checkbox;

        ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.profile_username);
            age = view.findViewById(R.id.profile_age);
            icon = view.findViewById(R.id.profile_icon);
            checkbox = view.findViewById(R.id.profile_checkbox);
        }
    }

    public List<String> getCheckedProfiles(){
        return new ArrayList<>(itemsChecked);
    }

}
