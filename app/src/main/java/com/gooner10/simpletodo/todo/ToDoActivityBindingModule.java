package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.ApplicationModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binding Module for ToDoActivity
 */

@Singleton
@Module
public abstract class ToDoActivityBindingModule {

    @ContributesAndroidInjector(modules = ApplicationModule.class)
    abstract ToDoActivity toDoActivity();
}
