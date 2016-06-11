package com.gooner10.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.gooner10.simpletodo.todo.ToDoActivity;
import com.gooner10.simpletodo.todo.ToDoContract;

/**
 * A To Do DialogFragment that displays Dialog
 * and presents editText for user input.
 */

public class ToDoDialogFragment extends DialogFragment {
    private EditText mEditText;
    private static final String TITLE = "title";
    private ToDoContract.UserActionsListener mActionsListener;

    public ToDoDialogFragment() {
    }

    /**
     * Create a new instance of ToDoDialogFragment, providing "title"
     * as an argument.
     */
    public static ToDoDialogFragment newInstance(int title) {
        ToDoDialogFragment frag = new ToDoDialogFragment();
        Bundle args = new Bundle();
        args.putInt(TITLE, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.input_dialog_box, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int title = getArguments().getInt(TITLE);
        mEditText = (EditText) view.findViewById(R.id.addToDoText);
        getDialog().setTitle(title);
        setCancelable(false);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button cancelBtn = (Button) view.findViewById(R.id.cancel_input_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        mActionsListener = ((ToDoActivity) getActivity()).getUserActionsListener();
        Button addBtn = (Button) view.findViewById(R.id.add_input_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText != null) {
                    String m_Text = mEditText.getText().toString();
                    if (m_Text.length() == 0) {
                        mEditText.setError("To Do Item is required!");

                    } else {
                        ((ToDoActivity) getActivity()).addNewToDo(m_Text);
//                        mActionsListener.addNewToDo(m_Text);
                        dismiss();
                    }
                }
            }
        });
    }

    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // 1. Instantiate an AlertDialog.Builder with its constructor
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//
//        // 2. Chain together various setter methods to set the dialog characteristics
//        View dialogView = inflater.inflate(R.layout.input_dialog_box, null);
//        alertDialogBuilder.setView(dialogView);
//        alertDialogBuilder.setTitle("Add a New Item");
//        final EditText editText = (EditText) dialogView.findViewById(R.id.addToDoText);
//
//        // 4. Set up the button and Get the AlertDialog from create() and show
//        final AlertDialog alertDialog = alertDialogBuilder.setPositiveButton(R.string.add_btn, null).create();
//
//        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                b.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (editText != null) {
//                            String m_Text = editText.getText().toString();
//                            if (m_Text.length() == 0) {
//                                editText.setError("To Do Item is required!");
//                            } else {
////                                mActionsListener.addNewToDo(m_Text);
//                                alertDialog.dismiss();
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        alertDialog.show();
//        return alertDialog;
//    }


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
