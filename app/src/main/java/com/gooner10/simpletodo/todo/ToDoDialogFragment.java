package com.gooner10.simpletodo.todo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.databinding.InputDialogBoxBinding;

import hugo.weaving.DebugLog;

/**
 * A To Do DialogFragment that displays Dialog
 * and presents editText for user input.
 */

public class ToDoDialogFragment extends DialogFragment {
    private TextInputEditText inputEditText;
    private static final String TITLE = "title";
    private InputDialogBoxBinding dialogBoxBinding;

    public ToDoDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
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
    @DebugLog
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dialogBoxBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.input_dialog_box, container, false);
        return dialogBoxBinding.getRoot();
    }

    @Override
    @DebugLog
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int title = getArguments().getInt(TITLE);
        inputEditText = dialogBoxBinding.addToDoText;
        final TextInputLayout mInputText = dialogBoxBinding.textInputLayout;
        getDialog().setTitle(title);
        setCancelable(false);
        inputEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button cancelBtn = dialogBoxBinding.cancelInputBtn;
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button addBtn = dialogBoxBinding.addInputBtn;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEditText != null) {
                    String m_Text = inputEditText.getText().toString().trim();
                    if (m_Text.length() == 0) {
                        mInputText.setError(getString(R.string.empty_input_error));

                    } else {
                        ((ToDoActivity) getActivity()).addNewToDo(m_Text);
                        dismiss();
                    }
                }
            }
        });
    }
}
