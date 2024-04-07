package com.example.sids_checklist;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

/**
 * This file is responsible for the addition of new items to the checklist
 * activity. It creates a pop-up dialog option to input a new item to the list,
 * and dismisses the dialog upon completion.
 *
 * The Checklist was inspired by Mohit Singh's To Do List App Android Studio Tutorial
 */
public class Checklist_AddNewItem extends BottomSheetDialogFragment {
    /**
     * tag for the fragment
     */
    public static final String TAG = "ActionBottomDialog";
    private EditText newItemText;
    private Button newItemSaveButton;
    private Checklist_DatabaseHandler db;
    private int profileID;

    /**
     * Returns this object
     * @return Checklist_AddNewItem() - this object
     */
    public static Checklist_AddNewItem newInstance() {

        return new Checklist_AddNewItem();
        }

    /**
     * this is a constructor which handles the creation of the activity.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    /**
     *
     * It is a constructor which handles the creation of the dialog container
     *
     * @param inflater  the inflater.
     * @param container  the container.
     * @param savedInstanceState  the saved instance state.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checklist_new, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //Get profileID from Checklist_Activity.java
        Checklist_Activity checklist = (Checklist_Activity) getActivity();
        assert checklist != null;
        profileID = checklist.getProfileID();

        return view;
    }

    // Setup the text box for writing in names of added items, and save button

    /**
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
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
            /**
             * It is invoked before the text is changed
             *
             * @param s      The text before it has been changed.
             * @param start  The position of the beginning of the changed part in the text.
             * @param count  The length of the changed part in the text.
             * @param after  The length of the new text that replaces the changed part.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * This method is called when the text in the EditText is changed.
             * It is invoked after the text has been changed, to change the colour
             * of the save button displayed on screen, indicating to the user
             * that they have input a valid string.
             *
             * @param s      The modified text.
             * @param start  The starting position of the changed part in the text.
             * @param before The length of the text that has been replaced.
             * @param count  The length of the new text.
             */
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

            /**
             * It is invoked after the text is changed
             *
             * @param s      The text before it has been changed.
             */
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // push the item onto the database once finalized and saved by the user
        boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(v -> {
            String text = newItemText.getText().toString();
            if (finalIsUpdate) {
                db.updateItem(bundle.getInt("id"), text, profileID);
            } else {
                ChecklistModel item = new ChecklistModel();
                item.setItem(text);
                item.setStatus(0);
                db.insertItem(item, profileID);
            }
            dismiss();
        });
    }

    // dismissed the dialog box so close it

    /**
     * This method notifies the activity implementing the DialogCloseListener interface about the
     * dismissal of the dialog, allowing it to handle the event accordingly
     *
     * @param dialog the dialog that was dismissed will be passed into the
     *               method
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
