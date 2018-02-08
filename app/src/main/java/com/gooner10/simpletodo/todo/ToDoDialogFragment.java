package com.gooner10.simpletodo.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gooner10.simpletodo.R;

/**
 * A To Do DialogFragment that displays Dialog
 * and presents editText for user input.
 */

public class ToDoDialogFragment extends DialogFragment {
    private EditText mEditText;
    private static final String TITLE = "title";
//    private InputDialogBoxBinding dialogBoxBinding;

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
//        dialogBoxBinding = DataBindingUtil.inflate(inflater, R.layout.input_dialog_box, container, false);
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int title = getArguments().getInt(TITLE);
//        mEditText = dialogBoxBinding.addToDoText;
//        final TextInputLayout mInputText = dialogBoxBinding.textInputLayout;
        getDialog().setTitle(title);
        setCancelable(false);
        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

//        Button cancelBtn = dialogBoxBinding.cancelInputBtn;
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        Button addBtn = dialogBoxBinding.addInputBtn;
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mEditText != null) {
//                    String m_Text = mEditText.getText().toString().trim();
//                    if (m_Text.length() == 0) {
//                        mInputText.setError(getString(R.string.empty_input_error));
//
//                    } else {
//                        ((ToDoActivity) getActivity()).addNewToDo(m_Text);
//                        dismiss();
//                    }
//                }
//            }
//        });
    }
}
