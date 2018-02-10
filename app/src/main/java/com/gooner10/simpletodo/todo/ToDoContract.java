package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;

import java.util.List;

/**
 * * This specifies the contract between the view and the toDoPresenter.
 */
public interface ToDoContract {
    interface ToDoView {
        void showToDoUi(List<ToDoModel> toDoItems);

//        void showAddToDo();

        void updateChanges();

//        void showEditToDo();
    }

    interface ToDoPresenter {
        /**
         * Loads @{>}
         */
        void loadToDo();

        void addNewToDo(String mNewToDo);

//        void getRealmModel();

        void deleteToDo(ToDoModel toDoModel);
    }
}
