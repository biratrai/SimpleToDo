package com.gooner10.simpletodo.di;

import com.gooner10.simpletodo.model.ToDoRepository;
import com.gooner10.simpletodo.model.ToDoRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * A module for Android-specific dependencies which require a {@link android.content.Context} or
 * {@link android.app.Application} to create.
 */
@Module
public class ApplicationModule {

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    ToDoRepository provideRepository(Realm realm) {
        return new ToDoRepositoryImpl(realm);
    }
}
