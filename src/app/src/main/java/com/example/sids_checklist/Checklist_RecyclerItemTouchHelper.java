package com.example.sids_checklist;

/*
Adding recycler-view helper for addition of items, user prompts for saving items,
and drawing of icons for deleting/editing items.
*/


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sids_checklist.checklistadapter.ChecklistAdapter;


public class Checklist_RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private final ChecklistAdapter adapter;
    public Checklist_RecyclerItemTouchHelper(ChecklistAdapter adapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        final int position = viewHolder.getAbsoluteAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){
            adapter.deleteItem(position);
        } else {
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dx, float dy, int actionState, boolean isActive){
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isActive);

        Drawable icon;
        ColorDrawable background;
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 24;

        if(dx > 0){
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(),
                    R.color.colorPrimaryDark));
        } else{
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_delete);
            background = new ColorDrawable(Color.RED);
        }

        boolean isCanceled = dx == 0f && !isActive;

        if (isCanceled) {
            background = new ColorDrawable(Color.TRANSPARENT);
            background.draw(c);
            return;
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = (itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2);
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if(dx > 0){ // swiping right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin +icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft()
                    + ((int)dx) + backgroundCornerOffset, itemView.getBottom());
        } else if(dx < 0){ // swiping left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int)dx) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else {
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
        icon.draw(c);
    }
}
