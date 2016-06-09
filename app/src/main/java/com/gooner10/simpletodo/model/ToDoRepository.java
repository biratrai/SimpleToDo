package com.gooner10.simpletodo.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main entry point for accessing ToDo data.
 */
public interface ToDoRepository {
    interface LoadToDoCallback {
        void onToDoLoaded(List<ToDoModel> toDoList);
    }

    void getToDo(@NonNull LoadToDoCallback toDoCallback);
    void saveNote();
    void refreshData();
}
