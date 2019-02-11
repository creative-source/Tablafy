package com.jantzapps.jantz.tablafy;

import android.*;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;
import static com.jantzapps.jantz.tablafy.ListenFragment.listen;
import static com.jantzapps.jantz.tablafy.ListenFragment.textSwitcher1;
import static com.jantzapps.jantz.tablafy.ListenFragment.textSwitcher2;
import static com.jantzapps.jantz.tablafy.MyStandFragment.builder;
import static java.security.AccessController.getContext;

/**
 * Created by jantz on 7/11/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ((AnimationDrawable) listen.getDrawable()).selectDrawable(0);
        listen.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        ((AnimationDrawable) listen.getDrawable()).stop();
        textSwitcher2.setText("");
        textSwitcher1.setText("Place device near music and press button below.");
        textSwitcher1.setBackgroundResource(R.drawable.drawer_item_background);
        textSwitcher2.setBackgroundColor(Color.argb(1,224,224,224));
        AnalyzeMusic.analyzingMusic("Oct",false);
        TabFragment.viewPager.setCurrentItem(1);

        final SharedPreferences preference = context.getSharedPreferences("settings.conf", Context.MODE_PRIVATE);
        final String instrument = preference.getString("instrumentPref","guitar");

        if(instrument.equals("guitar")) {}
        if(instrument.equals("piano")) {
            MyStandFragment.tabViewFlipper.setDisplayedChild(1);
        }
        if(instrument.equals("bass")) {
            MyStandFragment.tabViewFlipper.setDisplayedChild(2);
        }
        if(instrument.equals("ukulele")) {
            MyStandFragment.tabViewFlipper.setDisplayedChild(3);
        }
        if(instrument.equals("harmonica")) {
            MyStandFragment.tabViewFlipper.setDisplayedChild(4);
        }
        builder.show();
    }}
