package com.example.sids_checklist;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseLongArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistprofiles.Profile_PopUpInterface;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Profile_EditProfile {
    private String colorResource;
    private Profile_PopUpInterface popUpInterface;
    public void showEditProfilePopUp(View view, Context context, int profileID) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View editProfilePopUpView = inflater.inflate(R.layout.profile_new, null);
        PopupWindow popupWindow = new PopupWindow(editProfilePopUpView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        this.popUpInterface = (Profile_PopUpInterface) context;

        Button acceptInfoTrueButton = editProfilePopUpView.findViewById(R.id.acceptInfoTrue);
        Button acceptInfoFalseButton = editProfilePopUpView.findViewById(R.id.acceptInfoFalse);
        TextView profileNameText = editProfilePopUpView.findViewById(R.id.profileName);
        DatePicker profileAgePicker = editProfilePopUpView.findViewById(R.id.profileAge);

        ImageView profileColorPicker1 = editProfilePopUpView.findViewById(R.id.profileColor1);
        ImageView profileColorPicker2 = editProfilePopUpView.findViewById(R.id.profileColor2);
        ImageView profileColorPicker3 = editProfilePopUpView.findViewById(R.id.profileColor3);
        ImageView profileColorPicker4 = editProfilePopUpView.findViewById(R.id.profileColor4);
        ImageView profileColorPicker5 = editProfilePopUpView.findViewById(R.id.profileColor5);
        ImageView profileColorPicker6 = editProfilePopUpView.findViewById(R.id.profileColor6);

        Profile_DatabaseHandler db = new Profile_DatabaseHandler(view.getContext());
        db.openDatabase();

        profileNameText.setText(db.getProfileInfoFromID(profileID).getUsername());

        String[] date = db.getProfileInfoFromID(profileID).getAge().split("/");
        // Log.d("tag", "Year: " + Integer.parseInt(date[2]) + " Month: " + Integer.parseInt(date[1]) + " Day: " + Integer.parseInt(date[0]));
        profileAgePicker.init(Integer.parseInt(date[2]), (Integer.parseInt(date[1]) - 1), Integer.parseInt(date[0]), null);

        switch (db.getProfileInfoFromID(profileID).getProfile_color()){
            case "profileColor1": profileColorPicker1.setBackgroundResource(R.drawable.color_item_selected); colorResource = "profileColor1"; break;
            case "profileColor2": profileColorPicker2.setBackgroundResource(R.drawable.color_item_selected); colorResource = "profileColor2"; break;
            case "profileColor3": profileColorPicker3.setBackgroundResource(R.drawable.color_item_selected); colorResource = "profileColor3"; break;
            case "profileColor4": profileColorPicker4.setBackgroundResource(R.drawable.color_item_selected); colorResource = "profileColor4"; break;
            case "profileColor5": profileColorPicker5.setBackgroundResource(R.drawable.color_item_selected); colorResource = "profileColor5"; break;
            case "profileColor6": profileColorPicker6.setBackgroundResource(R.drawable.color_item_selected); colorResource = "profileColor6"; break;
            default: break;
        }

        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileNameText.length() != 0) {
                    ProfileModel newProfile = new ProfileModel();
                    newProfile.setUsername(profileNameText.getText().toString());
                    int selectedMonth = profileAgePicker.getMonth() + 1; // Datepicker's months are indexed to 0.
                    String profileAge = String.format("%02d/%02d/%04d", profileAgePicker.getDayOfMonth(), selectedMonth, profileAgePicker.getYear());
                    newProfile.setAge(profileAge);
                    newProfile.setProfile_color(colorResource);
                    db.updateProfile(profileID, newProfile);
                    popUpInterface.refreshProfiles();
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

        /**
         * Each onClickListener sets the currently selected color to the address of the colors.xml
         * value. Each press changes the background of the icon to have a black border, which shows
         * users what color they have selected.
         */
        profileColorPicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileColorPicker1.setBackgroundResource(R.drawable.color_item_selected);
                colorResource = "profileColor1";

                profileColorPicker2.setBackgroundResource(R.drawable.color_item);
                profileColorPicker3.setBackgroundResource(R.drawable.color_item);
                profileColorPicker4.setBackgroundResource(R.drawable.color_item);
                profileColorPicker5.setBackgroundResource(R.drawable.color_item);
                profileColorPicker6.setBackgroundResource(R.drawable.color_item);

            }
        });

        profileColorPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileColorPicker2.setBackgroundResource(R.drawable.color_item_selected);
                colorResource = "profileColor2";

                profileColorPicker3.setBackgroundResource(R.drawable.color_item);
                profileColorPicker4.setBackgroundResource(R.drawable.color_item);
                profileColorPicker5.setBackgroundResource(R.drawable.color_item);
                profileColorPicker6.setBackgroundResource(R.drawable.color_item);
                profileColorPicker1.setBackgroundResource(R.drawable.color_item);

            }
        });

        profileColorPicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileColorPicker3.setBackgroundResource(R.drawable.color_item_selected);
                colorResource = "profileColor3";

                profileColorPicker4.setBackgroundResource(R.drawable.color_item);
                profileColorPicker5.setBackgroundResource(R.drawable.color_item);
                profileColorPicker6.setBackgroundResource(R.drawable.color_item);
                profileColorPicker1.setBackgroundResource(R.drawable.color_item);
                profileColorPicker2.setBackgroundResource(R.drawable.color_item);

            }
        });

        profileColorPicker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileColorPicker4.setBackgroundResource(R.drawable.color_item_selected);
                colorResource = "profileColor4";

                profileColorPicker5.setBackgroundResource(R.drawable.color_item);
                profileColorPicker6.setBackgroundResource(R.drawable.color_item);
                profileColorPicker1.setBackgroundResource(R.drawable.color_item);
                profileColorPicker2.setBackgroundResource(R.drawable.color_item);
                profileColorPicker3.setBackgroundResource(R.drawable.color_item);

            }
        });

        profileColorPicker5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileColorPicker5.setBackgroundResource(R.drawable.color_item_selected);
                colorResource = "profileColor5";

                profileColorPicker6.setBackgroundResource(R.drawable.color_item);
                profileColorPicker1.setBackgroundResource(R.drawable.color_item);
                profileColorPicker2.setBackgroundResource(R.drawable.color_item);
                profileColorPicker3.setBackgroundResource(R.drawable.color_item);
                profileColorPicker4.setBackgroundResource(R.drawable.color_item);

            }
        });

        profileColorPicker6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileColorPicker6.setBackgroundResource(R.drawable.color_item_selected);
                colorResource = "profileColor6";

                profileColorPicker1.setBackgroundResource(R.drawable.color_item);
                profileColorPicker2.setBackgroundResource(R.drawable.color_item);
                profileColorPicker3.setBackgroundResource(R.drawable.color_item);
                profileColorPicker4.setBackgroundResource(R.drawable.color_item);
                profileColorPicker5.setBackgroundResource(R.drawable.color_item);

            }
        });
    }
}
