package com.gooner10.simpletodo;

import android.app.Activity;
import android.app.Application;

import com.gooner10.simpletodo.di.ApplicationComponent;
import com.gooner10.simpletodo.di.DaggerApplicationComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static io.realm.RealmConfiguration.Builder;

/**
 * ToDoApplication BaseApplication Class
 */
@Singleton
public class ToDoApplication extends Application implements HasActivityInjector {
    private static ApplicationComponent component;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        setUpComponents();
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

    private void setUpComponents() {
        component = DaggerApplicationComponent.builder()
                .application(this)
                .build();
        component.inject(this);
    }


    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
