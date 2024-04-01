package com.example.sids_checklist;

import android.content.DialogInterface;

public interface DialogCloseListener {
    /**
     * Handles the closure of the dialog.
     *
     * @param dialog The dialog that is being closed.
     */
    void handleDialogClose(DialogInterface dialog);
}
