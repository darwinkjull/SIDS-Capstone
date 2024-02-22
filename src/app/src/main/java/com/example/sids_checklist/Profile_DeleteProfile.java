package com.example.sids_checklist;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.sids_checklist.checklistprofiles.Profile_PopUpInterface;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Profile_DeleteProfile {
    private Profile_PopUpInterface popUpInterface;
    public void showDeleteProfilePopUp(View view, Context context, int profileID) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View deleteProfilePopUpView = inflater.inflate(R.layout.profile_delete, null);
        PopupWindow popupWindow = new PopupWindow(deleteProfilePopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        this.popUpInterface = (Profile_PopUpInterface) context;

        Button deletionTrueButton = deleteProfilePopUpView.findViewById(R.id.deletionTrueButton);
        Button deletionFalseButton = deleteProfilePopUpView.findViewById(R.id.deletionFalseButton);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(view.getContext());
        profile_db.openDatabase();

        Checklist_DatabaseHandler checklist_db = new Checklist_DatabaseHandler(view.getContext());
        checklist_db.openDatabase();

        Checklist_UtilDatabaseHandler checklist_util_db = new Checklist_UtilDatabaseHandler(view.getContext());
        checklist_util_db.openDatabase();

        deletionTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_db.deleteProfile(profileID);
                checklist_db.deleteTable(profileID);
                checklist_util_db.deleteTable(profileID);
                popUpInterface.refreshProfiles();
                popupWindow.dismiss();
            }
        });
        deletionFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
