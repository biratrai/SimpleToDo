package com.gooner10.simpletodo.model;

import java.util.List;

/**
 * Main entry point for accessing RealmData Base.
 */
public interface ToDoRepository {

    void deleteToDoFromDatabase(ToDoModel toDoModel);

    void saveToDo(String mNewToDo);

    void editToDo(ToDoModel toDoModel, String mEditToDo);

    ToDoModel getRealmModel(String toDoModel);

    List<ToDoModel> getToDoList();
}
