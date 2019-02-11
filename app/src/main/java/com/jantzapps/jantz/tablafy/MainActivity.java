package com.jantzapps.jantz.tablafy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;
    LinearLayout Help,Home,Settings;
    final FragmentManager fm = getSupportFragmentManager();

    public static TextView tvPageTitle;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerLayout.closeDrawer(navigationView,false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        else {
            toolbar.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    new AlertDialog.Builder(this)
                            .setTitle("Record Audio")
                            .setMessage("You must allow audio recording to use this app." +
                                    "\n" +
                                    "\n" +
                                    "Microphone permission can be granted in Android settings.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Prompt the user once explanation has been shown
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.RECORD_AUDIO},
                                            1);
                                    startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                                }
                            })
                            .create()
                            .show();
                     }
                return;
            }
        }
    }

    private static final int TIME_INTERVAL = 2500; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        GuitarFragment guitarFragment = (GuitarFragment)fm.findFragmentByTag("GUITAR");
        PianoFragment pianoFragment = (PianoFragment)fm.findFragmentByTag("PIANO");
        BassFragment bassFragment = (BassFragment)fm.findFragmentByTag("BASS");
        UkuleleFragment ukuleleFragment = (UkuleleFragment)fm.findFragmentByTag("UKULELE");
        HarmonicaFragment harmonicaFragment = (HarmonicaFragment)fm.findFragmentByTag("HARMONICA");
        LearnFragment learnFragment = (LearnFragment)fm.findFragmentByTag("LEARN");
        FAQFragment faqsFragment = (FAQFragment)fm.findFragmentByTag("FAQS");
        InformationFragment informationFragment = (InformationFragment)fm.findFragmentByTag("INFORMATION");

        if(guitarFragment != null || pianoFragment != null || bassFragment != null || ukuleleFragment != null || harmonicaFragment != null) {
            FragmentTransaction fragmentTransaction= fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new LearnFragment()).commit();
        }
        else if(learnFragment != null || faqsFragment != null) {
            FragmentTransaction fragmentTransaction= fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new InformationFragment()).commit();
        }
        else {
            Home.setBackgroundResource(R.drawable.drawer_item_background);
            Help.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            Settings.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            if(TabFragment.viewPager.isShown()) {
                    if (TabFragment.viewPager.getCurrentItem() == 0) {
                        if (!drawerLayout.isDrawerOpen(navigationView)) {
                            drawerLayout.openDrawer(navigationView);
                        }
                        if (drawerLayout.isDrawerOpen(navigationView)) {
                            drawerLayout.closeDrawer(navigationView);
                        }
                    } else {
                        TabFragment.viewPager.setCurrentItem(0);
                    }
            }
            else {
                FragmentTransaction fragmentTransaction1 = FM.beginTransaction();
                fragmentTransaction1.replace(R.id.containerView, new TabFragment()).commit();
            }
        }
    }

    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setTitle (String actionBarTitle) {


        textSwitcher.setText(actionBarTitle);

    }

    TextSwitcher textSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);

        navigationView = (NavigationView) findViewById(R.id.shitstuff);

        textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView switcherTextView = new TextView(getApplicationContext());
                switcherTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/LeagueSpartan-Bold.otf"));
                switcherTextView.setTextSize(24);
                switcherTextView.setTextColor(Color.rgb(79,82,97));
                switcherTextView.setText("Welcome to Tablafy");
                return switcherTextView;
            }
        });

        Animation animationOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        Animation animationIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        textSwitcher.setOutAnimation(animationOut);
        textSwitcher.setInAnimation(animationIn);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView= (NavigationView) findViewById(R.id.shitstuff);
        Help = (LinearLayout) findViewById(R.id.llInformation);
        Home = (LinearLayout) findViewById(R.id.llPrimary);
        Settings = (LinearLayout) findViewById(R.id.llSettings);

        drawerLayout.openDrawer(navigationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(navigationView);            }
        }, 1700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setTitle("");            }
        }, 4000);

        FM= getSupportFragmentManager();
        FT= FM.beginTransaction();
        FT.replace(R.id.containerView, new TabFragment()).commit();

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Home.setBackgroundResource(R.drawable.drawer_item_background);
                Help.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Settings.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                fragmentTransaction1.replace(R.id.containerView,new TabFragment(),("PRIMARY")).commit();
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                drawerLayout.closeDrawers();
                Help.setBackgroundResource(R.drawable.drawer_item_background);
                Settings.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                fragmentTransaction1.replace(R.id.containerView,new InformationFragment(),("INFORMATION")).commit();
            }
        });
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                drawerLayout.closeDrawers();
                Settings.setBackgroundResource(R.drawable.drawer_item_background);
                Home.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Help.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                fragmentTransaction1.replace(R.id.containerView,new SettingsFragment(),("SETTINGS")).commit();
            }
        });


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
}
