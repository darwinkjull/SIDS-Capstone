package com.example.sids_checklist;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Profile_AddProfile {

    public void showAddProfilePopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View addProfilePopUpView = inflater.inflate(R.layout.profile_new, null);
        PopupWindow popupWindow = new PopupWindow(addProfilePopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button acceptInfoTrueButton = addProfilePopUpView.findViewById(R.id.acceptInfoTrue);
        Button acceptInfoFalseButton = addProfilePopUpView.findViewById(R.id.acceptInfoFalse);
        TextView profileNameText = addProfilePopUpView.findViewById(R.id.profileName);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(view.getContext());
        profile_db.openDatabase();

        Checklist_DatabaseHandler checklist_db = new Checklist_DatabaseHandler(view.getContext());
        checklist_db.openDatabase();

        Checklist_UtilDatabaseHandler checklist_util_db = new Checklist_UtilDatabaseHandler(view.getContext());
        checklist_util_db.openDatabase();

        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileNameText.length() != 0) {
                    ProfileModel newProfile = new ProfileModel();
                    newProfile.setUsername(profileNameText.getText().toString());
                    profile_db.insertProfile(newProfile);
                    int profileID = profile_db.getIDByUsername(newProfile.getUsername());
                    checklist_db.createTable(profileID);
                    checklist_util_db.createTable(profileID);
                    popupWindow.dismiss();
                }
            }
        });
        acceptInfoFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
