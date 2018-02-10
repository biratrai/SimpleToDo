package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;

import javax.inject.Inject;

/**
 * Listens to user actions from the UI ({@link ToDoActivity}), retrieves the data and updates the
 * UI as required.
 */
public class ToDoPresenterImpl implements ToDoContract.ToDoPresenter {

    private final ToDoRepository mToDoRepository;
    private final ToDoContract.ToDoView mToDoToDoView;

    @Inject
    public ToDoPresenterImpl(ToDoContract.ToDoView mToDoToDoView, ToDoRepository toDoRepository) {
        this.mToDoToDoView = mToDoToDoView;
        this.mToDoRepository = toDoRepository;
    }

    @Override
    public void loadToDo() {
        mToDoToDoView.showToDoUi(mToDoRepository.getToDoList());
    }

    @Override
    public void addNewToDo(final String mNewToDo) {
        mToDoRepository.saveToDo(mNewToDo);
        mToDoToDoView.updateChanges();
    }

    @Override
    public void deleteToDo(ToDoModel toDoModel) {
        mToDoRepository.deleteToDo(toDoModel);
        mToDoToDoView.updateChanges();
    }
}



