package com.example.sids_checklist;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Profile_DeleteProfile {
    public void showDeleteProfilePopUp(View view, int profileID) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View deleteProfilePopUpView = inflater.inflate(R.layout.profile_delete, null);
        PopupWindow popupWindow = new PopupWindow(deleteProfilePopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button deletionTrueButton = deleteProfilePopUpView.findViewById(R.id.deletionTrueButton);
        Button deletionFalseButton = deleteProfilePopUpView.findViewById(R.id.deletionFalseButton);

        Profile_DatabaseHandler db = new Profile_DatabaseHandler(view.getContext());
        db.openDatabase();

        deletionTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteProfile(profileID);
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
