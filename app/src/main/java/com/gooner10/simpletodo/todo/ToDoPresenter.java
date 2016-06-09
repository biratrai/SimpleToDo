package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Listens to user actions from the UI ({@link ToDoActivity}), retrieves the data and updates the
 * UI as required.
 */
public class ToDoPresenter implements ToDoContract.UserActionsListener {
    //        private final ToDoRepository mToDoRepository;
    private final ToDoContract.View mToDoView;
    //    private List<ToDoModel> mToDoList;
    private RealmResults mToDoList;
    Realm realm;

    public ToDoPresenter(ToDoContract.View mToDoView) {
        this.mToDoView = mToDoView;
        realm = Realm.getDefaultInstance();
    }

    // Returns the data from the Realm Database
    @Override
    public void loadToDo() {
        // Get result from Realm Query
        RealmResults<ToDoModel> mToDoList =
                realm.where(ToDoModel.class).findAll();
//        List<String> toDoItems = new ArrayList<>();
//        if (!mToDoList.isEmpty()) {
//            for (ToDoModel toDoModel : mToDoList) {
//                toDoItems.add(toDoModel.getToDoName());
//            }
//        }
        mToDoView.showToDoUi(mToDoList);
    }

    @Override
    public void addNewToDo(final String mNewToDo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ToDoModel toDoModel = realm.createObject(ToDoModel.class);
                toDoModel.setId(UUID.randomUUID().toString());
                toDoModel.setToDoName(mNewToDo);
            }
        });
        mToDoView.updateChanges();
    }

//    @Override
//    public void editToDo() {
//
//    }

    @Override
    public void deleteToDo(ToDoModel toDoModel) {
        realm.beginTransaction();
        toDoModel.deleteFromRealm();
        realm.commitTransaction();
        mToDoView.updateChanges();
    }
}



