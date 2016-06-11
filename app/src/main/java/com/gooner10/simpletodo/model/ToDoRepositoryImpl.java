package com.gooner10.simpletodo.model;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by Gooner10 on 6/10/16.
 */
public class ToDoRepositoryImpl implements ToDoRepository {
    Realm realm;

    public ToDoRepositoryImpl() {
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void deleteToDo(ToDoModel toDoModel) {
        realm.beginTransaction();
        toDoModel.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void saveToDo(final String mNewToDo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ToDoModel toDoModel = realm.createObject(ToDoModel.class);
                toDoModel.setId(UUID.randomUUID().toString());
                toDoModel.setToDoName(mNewToDo);
            }
        });
    }

    @Override
    public List<ToDoModel> getToDoList() {
        return realm.where(ToDoModel.class).findAll();
    }
}
