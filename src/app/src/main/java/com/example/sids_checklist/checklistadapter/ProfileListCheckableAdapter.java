package com.example.sids_checklist.checklistadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import java.util.HashSet;
import java.util.List;

/**
 * The ProfileListCheckableAdapter class manages a list of profiles in a RecyclerView
 * with checkable items.
 */
public class ProfileListCheckableAdapter extends  RecyclerView.Adapter<ProfileListCheckableAdapter.ViewHolder>{
    private final List<ProfileModel> profileList;
    private final Profile_DatabaseHandler profile_db;
    private final Context context;
    private final HashSet<String> itemsChecked; // Use hashset to get unique list of elements

    /**
     * Constructs a ProfileListCheckableAdapter with the associated database handler,
     * context, and profile list.
     *
     * @param profile_db   The database handler for profile data.
     * @param context      The context of the RecyclerView.
     * @param profileList  The list of profiles to be displayed.
     */
    public ProfileListCheckableAdapter(Profile_DatabaseHandler profile_db, Context context, List<ProfileModel> profileList){
        this.profile_db = profile_db;
        this.context = context;
        this.profileList = profileList;
        itemsChecked = new HashSet<>();
    }

    /**
     * Inflates the layout from profile_card_checkable.xml and creates a new ViewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param position The view type of the new View.
     * @return viewholder
     */
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View profileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card_checkable, parent, false);
        return new ViewHolder(profileView);
    }

    /**
     * Binds data to the ViewHolder and handles checkbox state changes.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(ViewHolder holder, int position){
        profile_db.openDatabase();
        ProfileModel profile = profileList.get(position);
        Profile_DateHandler profile_date = new Profile_DateHandler(profile);

        holder.username.setText(profile.getUsername());
        holder.age.setText(profile_date.getWeeks());
        @SuppressLint("DiscouragedApi") int colorID = context.getResources().getIdentifier(profile.getProfile_color(), "color", context.getPackageName());
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

    /**
     * @return Returns the total number of items in the list.
     */
    public int getItemCount(){return profileList.size();}

    /**
     * ViewHolder class for caching view components of item layout.
     */
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

    /**
     * @return Returns a list of checked profile usernames.
     *
     */
    public List<String> getCheckedProfiles(){
        return new ArrayList<>(itemsChecked);
    }
}
