package com.gooner10.simpletodo.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.model.ToDoModel;

import java.util.List;

import io.realm.Realm;

public class ToDoActivity extends AppCompatActivity implements ToDoAdapter.OnItemClickListener,
        FloatingActionButton.OnClickListener,
        ToDoContract.View {
    public static final String LOG_TAG = ToDoActivity.class.getSimpleName();
    private ToDoAdapter toDoAdapter;
    private Realm realm;
    private ToDoContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Instance of Realm
        realm = Realm.getDefaultInstance();

        // Get Instance of ToDoPresenter
        mActionsListener = new ToDoPresenter(this);

        // Initialize RecycleView with adapter
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        toDoAdapter = new ToDoAdapter(this);
        toDoAdapter.setOnItemClickListener(this);

        // Load the Ui with To Do List from ToDoPresenter
        mActionsListener.loadToDo();

        // Adding Swipe to delete on RecyclerView with ItemTouchHelper
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from RealmDatabase and update the view
                mActionsListener.deleteToDo(toDoAdapter.getItem(viewHolder.getAdapterPosition()));
                mActionsListener.loadToDo();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // Adding ToDoAdapter and Layout to the RecyclerView
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(toDoAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        // Floating Action button onClickListener to Add new ToDoItem
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
        mActionsListener.loadToDo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    // Displays AlertDialog and presents editText for user input
    private void addToDoFromDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ToDoActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.input_dialog_box, null);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Add a New Item");
        final EditText editText = (EditText) dialogView.findViewById(R.id.addToDoText);
        // 3. Set up the input
        // Specify the type of input expected;

        // 4. Set up the buttons
        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText != null) {
                    String m_Text = editText.getText().toString();
                    mActionsListener.addNewToDo(m_Text);
                }
            }
        });
        // 5. Get the AlertDialog from create() and show
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }


    // onClick Listener for the fab button
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            addToDoFromDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void showToDoUi(List<ToDoModel> mToDoList) {
        TextView textView = (TextView) findViewById(R.id.noTaskText);
        if (mToDoList.isEmpty()) {

            textView.setVisibility(View.VISIBLE);
        } else {
            toDoAdapter.setItems(mToDoList);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateChanges() {
        mActionsListener.loadToDo();
    }

    @Override
    public void onItemClick(ToDoAdapter.ItemHolder item, int position) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("ItemName", toDoAdapter.getItem(position).getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, item.textItemName, "toDoTransition");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    //    // Returns the data from the Realm Database
//    private List<String> getRealmData() {
//        List<String> items = new ArrayList<>();
//        RealmResults<ToDoModel> mToDoList =
//                realm.where(ToDoModel.class).findAll();
//        if (!mToDoList.isEmpty()) {
//            for (ToDoModel toDoModel : mToDoList) {
//                items.add(toDoModel.getToDoName());
//            }
//            toDoAdapter.setItems(items);
//        }
//        return items;
//    }

    //    // Inserts new RealmObjects into Realm Database
//    private void insertIntoRealm(final String m_text) {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                ToDoModel toDoModel = realm.createObject(ToDoModel.class);
//                toDoModel.setId(UUID.randomUUID().toString());
//                toDoModel.setToDoName(m_text);
//            }
//        });
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, "requestCode " + requestCode, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "resultCode " + resultCode, Toast.LENGTH_SHORT).show();
//
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK) {
//                String result = data.getStringExtra("result");
////                toDoAdapter.add(0, result);
////                Toast.makeText(this, "result " + result, Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }

//    @Override
//    public void onFinishToDoDialog(String inputText) {
//        Log.d("TAG", "inputText: " + inputText);
////        String m_Text = input.getText().toString();
//        toDoAdapter.add(0, inputText);
//    }

    //    private void showEditDialog() {
//        FragmentManager fm = getSupportFragmentManager();
//        ToDoDialogFragment editNameDialog = new ToDoDialogFragment();
//        editNameDialog.show(fm, "fragment_edit_name");
//    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
}

