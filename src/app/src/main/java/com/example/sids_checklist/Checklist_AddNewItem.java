package com.example.sids_checklist;

/*
Adding helper class to deal with adding new items to the database,
as well as ensuring that all items are valid and have non-empty names

TODO: Add duplicate checking
*/

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class Checklist_AddNewItem extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newItemText;
    private Button newItemSaveButton;
    private Checklist_DatabaseHandler db;
    private String profileUsername;

    /* Pass profile ID into the fragment as an argument so it can be used in the definition of
     new checklist items.
     */
    public static Checklist_AddNewItem newInstance() {
//        Log.d("tag", "Now importing intent to fragment");
//        Checklist_AddNewItem frag = new Checklist_AddNewItem();
//        Bundle args = new Bundle();
//        args.putInt("profile_ID", profileID);
//        Log.d("tag", "Intent imported");
//        frag.setArguments(args);
//        Log.d("tag", "Fragment arguments set");
        return new Checklist_AddNewItem();
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
//        Bundle args = getArguments();
//        profileID = args.getInt("profile_ID", -1);
//        assert (profileID != -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checklist_new, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //Get profileID from Checklist_Activity.java
        Checklist_Activity checklist = (Checklist_Activity) getActivity();
        profileUsername = checklist.getProfileUsername();

        return view;
    }

    // Setup the text box for writing in names of added items, and save button
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newItemText = requireView().findViewById(R.id.checklistNewItemText);
        newItemSaveButton = requireView().findViewById(R.id.checklistNewItemButton);
        newItemSaveButton.setEnabled(false);

        db = new Checklist_DatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String item = bundle.getString("item");
            newItemText.setText(item);

            assert item != null;
            if (item.length() > 0) {
                newItemSaveButton.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.colorPrimaryDark));
            }
        }

        // change color of the save button if text exists
        newItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(Color.GRAY);
                } else {
                    newItemSaveButton.setEnabled(true);
                    newItemSaveButton.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // push the item onto the database once finalized and saved by the user
        boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(v -> {
            String text = newItemText.getText().toString();
            if (finalIsUpdate) {
                db.updateItem(bundle.getInt("id"), text, profileUsername);
            } else {
                ChecklistModel item = new ChecklistModel();
                item.setItem(text);
                item.setStatus(0);
                db.insertItem(item, profileUsername);
            }
            dismiss();
        });
    }

    // dismissed the dialog box so close it
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
