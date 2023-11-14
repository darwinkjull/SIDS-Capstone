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

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Checklist_AddNewItem extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newItemText;
    private Button newItemSaveButton;
    private Checklist_DatabaseHandler db;

    public static Checklist_AddNewItem newInstance(){
        return new Checklist_AddNewItem();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.checklist_new, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    // Setup the text box for writing in names of added items, and save button
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newItemText = requireView().findViewById(R.id.checklistNewItemText);
        newItemSaveButton = requireView().findViewById(R.id.checklistNewItemButton);
        newItemSaveButton.setEnabled(false);

        db = new Checklist_DatabaseHandler(getActivity());
        db.openDatabase();

        List<ChecklistModel> checklist = db.getAllItems();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String item =bundle.getString("item");
            newItemText.setText(item);

            assert item != null;
            if(item.length()>0) {
                newItemSaveButton.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.colorPrimaryDark));
            }
        }

        // change color of the save button if text exists
        newItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 0){
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(Color.GRAY);
                } else {
                    newItemSaveButton.setEnabled(true);
                    newItemSaveButton.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // push the item onto the database once finalized and saved by the user
        boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(v -> {
            AtomicReference<String> session = new AtomicReference<>("null");
            String text = newItemText.getText().toString();
            if(finalIsUpdate){
                db.updateItem(bundle.getInt("id"), text);
            } else {

                try {
                    session.set(String.valueOf(checklist.get(0).getSession()));
                } catch(IndexOutOfBoundsException ex){
                    session.set(String.valueOf(Calendar.getInstance().getTime()));
                }
                ChecklistModel item = new ChecklistModel();
                item.setItem(text);
                item.setStatus(0);
                item.setSession(String.valueOf(session));
                db.insertItem(item);
            }
            dismiss();
        });
    }

    // dismissed the dialog box so close it
    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}
