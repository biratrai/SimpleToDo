package com.gooner10.simpletodo;

/**
 * Dagger Component
 */

import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.todo.ToDoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ToDoActivity toDoActivity);

    void inject(EditActivity editActivity);
}
