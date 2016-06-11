package com.gooner10.simpletodo;

import android.app.Application;

import com.gooner10.simpletodo.model.ToDoRepository;
import com.gooner10.simpletodo.model.ToDoRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * A module for Android-specific dependencies which require a {@link android.content.Context} or
 * {@link android.app.Application} to create.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    public ToDoRepository provideRepository(Realm realm) {
        return new ToDoRepositoryImpl(realm);
    }
}
