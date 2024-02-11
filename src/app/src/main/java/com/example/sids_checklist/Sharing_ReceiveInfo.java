package com.example.sids_checklist;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class Sharing_ReceiveInfo {
    public void showAddProfilePopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View receiveInfoPopupView = inflater.inflate(R.layout.sharing_receive, null);
        PopupWindow popupWindow = new PopupWindow(receiveInfoPopupView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button cancelOperationButton = receiveInfoPopupView.findViewById(R.id.cancelOperation);

        cancelOperationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
