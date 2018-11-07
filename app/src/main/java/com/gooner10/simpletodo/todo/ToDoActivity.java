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

import com.gooner10.simpletodo.Constants;
import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.databinding.ActivityMainBinding;
import com.gooner10.simpletodo.di.DaggerApplicationComponent;
import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;
import com.gooner10.simpletodo.todo.ToDoContract.ToDoPresenter;

import java.util.List;

public class ToDoActivity extends AppCompatActivity implements ToDoItemsAdapter.OnItemClickListener,
        ToDoContract.ToDoView {
    public static final String TAG = ToDoActivity.class.getSimpleName();
    private ToDoItemsAdapter toDoItemsAdapter;
    private FloatingActionButton fab;
    private ActivityMainBinding mainBinding;
    private ToDoRepository toDoRepository = DaggerApplicationComponent.create().toDoRepostioryProvider();
    private ToDoPresenter toDoPresenter = new ToDoPresenterImpl(this, toDoRepository);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = mainBinding.toolbar;
        setSupportActionBar(toolbar);

        RecyclerView recyclerview = mainBinding.layoutContentMain.recyclerview;

        toDoItemsAdapter = new ToDoItemsAdapter(this);
        recyclerview.setAdapter(toDoItemsAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        configureRecyclerViewSwipe(recyclerview);

        configureFabButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toDoPresenter.loadToDo();
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
                toDoPresenter.deleteToDo(toDoItemsAdapter.getItem(viewHolder.getAdapterPosition()));
                toDoPresenter.loadToDo();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Circular Reveal Animation added for the fab button in onClick
     */
    private void configureFabButton() {
        fab = mainBinding.fab;
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
                    ToDoDialogFragment mToDoDialogFragment = ToDoDialogFragment.newInstance(R.string.add_new_todo);
                    mToDoDialogFragment.show(getSupportFragmentManager(), Constants.INSTANCE.getTODO_DIALOG_FRAGMENT_TAG());
                }
            });
        }
    }

    public void addNewToDo(String newToDo) {
        toDoPresenter.addNewToDo(newToDo);
    }

    @Override
    public void showToDoUi(List<ToDoModel> mToDoList) {
        mainBinding.layoutContentMain.setUserList(mToDoList);
        toDoItemsAdapter.setItems(mToDoList);
    }

    @Override
    public void updateChanges() {
        toDoPresenter.loadToDo();
    }

    @Override
    public void onItemClick(ToDoItemsAdapter.ItemHolder item, int position) {
        Intent intent = EditActivity.getEditActivity(this, toDoItemsAdapter.getItem(position).getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, item.textItemName, Constants.INSTANCE.getSCENE_TRANSITION());
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}

