package com.test.movies;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by andres on 18/03/17.
 */

public class MovieApp extends Application {

    private static MovieApp mInstance;


    public static MovieApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Check internet connection
     *
     * @return
     */
    public static boolean checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mInstance.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo i = connectivityManager.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        return i.isAvailable();
    }
}
