package com.example.ericliu.photomosaic.util;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.ericliu.photomosaic.MyApplication;

import static android.content.ContentValues.TAG;

/**
 * Created by ericliu on 30/07/2016.
 */

public final class NetworkUtil {
    private NetworkUtil(){}





    /**
     * Simple network connection check.
     *
     */
    public static boolean checkConnection() {
        Context context = MyApplication.myApplication;
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {


            AlertDialog builder = new AlertDialog.Builder(context).setTitle("No Network")
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
            builder.show();
            Log.e(TAG, "checkConnection - no connection found");
            return false;
        }

        return true;
    }
}
