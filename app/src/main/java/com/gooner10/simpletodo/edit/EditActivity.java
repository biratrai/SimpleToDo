package com.gooner10.simpletodo.edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.model.ToDoModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditActivity extends AppCompatActivity implements Button.OnClickListener {
    EditText editText;
    Realm realm;
    RealmResults<ToDoModel> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle bundle = getIntent().getExtras();
        String itemName = (String) bundle.get("ItemName");
        realm = Realm.getDefaultInstance();
        results = realm.where(ToDoModel.class).equalTo("id", itemName).findAll();
        editText = (EditText) findViewById(R.id.editToDoText);
        if (editText != null) {
            editText.setText(results.get(0).getToDoName());
        }

        Button editSaveBtn = (Button) findViewById(R.id.editSaveBtn);
        editSaveBtn.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        supportFinishAfterTransition();
//        this.finish();
        Log.d("ToDoActivity", "onPause:--> ");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editSaveBtn) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ToDoModel toDoModel = results.get(0);
                    toDoModel.setToDoName(editText.getText().toString());
                    Log.d("EditActivity", "execute: " + results.get(0).getToDoName());
                    Log.d("EditActivity", "execute: " + editText.getText());
                    Log.d("EditActivity", "execute: " + results.get(0).getId());
                }
            });

            RealmResults<ToDoModel> mToDoList =
                    realm.where(ToDoModel.class).findAll();
            for(ToDoModel toDoModel:mToDoList){
                Log.d("EditActivity", "toDoModel: " + toDoModel.getToDoName());

            }
            Log.d("ToDoActivity", "onClick: ");

            finish();
//            supportFinishAfterTransition();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ToDoActivity", "onStop:--> ");
//        supportFinishAfterTransition();
//        this.finish();
    }
}
