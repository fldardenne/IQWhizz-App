package com.example.iqwhizz;

import android.app.Application;
import android.content.Context;

public class AppContextProvider extends Application {

    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContextProvider.sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return AppContextProvider.sContext;
    }

}
