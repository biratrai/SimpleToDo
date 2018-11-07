package com.gooner10.simpletodo;

import android.app.Application;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static io.realm.RealmConfiguration.Builder;

/**
 * ToDoApplication BaseApplication Class
 */
@Singleton
public class ToDoApplication extends Application {

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

}
