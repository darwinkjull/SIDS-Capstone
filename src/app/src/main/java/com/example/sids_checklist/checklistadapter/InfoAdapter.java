package com.example.sids_checklist.checklistadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.Info_Page_Activity;
import com.example.sids_checklist.R;
import com.example.sids_checklist.Topics;

import java.util.ArrayList;

/**
 * The InfoAdapter class is responsible for managing the list of topics in a RecyclerView.
 */
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private ArrayList<Topics> topics;

    private final Info_Page_Activity activity;

    /**
     * Constructs an InfoAdapter with the associated activity.
     *
     * @param activity The activity hosting the RecyclerView.
     */
    public InfoAdapter(Info_Page_Activity activity) {
        this.activity = activity;
    }

    /**
     * Inflates the layout from info_tiles.xml and creates a new ViewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return the viewholder
     */
    @NonNull
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_tiles, parent, false);
        return new InfoAdapter.ViewHolder(itemView);
    }

    /**
     * Binds data to the ViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.ViewHolder holder, int position) {
        Topics item = topics.get(position);
        TextView title = holder.titleView;
        title.setText(item.getTitle());
        ImageView icon = holder.iconView;
        icon.setImageResource(item.getImage());

    }

    /**
     * @return Returns the total number of items in the list.
     */
    @Override
    public int getItemCount() {
        return topics.size();
    }

    /**
     * ViewHolder class for caching view components of item layout.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        /**
         * the view of the title
         */
        public TextView titleView;
        /**
         * the view of the icon
         */
        public ImageView iconView;

        /**
         * setting the title and icon views
         * @param itemView - the view of the items
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.titleInfo);

            iconView = (ImageView) itemView.findViewById(R.id.imageInfo);
        }
    }}
