package com.gooner10.simpletodo;

/**
 * Dagger Component
 */

import android.app.Application;

import com.gooner10.simpletodo.todo.ToDoActivity;
import com.gooner10.simpletodo.todo.ToDoActivityBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApplicationModule.class, ToDoActivityBindingModule.class})
public interface ApplicationComponent extends
        AndroidInjector<ToDoApplication> {

    @Override
    void inject(ToDoApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(ToDoActivity toDoActivity);

//    void inject(EditActivity editActivity);
}
