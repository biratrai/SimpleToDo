package com.gooner10.simpletodo.todo

import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.gooner10.simpletodo.Constants
import com.gooner10.simpletodo.R
import com.gooner10.simpletodo.ToDoApplication
import com.gooner10.simpletodo.edit.EditActivity
import com.gooner10.simpletodo.model.ToDoModel
import com.gooner10.simpletodo.model.ToDoRepository
import javax.inject.Inject

class ToDoActivity : AppCompatActivity(), ToDoItemsAdapter.OnItemClickListener, ToDoContract.View {
    private var toDoItemsAdapter: ToDoItemsAdapter? = null
    private var mActionsListener: ToDoContract.UserActionsListener? = null
    private var fab: FloatingActionButton? = null
//    private var mainBinding: ActivityMainBinding? = null

//    @Inject
    var toDoRepository: ToDoRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ToDoApplication.getToDoApplication().component.inject(this)
//        mainBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)

        mActionsListener = ToDoPresenter(this, toDoRepository)

//        val toolbar = mainBinding!!.toolbar
//        setSupportActionBar(toolbar)

//        val mRecyclerView = mainBinding!!.layoutContentMain.recyclerview

        toDoItemsAdapter = ToDoItemsAdapter(this)
//        mRecyclerView.adapter = toDoItemsAdapter
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        configureRecyclerViewSwipe(mRecyclerView)

        configureFabButton()
    }

    override fun onResume() {
        super.onResume()
        mActionsListener!!.loadToDo()
    }

    private fun configureRecyclerViewSwipe(mRecyclerView: RecyclerView) {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                mActionsListener!!.deleteToDo(toDoItemsAdapter!!.getItem(viewHolder.adapterPosition))
                mActionsListener!!.loadToDo()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    /**
     * Circular Reveal Animation added for the fab button in onClick
     */
    private fun configureFabButton() {
//        fab = mainBinding!!.fab
        if (fab != null) {
            fab!!.setOnClickListener {
                // Adding Circular Reveal to the fab button
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    // get the center for the clipping circle
                    val cx = fab!!.measuredWidth / 2
                    val cy = fab!!.measuredHeight / 2

                    // get the final radius for the clipping circle
                    val finalRadius = Math.max(fab!!.width, fab!!.height) / 2
//                    val anim = ViewAnimationUtils.createCircularReveal(fab, cx, cy, 0f, finalRadius.toFloat())
//                    anim.start()
                }
                val mToDoDialogFragment = ToDoDialogFragment.newInstance(R.string.add_new_todo)
                mToDoDialogFragment.show(supportFragmentManager, Constants.TODO_DIALOG_FRAGMENT_TAG)
            }
        }
    }

    fun addNewToDo(newToDo: String) {
        mActionsListener!!.addNewToDo(newToDo)
    }

    override fun showToDoUi(mToDoList: List<ToDoModel>) {
//        mainBinding!!.layoutContentMain.userList = mToDoList
        toDoItemsAdapter!!.setItems(mToDoList)
    }

    override fun updateChanges() {
        mActionsListener!!.loadToDo()
    }

    override fun onItemClick(item: ToDoItemsAdapter.ItemHolder, position: Int) {
        val intent = EditActivity.getEditActivity(this, toDoItemsAdapter!!.getItem(position).id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, item.textItemName, Constants.SCENE_TRANSITION)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    companion object {
        val LOG_TAG = ToDoActivity::class.java.simpleName
    }
}

