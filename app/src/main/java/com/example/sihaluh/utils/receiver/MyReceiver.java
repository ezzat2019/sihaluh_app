package com.example.sihaluh.utils.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;

public class MyReceiver extends BroadcastReceiver {
    private AlertDialog alertDialog;



    @Override
    public void onReceive(Context context, Intent intent) {

        showInternetDialog(context,false);

        if (intent!=null)
        {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION))
            {
                ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getActiveNetworkInfo()!=null)
                {
                    showInternetDialog(context,false);
                }
                else
                {
                    showInternetDialog(context,true);
                }

            }
        }

    }

    private void showInternetDialog(Context context,boolean show) {
        createInternetDialog(context);
        if (show) {
            alertDialog.show();
        } else {
            alertDialog.dismiss();
        }

    }

    private void createInternetDialog(Context context) {
        alertDialog = new AlertDialog.Builder(context).setTitle("Internet Connection!")
                .setMessage("No Internet Connection enable internet and try again!").setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getActiveNetworkInfo()!=null)
                        {
                            showInternetDialog(context,false);
                        }
                        else
                        {
                            showInternetDialog(context,true);
                        }
                    }
                }).setCancelable(false).create();

    }

    public static void startReceiver(Context context,BroadcastReceiver receiver) {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver,intentFilter);
    }
    public static void stopReceiver(Context context,BroadcastReceiver receiver) {
     context.unregisterReceiver(receiver);
    }

}