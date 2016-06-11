package com.gooner10.simpletodo.todo;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.ToDoApplication;
import com.gooner10.simpletodo.databinding.ActivityMainBinding;
import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;

import java.util.List;

import javax.inject.Inject;

public class ToDoActivity extends AppCompatActivity implements ToDoItemsAdapter.OnItemClickListener,
        ToDoContract.View {
    public static final String LOG_TAG = ToDoActivity.class.getSimpleName();
    private ToDoItemsAdapter toDoItemsAdapter;
    private ToDoContract.UserActionsListener mActionsListener;
    private FloatingActionButton fab;
    private ActivityMainBinding mainBinding;
    private static final String SCENE_TRANSITION = "toDoTransition";

    @Inject
    ToDoRepository toDoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToDoApplication.getToDoApplication().getComponent().inject(this);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mActionsListener = new ToDoPresenter(this, toDoRepository);

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

    @Override
    protected void onResume() {
        super.onResume();
        mActionsListener.loadToDo();
    }

    private void configureRecyclerViewSwipe(RecyclerView mRecyclerView) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
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
                    ToDoDialogFragment editNameDialog = ToDoDialogFragment.newInstance(R.string.add_new_todo);
                    editNameDialog.show(getSupportFragmentManager(), "fragment_edit_name");
                }
            });
        }
    }

    public void addNewToDo(String newToDo) {
        mActionsListener.addNewToDo(newToDo);
    }

    @Override
    public void showToDoUi(List<ToDoModel> mToDoList) {
        mainBinding.layoutContentMain.setUserList(mToDoList);
        toDoItemsAdapter.setItems(mToDoList);
    }

    @Override
    public void updateChanges() {
        mActionsListener.loadToDo();
    }

    @Override
    public void onItemClick(ToDoItemsAdapter.ItemHolder item, int position) {
        Intent intent = EditActivity.getEditActivity(this, toDoItemsAdapter.getItem(position).getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, item.textItemName, SCENE_TRANSITION);
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

