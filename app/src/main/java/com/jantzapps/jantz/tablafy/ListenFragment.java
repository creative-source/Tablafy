package com.jantzapps.jantz.tablafy;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.kbeanie.multipicker.api.AudioPicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.AudioPickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenAudio;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.SpectralPeakProcessor;
import be.tarsos.dsp.io.PipedAudioStream;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListenFragment extends Fragment {

    private AudioPicker audioPicker;

    public static class Loading {
        private final boolean on;

        public Loading(boolean on) {
            this.on = on;
        }

        public boolean getOn() {
            return on;
        }
    }

    private static final String TAG = "<< DRIVE >>";
    static ImageButton listen;
    TextView cancel;
    static TextSwitcher textSwitcher1, textSwitcher2;
    AlarmManager alarm_manager;
    PendingIntent mAlarmIntent;
    final int sampleRate = 44100;
    final int fftsize = 32768 / 2;
    final int overlap = fftsize / 2;//50% overlap

    private static final String LOG_TAG = "AudioRecordTest";

    private static String mFileName = null;

    private MediaRecorder mRecorder = null;

    private MediaPlayer   mPlayer = null;                                  //WHY ?

    public ListenFragment() {
        // Required empty public constructor
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        if(ListenFragment.this.isAdded())
            try {
                mRecorder.stop();
            }
            catch (Exception e) {}
        try {
            mRecorder.release();
        } catch (Exception e) {}
        mRecorder = null;
    }

    /*
    private void extractPeakListList() {
        PipedAudioStream f = new PipedAudioStream(mFileName);
        TarsosDSPAudioInputStream stream = f.getMonoStream(sampleRate, sampleRate);
        final SpectralPeakProcessor spectralPeakFollower = new SpectralPeakProcessor(fftsize, overlap, sampleRate);
        AudioDispatcher dispatcher = new AudioDispatcher(stream, fftsize, overlap);
        dispatcher.addAudioProcessor(spectralPeakFollower);
        dispatcher.addAudioProcessor(new AudioProcessor() {

            @Override
            public void processingFinished() {
            }

            @Override
            public boolean process(AudioEvent audioEvent) {
                float [] magnitudesList = spectralPeakFollower.getMagnitudes();
                float [] frequencyEstimatesList = spectralPeakFollower.getFrequencyEstimates();
                Log.e(TAG,String.valueOf(frequencyEstimatesList));
                return true;
            }
        });
        dispatcher.run();
    }
    */

    /*
    private void extractPeakListList() {
        new AndroidFFMPEGLocator(this);
        new Thread(new Runnable() {
        @Override
        public void run() {
            File externalStorage = Environment.getExternalStorageDirectory();
            File mp3 = new File(externalStorage.getAbsolutePath() , "/audio.mp3");
            AudioDispatcher adp;
            adp = AudioDispatcherFactory.fromPipe(mp3.getAbsolutePath(),44100,5000,2500);
            adp.addAudioProcessor(new AndroidAudioPlayer(adp.getFormat(),5000, AudioManager.STREAM_MUSIC));
            adp.run();
        }
        }).start();
    }
    */

    private void extractPeakListList() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                new AndroidFFMPEGLocator(getContext());
                final SpectralPeakProcessor spectralPeakFollower = new SpectralPeakProcessor(fftsize, overlap, sampleRate);
                AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(mFileName,44100,5000,2500);
                dispatcher.addAudioProcessor(spectralPeakFollower);
                dispatcher.addAudioProcessor(new AudioProcessor() {

                    @Override
                    public void processingFinished() {
                    }

                    @Override
                    public boolean process(AudioEvent audioEvent) {
                        float [] magnitudesList = spectralPeakFollower.getMagnitudes();
                        float [] frequencyEstimatesList = spectralPeakFollower.getFrequencyEstimates();
                        Log.e(TAG+"ATTENTION",String.valueOf(frequencyEstimatesList));
                        return true;
                    }
                });
                dispatcher.run();
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =inflater.inflate(R.layout.fragment_listen,container,false);


        final SharedPreferences preference = getActivity().getSharedPreferences("settings.conf", Context.MODE_PRIVATE);

        final int fifteen = 900000;

        final String vibratePosition = preference.getString("vibratePref","on");
        final String soundPosition = preference.getString("soundPref","on");
        final String processMethod = preference.getString("processPref","Oct");

        cancel = (TextView) view.findViewById(R.id.textCancel);

        final LinearLayout upload = (LinearLayout) view.findViewById(R.id.llUpload);

        textSwitcher1 = (TextSwitcher) view.findViewById(R.id.textSwitcher1);
        textSwitcher2 = (TextSwitcher) view.findViewById(R.id.textSwitcher2);

        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.dland_hint);
        final MediaPlayer mp2 = MediaPlayer.create(getContext(), R.raw.trickle_clicker);

        mFileName = getContext().getExternalCacheDir().getAbsolutePath();
        mFileName += "/temp_record.3gp";


        textSwitcher1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView switcherTextView = new TextView(getContext().getApplicationContext());
                switcherTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/LeagueSpartan-Bold.otf"));
                switcherTextView.setTextSize(24);
                switcherTextView.setTextColor(Color.rgb(79,82,97));
                switcherTextView.setText("Place device near music and press button below.");
                switcherTextView.setPadding(34,34,34,34);
                switcherTextView.setGravity(Gravity.CENTER);
                return switcherTextView;
            }
        });

        textSwitcher2.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView switcherTextView = new TextView(getContext().getApplicationContext());
                switcherTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/LeagueSpartan-Bold.otf"));
                switcherTextView.setTextSize(24);
                switcherTextView.setTextColor(Color.rgb(79,82,97));
                switcherTextView.setText("");
                switcherTextView.setPadding(34,34,34,34);
                switcherTextView.setGravity(Gravity.CENTER);
                return switcherTextView;
            }
        });

        Animation animationOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        Animation animationIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);

        textSwitcher1.setOutAnimation(animationOut);
        textSwitcher1.setInAnimation(animationIn);

        textSwitcher2.setOutAnimation(animationOut);
        textSwitcher2.setInAnimation(animationIn);

        listen = (ImageButton) view.findViewById(R.id.btnListen);

        final Vibrator v = (Vibrator) this.getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibratePosition.equals("on")) {v.vibrate(100);} // 100 miliseconds = 5 seconds

                if(!((AnimationDrawable) listen.getDrawable()).isRunning()) {
                    if(soundPosition.equals("on")){mp.start();}
                    listen.setBackgroundColor(Color.WHITE);
                    ((AnimationDrawable) listen.getDrawable()).start();
                    textSwitcher1.setText("");
                    textSwitcher2.setText("Press button again to get your tabs.");
                    textSwitcher2.setBackgroundResource(R.drawable.drawer_item_background);
                    textSwitcher1.setBackgroundColor(Color.argb(1,224,224,224));

                    alarm_manager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getContext(), AlarmReceiver.class);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    mAlarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                    Long time = System.currentTimeMillis();
                    alarm_manager.set(AlarmManager.RTC_WAKEUP, time + fifteen, mAlarmIntent);

                    cancel.setVisibility(View.VISIBLE);
                    upload.setVisibility(View.GONE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AnalyzeMusic.analyzingMusic(processMethod,true);
                            onRecord(true);
                        }
                    }, 1000);
                } else {
                    if(soundPosition.equals("on")) {mp2.start();}
                    ((AnimationDrawable) listen.getDrawable()).selectDrawable(0);
                    listen.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    ((AnimationDrawable) listen.getDrawable()).stop();
                    textSwitcher2.setText("");
                    textSwitcher1.setText("Place device near music and press button below.");
                    textSwitcher1.setBackgroundResource(R.drawable.drawer_item_background);
                    textSwitcher2.setBackgroundColor(Color.argb(1,224,224,224));
                    final String instrument = preference.getString("instrumentPref","guitar");

                    EventBus.getDefault().post(new Loading(true));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AnalyzeMusic.analyzingMusic(processMethod,false);
                            onRecord(false);
                            extractPeakListList();                                 // FIXME: 9/10/2017
                        }
                    },1500);
                    TabFragment.viewPager.setCurrentItem(1);
                    if(instrument.equals("guitar")) {
                        MyStandFragment.tabViewFlipper.setDisplayedChild(0);
                    }
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
                    alarm_manager.cancel(mAlarmIntent);

                    cancel.setVisibility(View.GONE);
                    upload.setVisibility(View.VISIBLE);
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AnimationDrawable) listen.getDrawable()).selectDrawable(0);
                listen.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ((AnimationDrawable) listen.getDrawable()).stop();
                textSwitcher2.setText("");
                textSwitcher1.setText("Place device near music and press button below.");
                textSwitcher1.setBackgroundResource(R.drawable.drawer_item_background);
                textSwitcher2.setBackgroundColor(Color.argb(1,224,224,224));
                cancel.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upload.setVisibility(View.VISIBLE);
                    }
                }, 500);
                alarm_manager.cancel(mAlarmIntent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onRecord(false);
                    }
                },1000);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPicker.pickAudio();                                // FIXME: 9/25/2017
            }
        });

        audioPicker = new AudioPicker(getActivity());
// audioPicker.allowMultiple();
// audioPicker.
        audioPicker.setAudioPickerCallback(new AudioPickerCallback() {
            @Override
            public void onAudiosChosen(List<ChosenAudio> files) {
                // Display Files
            }

            @Override
            public void onError(String message) {
                // Handle errors
            }
        });


        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final RelativeLayout mainLayout = (RelativeLayout) getView().findViewById(R.id.rlMain);
        if(mainLayout.getVisibility() == View.VISIBLE)
            mainLayout.setVisibility(View.GONE);
        else
            mainLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_AUDIO && resultCode == RESULT_OK) {
            if(audioPicker == null) {
                audioPicker = new AudioPicker(getActivity());
                audioPicker.setAudioPickerCallback((AudioPickerCallback) this);  // FIXME: 9/26/2017
            }
            audioPicker.submit(data);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            try {
                mRecorder.stop();
            }
            catch (Exception e) {}
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

}
