package com.gooner10.simpletodo.di;

import com.gooner10.simpletodo.model.ToDoRepository;
import com.gooner10.simpletodo.model.ToDoRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;

/**
 * A module for Android-specific dependencies which require a {@link android.content.Context} or
 * {@link android.app.Application} to create.
 */
@Module(includes = AndroidInjectionModule.class)
public class ApplicationModule {

    @Provides
    @Singleton
    ToDoRepository provideRepository(ToDoRepositoryImpl repository) {
        return repository;
    }
}
