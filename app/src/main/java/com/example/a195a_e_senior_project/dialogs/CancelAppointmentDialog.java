package com.example.a195a_e_senior_project.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a195a_e_senior_project.R;
import com.example.a195a_e_senior_project.ViewScheduleActivity;

public class CancelAppointmentDialog extends DialogFragment {

    public interface CancelAppointmentDialogListener {
        public void onCancelPositiveClick(DialogFragment dialog);
        public void onCancelNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    CancelAppointmentDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (CancelAppointmentDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("must implement CancelAppointmentDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Cancel Appointment?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCancelPositiveClick(CancelAppointmentDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCancelNegativeClick(CancelAppointmentDialog.this);
                    }
                });
        return builder.create();
    }
}
