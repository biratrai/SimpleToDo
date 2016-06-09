package com.gooner10.simpletodo.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Realm model class object for a ToDoList.
 */
public class ToDoModel extends RealmObject{
    @PrimaryKey
    private String id;
    private String toDoTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToDoName() {
        return toDoTitle;
    }

    public void setToDoName(String toDoName) {
        this.toDoTitle = toDoName;
    }
}
