package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.di.ActivityScoped;
import com.gooner10.simpletodo.model.ToDoRepository;

import dagger.Module;
import dagger.Provides;


@Module
public class ToDoActivityModule {

    @Provides
    @ActivityScoped
    ToDoContract.ToDoView provideToDoView(ToDoActivity view) {
        return view;
    }

    @Provides
    @ActivityScoped
    ToDoContract.ToDoPresenter provideToDoPresenter(ToDoContract.ToDoView toDoView
            , ToDoRepository toDoRepository) {
        return new ToDoPresenterImpl(toDoView, toDoRepository);
    }
}
