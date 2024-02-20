package com.example.sids_checklist;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
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
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Profile_EditProfile {
    private String colorResource;
    public void showEditProfilePopUp(View view, int profileID) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View editProfilePopUpView = inflater.inflate(R.layout.profile_new, null);
        PopupWindow popupWindow = new PopupWindow(editProfilePopUpView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

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
        profileAgePicker.init(Integer.parseInt(date[2]), (Integer.parseInt(date[1]) ), Integer.parseInt(date[0]), null);

        switch (db.getProfileInfoFromID(profileID).getProfile_color()){
            case "@colors/profileColor1": profileColorPicker1.setBackgroundResource(R.drawable.color_item_selected); break;
            case "@colors/profileColor2": profileColorPicker2.setBackgroundResource(R.drawable.color_item_selected); break;
            case "@colors/profileColor3": profileColorPicker3.setBackgroundResource(R.drawable.color_item_selected); break;
            case "@colors/profileColor4": profileColorPicker4.setBackgroundResource(R.drawable.color_item_selected); break;
            case "@colors/profileColor5": profileColorPicker5.setBackgroundResource(R.drawable.color_item_selected); break;
            case "@colors/profileColor6": profileColorPicker6.setBackgroundResource(R.drawable.color_item_selected); break;
            default: break;
        }

        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileNameText.length() != 0) {
                    ProfileModel newProfile = new ProfileModel();
                    newProfile.setUsername(profileNameText.getText().toString());
                    String profileAge = String.format("%02d/%02d/%04d", profileAgePicker.getDayOfMonth(), profileAgePicker.getMonth(), profileAgePicker.getYear());
                    newProfile.setAge(profileAge);
                    newProfile.setProfile_color(colorResource);
                    db.updateProfile(profileID, newProfile);
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
                colorResource = "@colors/profileColor1";

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
                colorResource = "@colors/profileColor2";

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
                colorResource = "@colors/profileColor3";

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
                colorResource = "@colors/profileColor4";

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
                colorResource = "@colors/profileColor5";

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
                colorResource = "@colors/profileColor6";

                profileColorPicker1.setBackgroundResource(R.drawable.color_item);
                profileColorPicker2.setBackgroundResource(R.drawable.color_item);
                profileColorPicker3.setBackgroundResource(R.drawable.color_item);
                profileColorPicker4.setBackgroundResource(R.drawable.color_item);
                profileColorPicker5.setBackgroundResource(R.drawable.color_item);

            }
        });
    }
}
