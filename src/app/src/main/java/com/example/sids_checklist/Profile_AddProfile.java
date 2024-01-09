package com.example.sids_checklist;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sids_checklist.checklistmodel.ProfileModel;
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

        Profile_DatabaseHandler db = new Profile_DatabaseHandler(view.getContext());
        db.openDatabase();
        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileNameText.length() != 0) {
                    ProfileModel newProfile = new ProfileModel();
                    newProfile.setUsername(profileNameText.getText().toString());
                    db.insertProfile(newProfile);
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
