package com.gooner10.simpletodo.di;

/**
 * Dagger Component
 */

import com.gooner10.simpletodo.model.ToDoRepository;
import com.gooner10.simpletodo.model.ToDoRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,
        ToDoRepositoryModule.class})
public interface ApplicationComponent {
    ToDoRepository toDoRepositoryProvider();
}
