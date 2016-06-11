package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;
import com.gooner10.simpletodo.model.ToDoRepositoryImpl;

import io.realm.Realm;

/**
 * Listens to user actions from the UI ({@link ToDoActivity}), retrieves the data and updates the
 * UI as required.
 */
public class ToDoPresenter implements ToDoContract.UserActionsListener {
    private final ToDoRepository mToDoRepository;
    private final ToDoContract.View mToDoView;
    Realm realm;

    public ToDoPresenter(ToDoContract.View mToDoView) {
        this.mToDoView = mToDoView;
        mToDoRepository = new ToDoRepositoryImpl();
    }

    @Override
    public void loadToDo() {
        mToDoView.showToDoUi(mToDoRepository.getToDoList());
    }

    @Override
    public void addNewToDo(final String mNewToDo) {
        mToDoRepository.saveToDo(mNewToDo);
        mToDoView.updateChanges();
    }

    @Override
    public void deleteToDo(ToDoModel toDoModel) {
        mToDoRepository.deleteToDo(toDoModel);
        mToDoView.updateChanges();
    }
}



