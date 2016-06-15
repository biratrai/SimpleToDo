package com.gooner10.simpletodo.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.gooner10.simpletodo.Constants;
import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.ToDoApplication;
import com.gooner10.simpletodo.databinding.ActivityEditBinding;
import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;

import javax.inject.Inject;

public class EditActivity extends AppCompatActivity {
    private EditText editText;
    private ToDoModel toDoModel;

    @Inject
    ToDoRepository toDoRepository;

    public static Intent getEditActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, EditActivity.class);
        intent.putExtra(Constants.ITEM_NAME, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToDoApplication.getToDoApplication().getComponent().inject(this);

        ActivityEditBinding editBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit);

        String itemName = (String) getIntent().getExtras().get(Constants.ITEM_NAME);

        toDoModel = toDoRepository.getRealmModel(itemName);

        editText = editBinding.editToDoText;
        editText.setText(toDoModel.getToDoName());

        Button editSaveBtn = editBinding.editSaveBtn;
        editSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                toDoRepository.editToDo(toDoModel, editText.getText().toString());
                supportFinishAfterTransition();
            }
        });
    }
}
