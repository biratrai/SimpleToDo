package com.gooner10.simpletodo.model;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * ToDoRepositoryIml which is a realm helper class for querying realm database
 */
public class ToDoRepositoryImpl implements ToDoRepository {
    private final Realm realm;

    public ToDoRepositoryImpl(Realm realm) {
        this.realm = realm;
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
    public void editToDo(final ToDoModel toDoModel, final String mEditToDo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                toDoModel.setToDoName(mEditToDo);
            }
        });
    }

    @Override
    public ToDoModel getRealmModel(String itemName) {
        return realm.where(ToDoModel.class).equalTo(ToDoModel.ID, itemName).findFirst();
    }

    @Override
    public List<ToDoModel> getToDoList() {
        return realm.where(ToDoModel.class).findAll();
    }
}
