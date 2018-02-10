package com.gooner10.simpletodo.di;

import com.gooner10.simpletodo.edit.EditActivity;
import com.gooner10.simpletodo.todo.ToDoActivity;
import com.gooner10.simpletodo.todo.ToDoActivityModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binding Module for Activities
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = ToDoActivityModule.class)
    abstract ToDoActivity bindToDoActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract EditActivity bindEditActivity();
}
