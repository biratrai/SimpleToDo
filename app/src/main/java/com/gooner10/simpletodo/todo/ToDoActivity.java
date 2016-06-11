package com.gooner10.simpletodo.todo;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.ToDoDialogFragment;
import com.gooner10.simpletodo.databinding.ActivityMainBinding;
import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.model.ToDoModel;

import java.util.List;

public class ToDoActivity extends AppCompatActivity implements ToDoItemsAdapter.OnItemClickListener,
        ToDoContract.View {
    public static final String LOG_TAG = ToDoActivity.class.getSimpleName();
    private ToDoItemsAdapter toDoItemsAdapter;
    private ToDoContract.UserActionsListener mActionsListener;
    private FloatingActionButton fab;
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Todo Use Dagger to inject
        mActionsListener = new ToDoPresenter(this);

        Toolbar toolbar = mainBinding.toolbar;
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = mainBinding.layoutContentMain.recyclerview;
        // Todo Remove context
        toDoItemsAdapter = new ToDoItemsAdapter(this);
        toDoItemsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(toDoItemsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        configureRecyclerViewSwipe(mRecyclerView);

        configureFabButton();
    }

    private void configureRecyclerViewSwipe(RecyclerView mRecyclerView) {
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
                mActionsListener.deleteToDo(toDoItemsAdapter.getItem(viewHolder.getAdapterPosition()));
                mActionsListener.loadToDo();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Circular Reveal Animation added for the fab button in onClick
     */
    private void configureFabButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Adding Circular Reveal to the fab button
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        // get the center for the clipping circle
                        int cx = fab.getMeasuredWidth() / 2;
                        int cy = fab.getMeasuredHeight() / 2;

                        // get the final radius for the clipping circle
                        int finalRadius = Math.max(fab.getWidth(), fab.getHeight()) / 2;
                        Animator anim = ViewAnimationUtils.createCircularReveal(fab, cx, cy, 0, finalRadius);
                        anim.start();
                    }
//                    addToDoFromDialog();
                    ToDoDialogFragment editNameDialog = ToDoDialogFragment.newInstance(R.string.add_new_todo);
                    editNameDialog.show(getSupportFragmentManager(), "fragment_edit_name");
                }
            });
        }
    }

    public ToDoContract.UserActionsListener getUserActionsListener() {
        return mActionsListener;
    }

    public void addNewToDo(String newToDo) {
        mActionsListener.addNewToDo(newToDo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume: ");
        mActionsListener.loadToDo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause: ");
    }

    /**
     * Displays AlertDialog and presents editText for user input
     */
    private void addToDoFromDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ToDoActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        LayoutInflater inflater = getLayoutInflater();
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
                                Log.d(LOG_TAG, "Error");
                            } else {
                                mActionsListener.addNewToDo(m_Text);
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
    }

    @Override
    public void showToDoUi(List<ToDoModel> mToDoList) {
        mainBinding.layoutContentMain.setUserList(mToDoList);
        Log.d(LOG_TAG, "showToDoUi: " + mToDoList);
        toDoItemsAdapter.setItems(mToDoList);
    }

    @Override
    public void updateChanges() {
        mActionsListener.loadToDo();
        Log.d(LOG_TAG, "updateChanges ToDo");
    }

    @Override
    public void onItemClick(ToDoItemsAdapter.ItemHolder item, int position) {
        Intent intent = EditActivity.getEditActivity(this, toDoItemsAdapter.getItem(position).getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, item.textItemName, "toDoTransition");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

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

