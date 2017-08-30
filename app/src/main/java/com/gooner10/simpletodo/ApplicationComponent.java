package com.gooner10.simpletodo;

/**
 * Dagger Component
 */

import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.todo.ToDoActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(ToDoActivity toDoActivity);

    void inject(EditActivity editActivity);
}
