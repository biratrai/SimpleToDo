package com.gooner10.simpletodo;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * ToDoApplication BaseApplication Class
 */
public class ToDoApplication extends DaggerApplication {
    private ApplicationComponent component;
    private static ToDoApplication toDoApplication;

    public static ToDoApplication getToDoApplication() {
        return toDoApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Configure Realm for the application
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("todo.realm")
                .build();

        Realm.deleteRealm(realmConfiguration); //Deletes the realm,
        // use when you want a clean slate for dev/etc

        // Make this Realm the default
        Realm.setDefaultConfiguration(realmConfiguration);
        toDoApplication = this;

        // Initialize timber on debug build
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        ApplicationComponent applicationComponent = DaggerAppCo
        return null;
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
