package br.edu.ifspsaocarlos.agenda;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Andrey on 20/11/2016.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Inicializa o Realm
        Realm.init(this);
    }
}
