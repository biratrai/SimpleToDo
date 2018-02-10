package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.di.ActivityScoped;
import com.gooner10.simpletodo.model.ToDoRepository;

import dagger.Module;
import dagger.Provides;


@Module
public class ToDoActivityModule {

    @Provides
    @ActivityScoped
    ToDoContract.View provideToDoView(ToDoActivity view) {
        return view;
    }

    @Provides
    @ActivityScoped
    ToDoContract.Presenter provideToDoPresenter(ToDoContract.View view, ToDoRepository toDoRepository) {
        return new ToDoPresenter(view, toDoRepository);
    }
}
