package com.example.sids_checklist.checklistadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;
import com.example.sids_checklist.checklistprofiles.Profile_DateHandler;

import java.util.List;

/**
 * The ProfileListAdapter class is responsible for managing the list of profiles in a RecyclerView.
 */
public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    private final List<ProfileModel> profileList;
    private final Profile_DatabaseHandler profile_db;
    private final Context context;
    private OnClickListener onClickListener;

    /**
     * Constructs a ProfileListAdapter with the associated database handler, context, and profile list.
     *
     * @param profile_db   The database handler for profile data.
     * @param context      The context of the RecyclerView.
     * @param profileList  The list of profiles to be displayed.
     */
    public ProfileListAdapter(Profile_DatabaseHandler profile_db, Context context, List<ProfileModel> profileList){
        this.profile_db = profile_db;
        this.context = context;
        this.profileList = profileList;
    }

    /**
     * Inflates the layout from profile_card.xml and creates a new ViewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param position The view type of the new View.
     * @return the view
     */
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View profileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card, parent, false);
        return new ViewHolder(profileView);
    }

    /**
     * bind the view
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            /**
             * check to see if a profile has been selected
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if (onClickListener != null){
                    onClickListener.onClick(profile);
                }
            }
        });

    }

    /**
     * set the listener for clicking a profile
     *
     * @param onClickListener the event listener
     */
    public void setOnClickListener(ProfileListAdapter.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    /**
     * Sets the onClickListener for the adapter.
     */
    public interface OnClickListener{
        void onClick(ProfileModel profile);
    }

    /**
     * @return Returns the total number of items in the list.
     */
    public int getItemCount(){return profileList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView age;
        ImageView icon;

        /**
         * ViewHolder class for caching view components of item layout.
         *
         * @param view the view passed
         */
        ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.profile_username);
            age = view.findViewById(R.id.profile_age);
            icon = view.findViewById(R.id.profile_icon);
        }
    }
}
