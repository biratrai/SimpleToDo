package com.gooner10.simpletodo;

import com.gooner10.simpletodo.di.ApplicationComponent;
import com.gooner10.simpletodo.di.DaggerApplicationComponent;

import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static io.realm.RealmConfiguration.Builder;

/**
 * ToDoApplication BaseApplication Class
 */
@Singleton
public class ToDoApplication extends DaggerApplication implements HasActivityInjector {
    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        // Configure Realm for the application
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new Builder()
                .name("todo.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

//        Realm.deleteRealm(realmConfiguration); //Deletes the realm,
        // use when you want a clean slate for dev/etc

        // Make this Realm the default
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder()
                .application(this)
                .build();
    }
}
