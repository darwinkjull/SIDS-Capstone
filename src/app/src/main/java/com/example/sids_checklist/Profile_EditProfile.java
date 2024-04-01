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
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

/**
 * Profile_EditProfile class handles the functionality related to editing a user profile.
 */
public class Profile_EditProfile {
    private String colorResource;
    private Profile_PopUpInterface popUpInterface;

    /**
     * Displays the edit profile pop-up window.
     * @param view The view from which the pop-up is triggered.
     * @param context The context of the application.
     * @param profileID The ID of the profile to be edited.
     */
    public void showEditProfilePopUp(View view, Context context, int profileID) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View editProfilePopUpView = inflater.inflate(R.layout.profile_new, null);
        PopupWindow popupWindow = new PopupWindow(editProfilePopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
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

            /**
             * Handles the onClick event for accepting profile information changes.
             * If the profile name is not empty, it creates a new ProfileModel object with updated information,
             * sets the username, age, and profile color based on the entered values, and updates the profile in the database.
             * Finally, it triggers a refresh of the profiles displayed and dismisses the pop-up window.
             *
             * @param v The View that was clicked.
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
                    db.updateProfile(profileID, newProfile);
                    popUpInterface.refreshProfiles();
                    popupWindow.dismiss();
                }
            }
        });
        acceptInfoFalseButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for dismissing the pop-up window.
             * Dismisses the pop-up window when the associated view is clicked.
             *
             * @param v The View that was clicked.
             */
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        profileColorPicker1.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for selecting profile color option 1.
             * Sets the selected color to profileColor1 and updates UI accordingly by highlighting the selected color
             * and resetting the others.
             *
             * @param v The View that was clicked.
             */
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
            /**
             * OnClickListener for selecting profile color option 2.
             * Sets the selected color to profileColor1 and updates UI accordingly by highlighting the selected color
             * and resetting the others.
             *
             * @param v The View that was clicked.
             */
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
            /**
             * OnClickListener for selecting profile color option 3.
             * Sets the selected color to profileColor1 and updates UI accordingly by highlighting the selected color
             * and resetting the others.
             *
             * @param v The View that was clicked.
             */
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
            /**
             * OnClickListener for selecting profile color option 4.
             * Sets the selected color to profileColor1 and updates UI accordingly by highlighting the selected color
             * and resetting the others.
             *
             * @param v The View that was clicked.
             */
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
            /**
             * OnClickListener for selecting profile color option 5.
             * Sets the selected color to profileColor1 and updates UI accordingly by highlighting the selected color
             * and resetting the others.
             *
             * @param v The View that was clicked.
             */
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
            /**
             * OnClickListener for selecting profile color option 6.
             * Sets the selected color to profileColor1 and updates UI accordingly by highlighting the selected color
             * and resetting the others.
             *
             * @param v The View that was clicked.
             */
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
