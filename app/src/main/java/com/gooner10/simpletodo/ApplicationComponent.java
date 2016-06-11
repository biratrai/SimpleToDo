package com.gooner10.simpletodo;

/**
 * Created by Gooner10 on 6/10/16.
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
