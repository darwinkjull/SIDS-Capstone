package com.example.sids_checklist;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

public class Sharing_SendInfo {
    public void showAddProfilePopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View sendInfoPopUpView = inflater.inflate(R.layout.sharing_send, null);
        PopupWindow popupWindow = new PopupWindow(sendInfoPopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button acceptInfoTrueButton = sendInfoPopUpView.findViewById(R.id.confirmOperation);
        Button acceptInfoFalseButton = sendInfoPopUpView.findViewById(R.id.cancelOperation);

        ProgressBar sharingProgressBar = sendInfoPopUpView.findViewById(R.id.sharingProgressBar);
        sharingProgressBar.setVisibility(View.INVISIBLE);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(view.getContext());
        profile_db.openDatabase();

        acceptInfoTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharingProgressBar.setVisibility(View.VISIBLE);
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
