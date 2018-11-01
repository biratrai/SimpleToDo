package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Listens to user actions from the UI ({@link ToDoActivity}), retrieves the data and updates the
 * UI as required.
 */
@Singleton
public class ToDoPresenterImpl implements ToDoContract.ToDoPresenter {

    private final ToDoRepository toDoRepository;
    private final ToDoContract.ToDoView toDoView;

    @Inject
    public ToDoPresenterImpl(ToDoContract.ToDoView toDoView, ToDoRepository toDoRepository) {
        this.toDoView = toDoView;
        this.toDoRepository = toDoRepository;
    }

    @Override
    public void loadToDo() {
        toDoView.showToDoUi(toDoRepository.getToDoList());
    }

    @Override
    public void addNewToDo(final String newToDo) {
        toDoRepository.saveToDo(newToDo);
        toDoView.updateChanges();
    }

    @Override
    public void deleteToDo(ToDoModel toDoModel) {
        toDoRepository.deleteToDoFromDatabase(toDoModel);
        toDoView.updateChanges();
    }
}



