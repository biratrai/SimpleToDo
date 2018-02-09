package com.gooner10.simpletodo;

/**
 * Dagger Component
 */

import android.app.Application;

import com.gooner10.simpletodo.todo.ToDoActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        Builder application(Application application);
//
//        ApplicationComponent build();
//    }

//    void inject(ToDoActivity toDoActivity);

//    void inject(EditActivity editActivity);
}
