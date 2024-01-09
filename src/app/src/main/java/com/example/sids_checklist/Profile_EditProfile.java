package com.example.sids_checklist;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Profile_EditProfile {
    public void showEditProfilePopUp(View view, int profileID) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View editProfilePopUpView = inflater.inflate(R.layout.profile_new, null);
        PopupWindow popupWindow = new PopupWindow(editProfilePopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button acceptInfoTrueButton = editProfilePopUpView.findViewById(R.id.acceptInfoTrue);
        Button acceptInfoFalseButton = editProfilePopUpView.findViewById(R.id.acceptInfoFalse);
        TextView profileNameText = editProfilePopUpView.findViewById(R.id.profileName);

        Profile_DatabaseHandler db = new Profile_DatabaseHandler(view.getContext());
        db.openDatabase();

        profileNameText.setText(db.getProfileInfo(profileID).getUsername());
        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileNameText.length() != 0) {
                    ProfileModel newProfile = new ProfileModel();
                    newProfile.setUsername(profileNameText.getText().toString());
                    db.updateUsername(profileID, newProfile.getUsername());
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
