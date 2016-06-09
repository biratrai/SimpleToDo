package com.gooner10.simpletodo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A To Do DialogFragment
 */
public class ToDoDialogFragment extends DialogFragment implements EditText.OnEditorActionListener {
    private EditText mEditText;

    public ToDoDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_dialog_box, container);
//        mEditText = (EditText) view.findViewById(R.id.addToDoText);
//        getDialog().setTitle("Enter New ToDo");
//
//        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        mEditText.setOnEditorActionListener(this);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.input_dialog_box, null);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Add a New Item");
//        alertDialogBuilder.setMessage("Add a New Item");
        alertDialogBuilder.setPositiveButton("Add",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                mEditText.getText().toString();
//                getActivity().onActionModeFinished(mEditText.getText().toString());
            }
        });
        return alertDialogBuilder.create();
    }

    public interface ToDoDialogListener {
        void onFinishToDoDialog(String inputText);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d("TAG", "actionId: " + actionId);
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
            ToDoDialogListener activity = (ToDoDialogListener) getActivity();
            activity.onFinishToDoDialog(mEditText.getText().toString());
            this.dismiss();
            return true;
//        }
//        return false;
    }
}
