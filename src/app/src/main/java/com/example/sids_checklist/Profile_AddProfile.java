package com.example.sids_checklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistprofiles.Profile_PopUpInterface;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A BottomSheetDialogFragment used for adding a new profile.
 */
public class Profile_AddProfile extends BottomSheetDialogFragment {

    private String colorResource;

    private Profile_PopUpInterface popUpInterface;
    /**
     * Displays the add profile popup.
     *
     * @param view    The view from which the popup is invoked.
     * @param context The context of the invoking activity.
     */
    public void showAddProfilePopUp(View view, Context context) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View addProfilePopUpView = inflater.inflate(R.layout.profile_new, null);
        PopupWindow popupWindow = new PopupWindow(addProfilePopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        this.popUpInterface = (Profile_PopUpInterface) context;

        Button acceptInfoTrueButton = addProfilePopUpView.findViewById(R.id.acceptInfoTrue);
        Button acceptInfoFalseButton = addProfilePopUpView.findViewById(R.id.acceptInfoFalse);
        TextView profileNameText = addProfilePopUpView.findViewById(R.id.profileName);
        DatePicker profileAgePicker = addProfilePopUpView.findViewById(R.id.profileAge);

        ImageView profileColorPicker1 = addProfilePopUpView.findViewById(R.id.profileColor1);
        ImageView profileColorPicker2 = addProfilePopUpView.findViewById(R.id.profileColor2);
        ImageView profileColorPicker3 = addProfilePopUpView.findViewById(R.id.profileColor3);
        ImageView profileColorPicker4 = addProfilePopUpView.findViewById(R.id.profileColor4);
        ImageView profileColorPicker5 = addProfilePopUpView.findViewById(R.id.profileColor5);
        ImageView profileColorPicker6 = addProfilePopUpView.findViewById(R.id.profileColor6);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(view.getContext());
        profile_db.openDatabase();

        Checklist_DatabaseHandler checklist_db = new Checklist_DatabaseHandler(view.getContext());
        checklist_db.openDatabase();

        Checklist_UtilDatabaseHandler checklist_util_db = new Checklist_UtilDatabaseHandler(view.getContext());
        checklist_util_db.openDatabase();

        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Overrides the onClick method to handle the event when a profile is added.
             * If the profile name is not empty, it creates a new profile, sets its attributes,
             * inserts it into the database, creates tables for associated checklists, refreshes the
             * profiles list, and dismisses the popup window.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if (profileNameText.length() != 0) {
                    ProfileModel newProfile = new ProfileModel();
                    newProfile.setUsername(profileNameText.getText().toString());
                    int selectedMonth = profileAgePicker.getMonth() + 1; // Datepicker's months are indexed to 0.
                    @SuppressLint("DefaultLocale") String profileAge = String.format("%02d/%02d/%04d", profileAgePicker.getDayOfMonth(), selectedMonth, profileAgePicker.getYear());
                    newProfile.setAge(profileAge);
                    newProfile.setProfile_color(colorResource);
                    profile_db.insertProfile(newProfile);
                    int profileID = profile_db.getIDByUsername(newProfile.getUsername());
                    checklist_db.createTable(profileID);
                    checklist_util_db.createTable(profileID);
                    popUpInterface.refreshProfilesAdded(profileID);
                    popupWindow.dismiss();
                }
            }
        });
        acceptInfoFalseButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the onClick event for dismissing a popup window.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        profileColorPicker1.setOnClickListener(v -> {
            profileColorPicker1.setBackgroundResource(R.drawable.color_item_selected);
            colorResource = "profileColor1";

            profileColorPicker2.setBackgroundResource(R.drawable.color_item);
            profileColorPicker3.setBackgroundResource(R.drawable.color_item);
            profileColorPicker4.setBackgroundResource(R.drawable.color_item);
            profileColorPicker5.setBackgroundResource(R.drawable.color_item);
            profileColorPicker6.setBackgroundResource(R.drawable.color_item);

        });

        profileColorPicker2.setOnClickListener(v -> {
            profileColorPicker2.setBackgroundResource(R.drawable.color_item_selected);
            colorResource = "profileColor2";

            profileColorPicker3.setBackgroundResource(R.drawable.color_item);
            profileColorPicker4.setBackgroundResource(R.drawable.color_item);
            profileColorPicker5.setBackgroundResource(R.drawable.color_item);
            profileColorPicker6.setBackgroundResource(R.drawable.color_item);
            profileColorPicker1.setBackgroundResource(R.drawable.color_item);

        });

        profileColorPicker3.setOnClickListener(v -> {
            profileColorPicker3.setBackgroundResource(R.drawable.color_item_selected);
            colorResource = "profileColor3";

            profileColorPicker4.setBackgroundResource(R.drawable.color_item);
            profileColorPicker5.setBackgroundResource(R.drawable.color_item);
            profileColorPicker6.setBackgroundResource(R.drawable.color_item);
            profileColorPicker1.setBackgroundResource(R.drawable.color_item);
            profileColorPicker2.setBackgroundResource(R.drawable.color_item);

        });

        profileColorPicker4.setOnClickListener(v -> {
            profileColorPicker4.setBackgroundResource(R.drawable.color_item_selected);
            colorResource = "profileColor4";

            profileColorPicker5.setBackgroundResource(R.drawable.color_item);
            profileColorPicker6.setBackgroundResource(R.drawable.color_item);
            profileColorPicker1.setBackgroundResource(R.drawable.color_item);
            profileColorPicker2.setBackgroundResource(R.drawable.color_item);
            profileColorPicker3.setBackgroundResource(R.drawable.color_item);

        });

        profileColorPicker5.setOnClickListener(v -> {
            profileColorPicker5.setBackgroundResource(R.drawable.color_item_selected);
            colorResource = "profileColor5";

            profileColorPicker6.setBackgroundResource(R.drawable.color_item);
            profileColorPicker1.setBackgroundResource(R.drawable.color_item);
            profileColorPicker2.setBackgroundResource(R.drawable.color_item);
            profileColorPicker3.setBackgroundResource(R.drawable.color_item);
            profileColorPicker4.setBackgroundResource(R.drawable.color_item);

        });

        profileColorPicker6.setOnClickListener(v -> {
            profileColorPicker6.setBackgroundResource(R.drawable.color_item_selected);
            colorResource = "profileColor6";

            profileColorPicker1.setBackgroundResource(R.drawable.color_item);
            profileColorPicker2.setBackgroundResource(R.drawable.color_item);
            profileColorPicker3.setBackgroundResource(R.drawable.color_item);
            profileColorPicker4.setBackgroundResource(R.drawable.color_item);
            profileColorPicker5.setBackgroundResource(R.drawable.color_item);

        });
    }
}
