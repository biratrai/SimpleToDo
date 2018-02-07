package com.gooner10.simpletodo.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.gooner10.simpletodo.Constants
import com.gooner10.simpletodo.R
import com.gooner10.simpletodo.ToDoApplication
import com.gooner10.simpletodo.databinding.ActivityEditBinding
import com.gooner10.simpletodo.model.ToDoModel
import com.gooner10.simpletodo.model.ToDoRepository
import javax.inject.Inject

class EditActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var toDoModel: ToDoModel? = null

    @Inject
    private var toDoRepository: ToDoRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToDoApplication.getToDoApplication().component.inject(this)

        val editBinding = DataBindingUtil.setContentView<ActivityEditBinding>(this, R.layout.activity_edit)

        val itemName = intent.extras!!.get(Constants.ITEM_NAME) as String

        toDoModel = toDoRepository!!.getRealmModel(itemName)

        editText = editBinding.editToDoText
        editText!!.setText(toDoModel!!.toDoName)

        val editSaveBtn = editBinding.editSaveBtn
        editSaveBtn.setOnClickListener { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            toDoRepository!!.editToDo(toDoModel, editText!!.text.toString())
            supportFinishAfterTransition()
        }
    }

    companion object {

        fun getEditActivity(activity: Activity, id: String): Intent {
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra(Constants.ITEM_NAME, id)
            return intent
        }
    }
}
