package com.gooner10.simpletodo;

/**
 * Created by Gooner10 on 6/10/16.
 */

import com.gooner10.simpletodo.todo.ToDoPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
    void inject(ToDoApplication target);
    void inject(ToDoPresenter target);
}
