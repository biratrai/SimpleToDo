package com.gooner10.simpletodo.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.model.ToDoModel;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity implements Button.OnClickListener {
    private static final String ITEM_NAME = "itemName";
    EditText editText;
    Realm realm;
    ToDoModel toDoModel;

    public static Intent getEditActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, EditActivity.class);
        intent.putExtra(ITEM_NAME, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Todo use databinging
        Bundle bundle = getIntent().getExtras();
        String itemName = (String) bundle.get(ITEM_NAME);
        realm = Realm.getDefaultInstance();
        toDoModel = realm.where(ToDoModel.class).equalTo(ToDoModel.ID, itemName).findFirst();
        editText = (EditText) findViewById(R.id.editToDoText);
        if (editText != null) {
            editText.setText(toDoModel.getToDoName());
        }
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button editSaveBtn = (Button) findViewById(R.id.editSaveBtn);
        editSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editSaveBtn) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    toDoModel.setToDoName(editText.getText().toString());
                }
            });
            supportFinishAfterTransition();
        }
    }
}
