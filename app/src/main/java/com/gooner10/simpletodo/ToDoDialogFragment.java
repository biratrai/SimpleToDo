package com.gooner10.simpletodo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * A To Do DialogFragment
 */
public class ToDoDialogFragment extends DialogFragment {
    private EditText mEditText;

    public ToDoDialogFragment() {
    }

    public static ToDoDialogFragment newInstance(int title) {
        ToDoDialogFragment frag = new ToDoDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        int title = getArguments().getInt("title");
//        View view = inflater.inflate(R.layout.input_dialog_box, container);
//        mEditText = (EditText) view.findViewById(R.id.addToDoText);
//        getDialog().setTitle(title);
//
//        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        mEditText.setOnEditorActionListener(this);
//        return view;
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        // 2. Chain together various setter methods to set the dialog characteristics
        View dialogView = inflater.inflate(R.layout.input_dialog_box, null);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Add a New Item");
        final EditText editText = (EditText) dialogView.findViewById(R.id.addToDoText);


        // 4. Set up the button and Get the AlertDialog from create() and show
        final AlertDialog alertDialog = alertDialogBuilder.setPositiveButton(R.string.add_btn, null).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText != null) {
                            String m_Text = editText.getText().toString();
                            if (m_Text.length() == 0) {
                                editText.setError("To Do Item is required!");
//                                Log.d(LOG_TAG, "Error");
                            } else {
//                                mActionsListener.addNewToDo(m_Text);
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
        return alertDialog;
    }

    public interface ToDoDialogListener {
        void onFinishToDoDialog(String inputText);
    }

//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        Log.d("TAG", "actionId: " + actionId);
////        if (EditorInfo.IME_ACTION_DONE == actionId) {
//        ToDoDialogListener activity = (ToDoDialogListener) getActivity();
//        activity.onFinishToDoDialog(mEditText.getText().toString());
//        this.dismiss();
//        return true;
////        }
////        return false;
//    }
}
