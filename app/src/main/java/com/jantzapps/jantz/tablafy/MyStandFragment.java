package com.jantzapps.jantz.tablafy;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;

import static android.R.string.ok;
import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;
import static com.jantzapps.jantz.tablafy.TabFragment.tabLayout;
import static com.jantzapps.jantz.tablafy.TabFragment.viewPager;

/**
 * A simple {@link Fragment} subclass.
 */

public class MyStandFragment extends Fragment{
    DbHelper dbHelper;
    EditText tabName;
    private int dimensionInPixel2 = 45;
    final int sec = R.drawable.sec_mark;
    GraphView graph,graph2,graph3,graph4,graph5;
    boolean land = false;

    public static ViewFlipper tabViewFlipper = null;
    static AlertDialog.Builder builder;
    int [] timeMarkBy30 = {R.drawable.t_0_30mark,R.drawable.t_1_00mark,R.drawable.t_1_30mark,R.drawable.t_2_00mark,
            R.drawable.t_2_30mark,R.drawable.t_3_00mark,R.drawable.t_3_30mark,R.drawable.t_4_00mark,
            R.drawable.t_4_30mark,R.drawable.t_5_00mark,R.drawable.t_5_30mark,R.drawable.t_6_00mark,
            R.drawable.t_6_30mark,R.drawable.t_7_00mark,R.drawable.t_7_30mark,R.drawable.t_8_00mark,
            R.drawable.t_8_30mark,R.drawable.t_9_00mark,R.drawable.t_9_30mark,R.drawable.t_10_00mark,
            R.drawable.t_10_30mark,R.drawable.t_11_00mark,R.drawable.t_11_30mark,R.drawable.t_12_00mark,
            R.drawable.t_12_30mark,R.drawable.t_13_00mark,R.drawable.t_13_30mark,R.drawable.t_14_00mark,
            R.drawable.t_14_30mark,R.drawable.t_15_00mark};

    public MyStandFragment() {
        // Required empty public constructor
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return  inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                        int reqWidth, int reqHeight) {

        //First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    public boolean configSwitch() {
        if(land)
            land = false;
        else if(!land)
            land = true;
        return land;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        graph = (GraphView) getView().findViewById(R.id.graph);
        graph2 = (GraphView) getView().findViewById(R.id.graph2);
        graph3 = (GraphView) getView().findViewById(R.id.graph3);
        graph4 = (GraphView) getView().findViewById(R.id.graph4);
        graph5 = (GraphView) getView().findViewById(R.id.graph5);
        configSwitch();
        if(land) {
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(200);

            graph2.getViewport().setXAxisBoundsManual(true);
            graph2.getViewport().setMinX(0);
            graph2.getViewport().setMaxX(200);

            graph3.getViewport().setXAxisBoundsManual(true);
            graph3.getViewport().setMinX(0);
            graph3.getViewport().setMaxX(200);

            graph4.getViewport().setXAxisBoundsManual(true);
            graph4.getViewport().setMinX(0);
            graph4.getViewport().setMaxX(200);

            graph5.getViewport().setXAxisBoundsManual(true);
            graph5.getViewport().setMinX(0);
            graph5.getViewport().setMaxX(200);
        }
        else {
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(100);

            graph2.getViewport().setXAxisBoundsManual(true);
            graph2.getViewport().setMinX(0);
            graph2.getViewport().setMaxX(100);

            graph3.getViewport().setXAxisBoundsManual(true);
            graph3.getViewport().setMinX(0);
            graph3.getViewport().setMaxX(100);

            graph4.getViewport().setXAxisBoundsManual(true);
            graph4.getViewport().setMinX(0);
            graph4.getViewport().setMaxX(100);

            graph5.getViewport().setXAxisBoundsManual(true);
            graph5.getViewport().setMinX(0);
            graph5.getViewport().setMaxX(100);
        }
        final RelativeLayout mainLayout = (RelativeLayout) getView().findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainLayout.getLayoutParams();

        int DpPx1 = convertDpToPixels(18,getContext());
        int DpPx2 = convertDpToPixels(38,getContext());

        if(tabLayout.getVisibility() == View.GONE) {
            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
            mainLayout.setLayoutParams(params);
        }
        else {
            params.setMargins(DpPx1, DpPx2, DpPx1, DpPx2); //substitute parameters for left, top, right, bottom
            mainLayout.setLayoutParams(params);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toStand(MyTabsFragment.ToStand title){
        TextView tabTitle = (TextView) getView().findViewById(R.id.tab_name);
        tabTitle.setText(title.getTitle());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadingSwitch(ListenFragment.Loading on){
        final RelativeLayout loading = (RelativeLayout) getView().findViewById(R.id.rlLoading);
        TextView tabTitle = (TextView) getView().findViewById(R.id.tab_name);
        final ImageView imageView = (ImageView) getView().findViewById(R.id.ivT);

        final Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

                imageView.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.startAnimation(fadeIn);
                    }
                }, 500);


            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        tabTitle.setText("");
        if(on.getOn()) {
            loading.setVisibility(View.VISIBLE);
            imageView.startAnimation(fadeIn);
        }
        if(!on.getOn()) {
            imageView.clearAnimation();
            loading.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendTab(CreateTabs.FormTab event){
        Long duration = event.getDuration();
        ArrayList<Long> noteTimes = event.getNoteTimes();
        int [] rowNumbers = event.getRowNumbers();
        int [] playNumbers = event.getPlayNumbers();
        String instrument = event.getInstrument();

        if(instrument.equals("guitar")){
            postGuitarTab(duration,noteTimes,rowNumbers,playNumbers);
        }
        if(instrument.equals("piano")){
            postPianoTab(duration,noteTimes,rowNumbers,playNumbers);
        }
        if(instrument.equals("bass")){
            postBassTab(duration,noteTimes,rowNumbers,playNumbers);
        }
        if(instrument.equals("ukulele")){
            postUkuleleTab(duration,noteTimes,rowNumbers,playNumbers);
        }
        if(instrument.equals("harmonica")){
            postHarmonicaTab(duration,noteTimes,rowNumbers,playNumbers);
        }
    }
    public void postGuitarTab(Long duration, final ArrayList<Long> noteTimes, final int [] rowNumbers, final int [] fretNumbers){

            int xSet = 20;
            int array30Count = 0;
            int secCount = 0;

        final FrameLayout innerlayout = (FrameLayout) getView().findViewById(R.id.frame);

        innerlayout.setVisibility(View.VISIBLE);
        //innerlayout.removeAllViews();

        final TextView guitarType = (TextView) getView().findViewById(R.id.tabType);
        final TextView guitarSwipe = (TextView) getView().findViewById(R.id.guitarSwipeHint);
        final Button guitarSave = (Button) getView().findViewById(R.id.btnSave);
        final LinearLayout guitarEmpty = (LinearLayout) getView().findViewById(R.id.llGuitarEmpty);

        final int dimensionInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel2, getResources().getDisplayMetrics());

        guitarEmpty.setVisibility(View.GONE);
        guitarSave.setVisibility(View.VISIBLE);
        guitarType.setVisibility(View.VISIBLE);
        guitarSwipe.setVisibility(View.VISIBLE);

        graph = (GraphView) getView().findViewById(R.id.graph);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

        staticLabelsFormatter.setVerticalLabels(new String[] {"","","","E  ","", "A  ","", "D  ","","G  ","","B  ","","E  ","","",""});

        graph.getGridLabelRenderer().setTextSize(100);
        graph.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);

        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setLabelsSpace(150);
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);

        graph.setBackgroundColor(Color.WHITE);

        graph.getGridLabelRenderer().setLabelFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LeagueSpartan-Bold.otf"));
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(-0.5);
        graph.getViewport().setMaxY(7.5);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(100);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 6),
                new DataPoint(220, 6),
        });
        graph.addSeries(series);
        series.setColor(Color.BLACK);
        series.setThickness(30);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(220, 5),
        });
        graph.addSeries(series2);
        series2.setColor(Color.BLACK);
        series2.setThickness(30);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(220, 4),
        });
        graph.addSeries(series3);
        series3.setColor(Color.BLACK);
        series3.setThickness(30);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(220, 3),
        });
        graph.addSeries(series4);
        series4.setColor(Color.BLACK);
        series4.setThickness(30);

        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 2),
                new DataPoint(220, 2),
        });
        graph.addSeries(series5);
        series5.setColor(Color.BLACK);
        series5.setThickness(30);

        LineGraphSeries<DataPoint> series6 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(220, 1),
        });
        graph.addSeries(series6);
        series6.setColor(Color.BLACK);
        series6.setThickness(30);

        final PointsGraphSeries<DataPoint> series8 = new PointsGraphSeries<>();

        for (int i = 0; i < fretNumbers.length; i++) {

            final int finalI = i;
            class Decode extends AsyncTask<Void,Void,Bitmap> {
                @Override
                protected Bitmap doInBackground(Void... params) {

                    if(isAdded()) {
                        return decodeSampledBitmapFromResource(getResources(), fretNumbers[finalI],
                                100, 100);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap tabImage) {
                    if(tabImage == null) {
                        return;
                    }
                                                                  //// FIXME: 10/11/2017
                    series8.appendDataCustom(new DataPoint((noteTimes.get(finalI)),(rowNumbers [finalI])),false,1000,false,new PointsGraphSeries.CustomShape() {
                        @Override
                        public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {

                            Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                            canvas.drawBitmap(finalbitmap,x,y-73,null);
                        }
                    });
                }
            }

            new Decode().execute();
        }

        graph.addSeries(series8);

        final PointsGraphSeries<DataPoint> series7 = new PointsGraphSeries<>();
        final PointsGraphSeries<DataPoint> series9 = new PointsGraphSeries<>();

            for(; 0 <= duration; duration -= 14) {
                final int finalXSet = xSet;

                if(secCount == 29) {

                    if(array30Count <= 14) {

                        final int tempArray30Count = array30Count;

                        class Decode extends AsyncTask<Void,Void,Bitmap> {
                            @Override
                            protected Bitmap doInBackground(Void... params) {

                                if(isAdded()) {
                                    return decodeSampledBitmapFromResource(getResources(), timeMarkBy30[tempArray30Count],
                                            dimensionInDp2, dimensionInDp2);
                                } else {
                                    return null;
                                }
                            }

                            @Override
                            protected void onPostExecute(final Bitmap tabImage) {
                                if(tabImage == null) {
                                    return;
                                }
                                series9.appendDataCustom(new DataPoint(finalXSet,7.5),false,1000,false,new PointsGraphSeries.CustomShape() {
                                @Override
                                public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                    Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                                canvas.drawBitmap(finalbitmap, x - 68, y, null);
                                }
                                });
                            }
                        }

                        new Decode().execute();

                        array30Count++;
                        Log.e("<DEBUG>","The value of tempArray30Count is "+ tempArray30Count); // Debug
                        xSet += 8;
                        secCount = 0;
                    }
                }
                else {

                    class Decode extends AsyncTask<Void,Void,Bitmap> {
                        @Override
                        protected Bitmap doInBackground(Void... params) {

                            if(isAdded()) {
                                return decodeSampledBitmapFromResource(getResources(), sec,
                                7, 20);
                            }
                            else {
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(final Bitmap tabImage) {
                            if(tabImage == null) {
                                return;
                            }
                            series7.appendDataCustom(new DataPoint(finalXSet,7.5),false,1000,false,new PointsGraphSeries.CustomShape() {
                            @Override
                            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,14,30,false);

                            canvas.drawBitmap(finalbitmap, x, y, null);
                            }
                            });
                        }
                    }

                    new Decode().execute();

                    secCount++;
                    Log.e("<DEBUG>","The value of secCount is "+ secCount);

                    xSet += 8;
                }
            }

        graph.addSeries(series7);
        graph.addSeries(series9);


    }


    public void postPianoTab(float duration, final ArrayList<Long> noteTimes, final int [] rowNumbers, final int [] fretNumbers){

                int xSet = 20;
                int array30Count = 0;
                int secCount = 0;

                final FrameLayout innerlayout = (FrameLayout) getView().findViewById(R.id.frame2);

                innerlayout.setVisibility(View.VISIBLE);
                //innerlayout.removeAllViews();

                final TextView pianoType = (TextView) getView().findViewById(R.id.tabType2);
                final TextView pianoSwipe = (TextView) getView().findViewById(R.id.pianoSwipeHint);
                final Button pianoSave = (Button) getView().findViewById(R.id.btnSave2);
                final LinearLayout pianoEmpty = (LinearLayout) getView().findViewById(R.id.llPianoEmpty);

                pianoEmpty.setVisibility(View.GONE);
                pianoSave.setVisibility(View.VISIBLE);
                pianoType.setVisibility(View.VISIBLE);
                pianoSwipe.setVisibility(View.VISIBLE);

                final int dimensionInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel2, getResources().getDisplayMetrics());

        graph2 = (GraphView) getView().findViewById(R.id.graph2);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph2);

        staticLabelsFormatter.setVerticalLabels(new String[] {"","", "5  ", "4  ","3  ","2  ","",""});

        graph2.getGridLabelRenderer().setTextSize(100);
        graph2.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);

        graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph2.getGridLabelRenderer().setLabelsSpace(150);
        graph2.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);

        graph2.setBackgroundColor(Color.WHITE);

        graph2.getGridLabelRenderer().setLabelFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LeagueSpartan-Bold.otf"));
        graph2.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph2.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph2.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph2.getGridLabelRenderer().setHighlightZeroLines(false);
        graph2.getViewport().setScrollable(true);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(7);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(100);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(220, 5),
        });
        graph2.addSeries(series2);
        series2.setColor(Color.BLACK);
        series2.setThickness(30);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(220, 4),
        });
        graph2.addSeries(series3);
        series3.setColor(Color.BLACK);
        series3.setThickness(30);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(220, 3),
        });
        graph2.addSeries(series4);
        series4.setColor(Color.BLACK);
        series4.setThickness(30);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 2),
                new DataPoint(220, 2),
        });
        graph2.addSeries(series);
        series.setColor(Color.BLACK);
        series.setThickness(30);

        final PointsGraphSeries<DataPoint> series7 = new PointsGraphSeries<>();
        final PointsGraphSeries<DataPoint> series9 = new PointsGraphSeries<>();

        for(; 0 <= duration; duration -= 14) {
            final int finalXSet = xSet;

            if(secCount == 29) {

                if(array30Count <= 14) {

                    final int tempArray30Count = array30Count;

                    class Decode extends AsyncTask<Void,Void,Bitmap> {
                        @Override
                        protected Bitmap doInBackground(Void... params) {

                            if(isAdded()) {
                                return decodeSampledBitmapFromResource(getResources(), timeMarkBy30[tempArray30Count],
                                        dimensionInDp2, dimensionInDp2);
                            } else {
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(final Bitmap tabImage) {
                            if(tabImage == null) {
                                return;
                            }
                            series9.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                                @Override
                                public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                    Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                                    canvas.drawBitmap(finalbitmap, x - 68, y, null);
                                }
                            });
                        }
                    }

                    new Decode().execute();

                    array30Count++;
                    Log.e("<DEBUG>","The value of tempArray30Count is "+ tempArray30Count);
                    xSet += 8;
                    secCount = 0;
                }
            }
            else {

                class Decode extends AsyncTask<Void,Void,Bitmap> {
                    @Override
                    protected Bitmap doInBackground(Void... params) {

                        if(isAdded()) {
                            return decodeSampledBitmapFromResource(getResources(), sec,
                                    7, 20);
                        }
                        else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(final Bitmap tabImage) {
                        if(tabImage == null) {
                            return;
                        }
                        series7.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                            @Override
                            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,14,30,false);

                                canvas.drawBitmap(finalbitmap, x, y, null);
                            }
                        });
                    }
                }

                new Decode().execute();

                secCount++;
                Log.e("<DEBUG>","The value of secCount is "+ secCount);

                xSet += 8;
            }
        }

        graph2.addSeries(series7);
        graph2.addSeries(series9);


        final PointsGraphSeries<DataPoint> series8 = new PointsGraphSeries<>();

        for (int i = 0; i < fretNumbers.length; i++) {

            //final ImageView imageView2 = new ImageView(getContext());

            //imageView2.setId(i);

            final int finalI = i;
            class Decode extends AsyncTask<Void,Void,Bitmap> {
                @Override
                protected Bitmap doInBackground(Void... params) {

                    if(isAdded()) {
                        return decodeSampledBitmapFromResource(getResources(), fretNumbers[finalI],
                                100, 100);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap tabImage) {
                    if(tabImage == null) {
                        return;
                    }
                    //// FIXME: 10/11/2017
                    series8.appendDataCustom(new DataPoint((noteTimes.get(finalI)),(rowNumbers [finalI])),false,1000,false,new PointsGraphSeries.CustomShape() {
                        @Override
                        public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {

                            Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                            canvas.drawBitmap(finalbitmap,x,y-73,null);
                        }
                    });
                }
            }

            new Decode().execute();
        }

        graph2.addSeries(series8);

    }

    public void postBassTab(float duration, final ArrayList<Long> noteTimes, final int[] rowNumbers, final int[] fretNumbers){

                int xSet = 20;
                int array30Count = 0;
                int secCount = 0;

                final FrameLayout innerlayout = (FrameLayout) getView().findViewById(R.id.frame3);

                innerlayout.setVisibility(View.VISIBLE);
                //innerlayout.removeAllViews();

                final TextView bassType = (TextView) getView().findViewById(R.id.tabType3);
                final TextView bassSwipe = (TextView) getView().findViewById(R.id.bassSwipeHint);
                final Button bassSave = (Button) getView().findViewById(R.id.btnSave3);
                final LinearLayout bassEmpty = (LinearLayout) getView().findViewById(R.id.llBassEmpty);

                bassEmpty.setVisibility(View.GONE);
                bassSave.setVisibility(View.VISIBLE);
                bassType.setVisibility(View.VISIBLE);
                bassSwipe.setVisibility(View.VISIBLE);

                final int dimensionInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel2, getResources().getDisplayMetrics());

        graph3 = (GraphView) getView().findViewById(R.id.graph3);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph3);

        staticLabelsFormatter.setVerticalLabels(new String[] {"","", "E  ", "A  ","D  ","G  ","",""});

        graph3.getGridLabelRenderer().setTextSize(100);
        graph3.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);

        graph3.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph3.getGridLabelRenderer().setLabelsSpace(150);
        graph3.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);

        graph3.setBackgroundColor(Color.WHITE);

        graph3.getGridLabelRenderer().setLabelFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LeagueSpartan-Bold.otf"));
        graph3.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph3.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph3.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph3.getGridLabelRenderer().setHighlightZeroLines(false);
        graph3.getViewport().setScrollable(true);
        graph3.getViewport().setYAxisBoundsManual(true);
        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setMinY(0);
        graph3.getViewport().setMaxY(7);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(100);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(220, 5),
        });
        graph3.addSeries(series2);
        series2.setColor(Color.BLACK);
        series2.setThickness(30);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(220, 4),
        });
        graph3.addSeries(series3);
        series3.setColor(Color.BLACK);
        series3.setThickness(30);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(220, 3),
        });
        graph3.addSeries(series4);
        series4.setColor(Color.BLACK);
        series4.setThickness(30);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 2),
                new DataPoint(220, 2),
        });
        graph3.addSeries(series);
        series.setColor(Color.BLACK);
        series.setThickness(30);

        final PointsGraphSeries<DataPoint> series7 = new PointsGraphSeries<>();
        final PointsGraphSeries<DataPoint> series9 = new PointsGraphSeries<>();

        for(; 0 <= duration; duration -= 14) {
            final int finalXSet = xSet;

            if(secCount == 29) {

                if(array30Count <= 14) {

                    final int tempArray30Count = array30Count;

                    class Decode extends AsyncTask<Void,Void,Bitmap> {
                        @Override
                        protected Bitmap doInBackground(Void... params) {

                            if(isAdded()) {
                                return decodeSampledBitmapFromResource(getResources(), timeMarkBy30[tempArray30Count],
                                        dimensionInDp2, dimensionInDp2);
                            } else {
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(final Bitmap tabImage) {
                            if(tabImage == null) {
                                return;
                            }
                            series9.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                                @Override
                                public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                    Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                                    canvas.drawBitmap(finalbitmap, x - 68, y, null);
                                }
                            });
                        }
                    }

                    new Decode().execute();

                    array30Count++;
                    Log.e("<DEBUG>","The value of tempArray30Count is "+ tempArray30Count);
                    xSet += 8;
                    secCount = 0;
                }
            }
            else {

                class Decode extends AsyncTask<Void,Void,Bitmap> {
                    @Override
                    protected Bitmap doInBackground(Void... params) {

                        if(isAdded()) {
                            return decodeSampledBitmapFromResource(getResources(), sec,
                                    7, 20);
                        }
                        else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(final Bitmap tabImage) {
                        if(tabImage == null) {
                            return;
                        }
                        series7.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                            @Override
                            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,14,30,false);

                                canvas.drawBitmap(finalbitmap, x, y, null);
                            }
                        });
                    }
                }

                new Decode().execute();

                secCount++;
                Log.e("<DEBUG>","The value of secCount is "+ secCount);

                xSet += 8;
            }
        }

        graph3.addSeries(series7);
        graph3.addSeries(series9);


        final PointsGraphSeries<DataPoint> series8 = new PointsGraphSeries<>();

        for (int i = 0; i < fretNumbers.length; i++) {

            //final ImageView imageView2 = new ImageView(getContext());

            //imageView2.setId(i);

            final int finalI = i;
            class Decode extends AsyncTask<Void,Void,Bitmap> {
                @Override
                protected Bitmap doInBackground(Void... params) {

                    if(isAdded()) {
                        return decodeSampledBitmapFromResource(getResources(), fretNumbers[finalI],
                                100, 100);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap tabImage) {
                    if(tabImage == null) {
                        return;
                    }
                    //// FIXME: 10/11/2017
                    series8.appendDataCustom(new DataPoint((noteTimes.get(finalI)),(rowNumbers [finalI])),false,1000,false,new PointsGraphSeries.CustomShape() {
                        @Override
                        public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {

                            Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                            canvas.drawBitmap(finalbitmap,x,y-73,null);
                        }
                    });
                }
            }

            new Decode().execute();
        }

        graph3.addSeries(series8);

    }

    public void postUkuleleTab(float duration, final ArrayList<Long> noteTimes, final int[] rowNumbers, final int[] fretNumbers){

                int xSet = 20;
                int array30Count = 0;
                int secCount = 0;

                final FrameLayout innerlayout = (FrameLayout) getView().findViewById(R.id.frame4);

                innerlayout.setVisibility(View.VISIBLE);
                //innerlayout.removeAllViews();

                final TextView ukuleleType = (TextView) getView().findViewById(R.id.tabType4);
                final TextView ukuleleSwipe = (TextView) getView().findViewById(R.id.ukuleleSwipeHint);
                final Button ukuleleSave = (Button) getView().findViewById(R.id.btnSave4);
                final LinearLayout ukuleleEmpty = (LinearLayout) getView().findViewById(R.id.llUkuleleEmpty);

                ukuleleEmpty.setVisibility(View.GONE);
                ukuleleSave.setVisibility(View.VISIBLE);
                ukuleleType.setVisibility(View.VISIBLE);
                ukuleleSwipe.setVisibility(View.VISIBLE);

                final int dimensionInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel2, getResources().getDisplayMetrics());

        graph4 = (GraphView) getView().findViewById(R.id.graph4);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph4);

        staticLabelsFormatter.setVerticalLabels(new String[] {"","", "G  ", "C  ","E  ","A  ","",""});

        graph4.getGridLabelRenderer().setTextSize(100);
        graph4.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);

        graph4.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph4.getGridLabelRenderer().setLabelsSpace(150);
        graph4.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);

        graph4.setBackgroundColor(Color.WHITE);

        graph4.getGridLabelRenderer().setLabelFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LeagueSpartan-Bold.otf"));
        graph4.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph4.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph4.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph4.getGridLabelRenderer().setHighlightZeroLines(false);
        graph4.getViewport().setScrollable(true);
        graph4.getViewport().setYAxisBoundsManual(true);
        graph4.getViewport().setXAxisBoundsManual(true);
        graph4.getViewport().setMinY(0);
        graph4.getViewport().setMaxY(7);
        graph4.getViewport().setMinX(0);
        graph4.getViewport().setMaxX(100);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(220, 5),
        });
        graph4.addSeries(series2);
        series2.setColor(Color.BLACK);
        series2.setThickness(30);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(220, 4),
        });
        graph4.addSeries(series3);
        series3.setColor(Color.BLACK);
        series3.setThickness(30);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(220, 3),
        });
        graph4.addSeries(series4);
        series4.setColor(Color.BLACK);
        series4.setThickness(30);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 2),
                new DataPoint(220, 2),
        });
        graph4.addSeries(series);
        series.setColor(Color.BLACK);
        series.setThickness(30);

        final PointsGraphSeries<DataPoint> series7 = new PointsGraphSeries<>();
        final PointsGraphSeries<DataPoint> series9 = new PointsGraphSeries<>();

        for(; 0 <= duration; duration -= 14) {
            final int finalXSet = xSet;

            if(secCount == 29) {

                if(array30Count <= 14) {

                    final int tempArray30Count = array30Count;

                    class Decode extends AsyncTask<Void,Void,Bitmap> {
                        @Override
                        protected Bitmap doInBackground(Void... params) {

                            if(isAdded()) {
                                return decodeSampledBitmapFromResource(getResources(), timeMarkBy30[tempArray30Count],
                                        dimensionInDp2, dimensionInDp2);
                            } else {
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(final Bitmap tabImage) {
                            if(tabImage == null) {
                                return;
                            }
                            series9.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                                @Override
                                public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                    Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                                    canvas.drawBitmap(finalbitmap, x - 68, y, null);
                                }
                            });
                        }
                    }

                    new Decode().execute();

                    array30Count++;
                    Log.e("<DEBUG>","The value of tempArray30Count is "+ tempArray30Count);
                    xSet += 8;
                    secCount = 0;
                }
            }
            else {

                class Decode extends AsyncTask<Void,Void,Bitmap> {
                    @Override
                    protected Bitmap doInBackground(Void... params) {

                        if(isAdded()) {
                            return decodeSampledBitmapFromResource(getResources(), sec,
                                    7, 20);
                        }
                        else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(final Bitmap tabImage) {
                        if(tabImage == null) {
                            return;
                        }
                        series7.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                            @Override
                            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,14,30,false);

                                canvas.drawBitmap(finalbitmap, x, y, null);
                            }
                        });
                    }
                }

                new Decode().execute();

                secCount++;
                Log.e("<DEBUG>","The value of secCount is "+ secCount);

                xSet += 8;
            }
        }

        graph4.addSeries(series7);
        graph4.addSeries(series9);


        final PointsGraphSeries<DataPoint> series8 = new PointsGraphSeries<>();

        for (int i = 0; i < fretNumbers.length; i++) {

            //final ImageView imageView2 = new ImageView(getContext());

            //imageView2.setId(i);

            final int finalI = i;
            class Decode extends AsyncTask<Void,Void,Bitmap> {
                @Override
                protected Bitmap doInBackground(Void... params) {

                    if(isAdded()) {
                        return decodeSampledBitmapFromResource(getResources(), fretNumbers[finalI],
                                100, 100);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap tabImage) {
                    if(tabImage == null) {
                        return;
                    }
                    //// FIXME: 10/11/2017
                    series8.appendDataCustom(new DataPoint((noteTimes.get(finalI)),(rowNumbers [finalI])),false,1000,false,new PointsGraphSeries.CustomShape() {
                        @Override
                        public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {

                            Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                            canvas.drawBitmap(finalbitmap,x,y-73,null);
                        }
                    });
                }
            }

            new Decode().execute();
        }

        graph4.addSeries(series8);

    }

    public void postHarmonicaTab(float duration, final ArrayList<Long> noteTimes, final int [] rowNumbers, final int [] fretNumbers){

                int xSet = 20;
                int array30Count = 0;
                int secCount = 0;

                final FrameLayout innerlayout = (FrameLayout) getView().findViewById(R.id.frame5);

                innerlayout.setVisibility(View.VISIBLE);
                //innerlayout.removeAllViews();

                final TextView harmonicaType = (TextView) getView().findViewById(R.id.tabType5);
                final TextView harmonicaSwipe = (TextView) getView().findViewById(R.id.harmonicaSwipeHint);
                final Button harmonicaSave = (Button) getView().findViewById(R.id.btnSave5);
                final LinearLayout harmonicaEmpty = (LinearLayout) getView().findViewById(R.id.llHarmonicaEmpty);

                harmonicaEmpty.setVisibility(View.GONE);
                harmonicaSave.setVisibility(View.VISIBLE);
                harmonicaType.setVisibility(View.VISIBLE);
                harmonicaSwipe.setVisibility(View.VISIBLE);

        EventBus.getDefault().post(new ListenFragment.Loading(false));

                final int dimensionInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel2, getResources().getDisplayMetrics());

        graph5 = (GraphView) getView().findViewById(R.id.graph5);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph5);

        staticLabelsFormatter.setVerticalLabels(new String[] {"","", "", "D  ","B  ","","",""});

        graph5.getGridLabelRenderer().setTextSize(100);
        graph5.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);

        graph5.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph5.getGridLabelRenderer().setLabelsSpace(150);
        graph5.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);

        graph5.setBackgroundColor(Color.WHITE);

        graph5.getGridLabelRenderer().setLabelFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LeagueSpartan-Bold.otf"));
        graph5.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph5.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph5.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph5.getGridLabelRenderer().setHighlightZeroLines(false);
        graph5.getViewport().setScrollable(true);
        graph5.getViewport().setYAxisBoundsManual(true);
        graph5.getViewport().setXAxisBoundsManual(true);
        graph5.getViewport().setMinY(0);
        graph5.getViewport().setMaxY(7);
        graph5.getViewport().setMinX(0);
        graph5.getViewport().setMaxX(100);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(220, 4),
        });
        graph5.addSeries(series3);
        series3.setColor(Color.BLACK);
        series3.setThickness(30);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(220, 3),
        });
        graph5.addSeries(series4);
        series4.setColor(Color.BLACK);
        series4.setThickness(30);

        final PointsGraphSeries<DataPoint> series7 = new PointsGraphSeries<>();
        final PointsGraphSeries<DataPoint> series9 = new PointsGraphSeries<>();

        for(; 0 <= duration; duration -= 14) {
            final int finalXSet = xSet;

            if(secCount == 29) {

                if(array30Count <= 14) {

                    final int tempArray30Count = array30Count;

                    class Decode extends AsyncTask<Void,Void,Bitmap> {
                        @Override
                        protected Bitmap doInBackground(Void... params) {

                            if(isAdded()) {
                                return decodeSampledBitmapFromResource(getResources(), timeMarkBy30[tempArray30Count],
                                        dimensionInDp2, dimensionInDp2);
                            } else {
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(final Bitmap tabImage) {
                            if(tabImage == null) {
                                return;
                            }
                            series9.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                                @Override
                                public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                    Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                                    canvas.drawBitmap(finalbitmap, x - 68, y, null);
                                }
                            });
                        }
                    }

                    new Decode().execute();

                    array30Count++;
                    Log.e("<DEBUG>","The value of tempArray30Count is "+ tempArray30Count);
                    xSet += 8;
                    secCount = 0;
                }
            }
            else {

                class Decode extends AsyncTask<Void,Void,Bitmap> {
                    @Override
                    protected Bitmap doInBackground(Void... params) {

                        if(isAdded()) {
                            return decodeSampledBitmapFromResource(getResources(), sec,
                                    7, 20);
                        }
                        else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(final Bitmap tabImage) {
                        if(tabImage == null) {
                            return;
                        }
                        series7.appendDataCustom(new DataPoint(finalXSet,7),false,1000,false,new PointsGraphSeries.CustomShape() {
                            @Override
                            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                                Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,14,30,false);

                                canvas.drawBitmap(finalbitmap, x, y, null);
                            }
                        });
                    }
                }

                new Decode().execute();

                secCount++;
                Log.e("<DEBUG>","The value of secCount is "+ secCount);

                xSet += 8;
            }
        }

        graph5.addSeries(series7);
        graph5.addSeries(series9);


        final PointsGraphSeries<DataPoint> series8 = new PointsGraphSeries<>();

        for (int i = 0; i < fretNumbers.length; i++) {

            //final ImageView imageView2 = new ImageView(getContext());

            //imageView2.setId(i);

            final int finalI = i;
            class Decode extends AsyncTask<Void,Void,Bitmap> {
                @Override
                protected Bitmap doInBackground(Void... params) {

                    if(isAdded()) {
                        return decodeSampledBitmapFromResource(getResources(), fretNumbers[finalI],
                                100, 100);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap tabImage) {
                    if(tabImage == null) {
                        return;
                    }
                    //// FIXME: 10/11/2017
                    series8.appendDataCustom(new DataPoint((noteTimes.get(finalI)),(rowNumbers [finalI])),false,1000,false,new PointsGraphSeries.CustomShape() {
                        @Override
                        public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {

                            Bitmap finalbitmap = Bitmap.createScaledBitmap(tabImage,150,150,false);

                            canvas.drawBitmap(finalbitmap,x,y-73,null);
                        }
                    });
                }
            }

            new Decode().execute();
        }

        graph5.addSeries(series8);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_stand,container,false);

        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.tablafy_launcher);
        builder.setMessage("Maximum fifteen minute music recording limit reached.")
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();

        tabViewFlipper = (ViewFlipper) view.findViewById(R.id.tabFlipper); // get the reference of ViewFlipper

        final GraphView graph = (GraphView) view.findViewById(R.id.graph);
        final FrameLayout innerlayout = (FrameLayout) view.findViewById(R.id.frame);
        final Button savebtn = (Button) view.findViewById(R.id.btnSave);
        final TextView tabType = (TextView) view.findViewById(R.id.tabType);
        final TextView swipeHint = (TextView) view.findViewById(R.id.guitarSwipeHint);

        final GraphView graph2 = (GraphView) view.findViewById(R.id.graph2);
        final FrameLayout innerlayout2 = (FrameLayout) view.findViewById(R.id.frame2);
        final Button savebtn2 = (Button) view.findViewById(R.id.btnSave2);
        final TextView tabType2 = (TextView) view.findViewById(R.id.tabType2);
        final TextView swipeHint2 = (TextView) view.findViewById(R.id.pianoSwipeHint);

        final GraphView graph3 = (GraphView) view.findViewById(R.id.graph3);
        final FrameLayout innerlayout3 = (FrameLayout) view.findViewById(R.id.frame3);
        final Button savebtn3 = (Button) view.findViewById(R.id.btnSave3);
        final TextView tabType3 = (TextView) view.findViewById(R.id.tabType3);
        final TextView swipeHint3 = (TextView) view.findViewById(R.id.bassSwipeHint);

        final GraphView graph4 = (GraphView) view.findViewById(R.id.graph4);
        final FrameLayout innerlayout4 = (FrameLayout) view.findViewById(R.id.frame4);
        final Button savebtn4 = (Button) view.findViewById(R.id.btnSave4);
        final TextView tabType4 = (TextView) view.findViewById(R.id.tabType4);
        final TextView swipeHint4 = (TextView) view.findViewById(R.id.ukuleleSwipeHint);

        final GraphView graph5 = (GraphView) view.findViewById(R.id.graph5);
        final FrameLayout innerlayout5 = (FrameLayout) view.findViewById(R.id.frame5);
        final Button savebtn5 = (Button) view.findViewById(R.id.btnSave5);
        final TextView tabType5 = (TextView) view.findViewById(R.id.tabType5);
        final TextView swipeHint5 = (TextView) view.findViewById(R.id.harmonicaSwipeHint);

        final TextView tabName = (TextView) view.findViewById(R.id.tab_name);

        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);

        // set the animation type to ViewFlipper
        tabViewFlipper.setInAnimation(in);
        tabViewFlipper.setOutAnimation(out);

        dbHelper = new DbHelper(getContext());

        //HANDLE EVENTS
        savebtn.setOnClickListener(new View.OnClickListener() {

            private void openDialog() {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View subView = inflater.inflate(R.layout.dialog_text, null);
                final EditText reminderEditText = (EditText) subView.findViewById(R.id.et);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter a name for your tab.");
                builder.setView(subView);

                builder.setPositiveButton("SAVE", null);
                builder.setNegativeButton("CANCEL", null);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tab = String.valueOf(reminderEditText.getText());
                                if(!tab.isEmpty() && tab.length() > 0) {
                                    if(!dbHelper.getChannelList().contains(tab)) {

                                        //ADD TO DATABASE
                                        dbHelper.insertNewChannel(tab);

                                        //ADD TO TAB TITLE LABEL
                                        tabName.setText(tab);
                                        savebtn.setVisibility(View.GONE);
                                        tabType.setVisibility(View.GONE);
                                        swipeHint.setVisibility(View.GONE);
                                        savebtn2.setVisibility(View.GONE);
                                        tabType2.setVisibility(View.GONE);
                                        swipeHint2.setVisibility(View.GONE);
                                        savebtn3.setVisibility(View.GONE);
                                        tabType3.setVisibility(View.GONE);
                                        swipeHint3.setVisibility(View.GONE);
                                        savebtn4.setVisibility(View.GONE);
                                        tabType4.setVisibility(View.GONE);
                                        swipeHint4.setVisibility(View.GONE);
                                        savebtn5.setVisibility(View.GONE);
                                        tabType5.setVisibility(View.GONE);
                                        swipeHint5.setVisibility(View.GONE);

                                        Toast.makeText(getContext(), "\u201C"+tab+"\u201D"+" "+"has been added to your tab book.", Toast.LENGTH_SHORT).show();
                                        //CLOSES DIALOG
                                        dialog.dismiss();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("\u201C"+tab+"\u201D"+" "+"is already in use please choose another name for your tab.")
                                                .setNegativeButton(ok, null)
                                                .create()
                                                .show();
                                    }
                                } else {
                                    //DOES NOT CLOSE DIALOG
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Please enter a name.")
                                            .setNegativeButton(ok, null)
                                            .create()
                                            .show();
                                }
                            }
                        });

                        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //CLOSE THE DIALOG
                                dialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        savebtn2.setOnClickListener(new View.OnClickListener() {

            private void openDialog() {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View subView = inflater.inflate(R.layout.dialog_text, null);
                final EditText reminderEditText = (EditText) subView.findViewById(R.id.et);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter a name for your tab.");
                builder.setView(subView);

                builder.setPositiveButton("SAVE", null);
                builder.setNegativeButton("CANCEL", null);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tab = String.valueOf(reminderEditText.getText());
                                if(!tab.isEmpty() && tab.length() > 0) {
                                    if(!dbHelper.getChannelList().contains(tab)) {

                                        //ADD TO DATABASE
                                        dbHelper.insertNewChannel(tab);

                                        //ADD TO TAB TITLE LABEL
                                        tabName.setText(tab);
                                        savebtn.setVisibility(View.GONE);
                                        tabType.setVisibility(View.GONE);
                                        swipeHint.setVisibility(View.GONE);
                                        savebtn2.setVisibility(View.GONE);
                                        tabType2.setVisibility(View.GONE);
                                        swipeHint2.setVisibility(View.GONE);
                                        savebtn3.setVisibility(View.GONE);
                                        tabType3.setVisibility(View.GONE);
                                        swipeHint3.setVisibility(View.GONE);
                                        savebtn4.setVisibility(View.GONE);
                                        tabType4.setVisibility(View.GONE);
                                        swipeHint4.setVisibility(View.GONE);
                                        savebtn5.setVisibility(View.GONE);
                                        tabType5.setVisibility(View.GONE);
                                        swipeHint5.setVisibility(View.GONE);

                                        Toast.makeText(getContext(), "\u201C"+tab+"\u201D"+" "+"has been added to your tab book.", Toast.LENGTH_SHORT).show();
                                        //CLOSES DIALOG
                                        dialog.dismiss();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("\u201C"+tab+"\u201D"+" "+"is already in use please choose another name for your tab.")
                                                .setNegativeButton(ok, null)
                                                .create()
                                                .show();
                                    }
                                } else {
                                    //DOES NOT CLOSE DIALOG
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Please enter a name.")
                                            .setNegativeButton(ok, null)
                                            .create()
                                            .show();
                                }
                            }
                        });

                        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //CLOSE THE DIALOG
                                dialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        savebtn3.setOnClickListener(new View.OnClickListener() {

            private void openDialog() {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View subView = inflater.inflate(R.layout.dialog_text, null);
                final EditText reminderEditText = (EditText) subView.findViewById(R.id.et);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter a name for your tab.");
                builder.setView(subView);

                builder.setPositiveButton("SAVE", null);
                builder.setNegativeButton("CANCEL", null);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tab = String.valueOf(reminderEditText.getText());
                                if(!tab.isEmpty() && tab.length() > 0) {
                                    if(!dbHelper.getChannelList().contains(tab)) {

                                        //ADD TO DATABASE
                                        dbHelper.insertNewChannel(tab);

                                        //ADD TO TAB TITLE LABEL
                                        tabName.setText(tab);
                                        savebtn.setVisibility(View.GONE);
                                        tabType.setVisibility(View.GONE);
                                        swipeHint.setVisibility(View.GONE);
                                        savebtn2.setVisibility(View.GONE);
                                        tabType2.setVisibility(View.GONE);
                                        swipeHint2.setVisibility(View.GONE);
                                        savebtn3.setVisibility(View.GONE);
                                        tabType3.setVisibility(View.GONE);
                                        swipeHint3.setVisibility(View.GONE);
                                        savebtn4.setVisibility(View.GONE);
                                        tabType4.setVisibility(View.GONE);
                                        swipeHint4.setVisibility(View.GONE);
                                        savebtn5.setVisibility(View.GONE);
                                        tabType5.setVisibility(View.GONE);
                                        swipeHint5.setVisibility(View.GONE);

                                        Toast.makeText(getContext(), "\u201C"+tab+"\u201D"+" "+"has been added to your tab book.", Toast.LENGTH_SHORT).show();
                                        //CLOSES DIALOG
                                        dialog.dismiss();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("\u201C"+tab+"\u201D"+" "+"is already in use please choose another name for your tab.")
                                                .setNegativeButton(ok, null)
                                                .create()
                                                .show();
                                    }
                                } else {
                                    //DOES NOT CLOSE DIALOG
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Please enter a name.")
                                            .setNegativeButton(ok, null)
                                            .create()
                                            .show();
                                }
                            }
                        });

                        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //CLOSE THE DIALOG
                                dialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        savebtn4.setOnClickListener(new View.OnClickListener() {

            private void openDialog() {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View subView = inflater.inflate(R.layout.dialog_text, null);
                final EditText reminderEditText = (EditText) subView.findViewById(R.id.et);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter a name for your tab.");
                builder.setView(subView);

                builder.setPositiveButton("SAVE", null);
                builder.setNegativeButton("CANCEL", null);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tab = String.valueOf(reminderEditText.getText());
                                if(!tab.isEmpty() && tab.length() > 0) {
                                    if(!dbHelper.getChannelList().contains(tab)) {

                                        //ADD TO DATABASE
                                        dbHelper.insertNewChannel(tab);

                                        //ADD TO TAB TITLE LABEL
                                        tabName.setText(tab);
                                        savebtn.setVisibility(View.GONE);
                                        tabType.setVisibility(View.GONE);
                                        swipeHint.setVisibility(View.GONE);
                                        savebtn2.setVisibility(View.GONE);
                                        tabType2.setVisibility(View.GONE);
                                        swipeHint2.setVisibility(View.GONE);
                                        savebtn3.setVisibility(View.GONE);
                                        tabType3.setVisibility(View.GONE);
                                        swipeHint3.setVisibility(View.GONE);
                                        savebtn4.setVisibility(View.GONE);
                                        tabType4.setVisibility(View.GONE);
                                        swipeHint4.setVisibility(View.GONE);
                                        savebtn5.setVisibility(View.GONE);
                                        tabType5.setVisibility(View.GONE);
                                        swipeHint5.setVisibility(View.GONE);

                                        Toast.makeText(getContext(), "\u201C"+tab+"\u201D"+" "+"has been added to your tab book.", Toast.LENGTH_SHORT).show();
                                        //CLOSES DIALOG
                                        dialog.dismiss();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("\u201C"+tab+"\u201D"+" "+"is already in use please choose another name for your tab.")
                                                .setNegativeButton(ok, null)
                                                .create()
                                                .show();
                                    }
                                } else {
                                    //DOES NOT CLOSE DIALOG
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Please enter a name.")
                                            .setNegativeButton(ok, null)
                                            .create()
                                            .show();
                                }
                            }
                        });

                        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //CLOSE THE DIALOG
                                dialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        savebtn5.setOnClickListener(new View.OnClickListener() {

            private void openDialog() {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View subView = inflater.inflate(R.layout.dialog_text, null);
                final EditText reminderEditText = (EditText) subView.findViewById(R.id.et);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter a name for your tab.");
                builder.setView(subView);

                builder.setPositiveButton("SAVE", null);
                builder.setNegativeButton("CANCEL", null);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tab = String.valueOf(reminderEditText.getText());
                                if(!tab.isEmpty() && tab.length() > 0) {
                                    if(!dbHelper.getChannelList().contains(tab)) {

                                        //ADD TO DATABASE
                                        dbHelper.insertNewChannel(tab);

                                        //ADD TO TAB TITLE LABEL
                                        tabName.setText(tab);
                                        savebtn.setVisibility(View.GONE);
                                        tabType.setVisibility(View.GONE);
                                        swipeHint.setVisibility(View.GONE);
                                        savebtn2.setVisibility(View.GONE);
                                        tabType2.setVisibility(View.GONE);
                                        swipeHint2.setVisibility(View.GONE);
                                        savebtn3.setVisibility(View.GONE);
                                        tabType3.setVisibility(View.GONE);
                                        swipeHint3.setVisibility(View.GONE);
                                        savebtn4.setVisibility(View.GONE);
                                        tabType4.setVisibility(View.GONE);
                                        swipeHint4.setVisibility(View.GONE);
                                        savebtn5.setVisibility(View.GONE);
                                        tabType5.setVisibility(View.GONE);
                                        swipeHint5.setVisibility(View.GONE);

                                        Toast.makeText(getContext(), "\u201C"+tab+"\u201D"+" "+"has been added to your tab book.", Toast.LENGTH_SHORT).show();
                                        //CLOSES DIALOG
                                        dialog.dismiss();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("\u201C"+tab+"\u201D"+" "+"is already in use please choose another name for your tab.")
                                                .setNegativeButton(ok, null)
                                                .create()
                                                .show();
                                    }
                                } else {
                                    //DOES NOT CLOSE DIALOG
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Please enter a name.")
                                            .setNegativeButton(ok, null)
                                            .create()
                                            .show();
                                }
                            }
                        });

                        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //CLOSE THE DIALOG
                                dialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        graph.setOnTouchListener(new View.OnTouchListener() {
            private float xWhenDown,yWhenDown;
            private long lastTouchDown;
            private int CLICK_ACTION_THRESHHOLD = 20;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.requestDisallowInterceptTouchEvent(true);          //disable tab swipe
                int action = MotionEventCompat.getActionMasked(motionEvent);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if(graph.getViewport().repeat)
                            graph.getViewport().repeat = false;
                        else {
                            graph.getViewport().repeat = true;
                        }
                        xWhenDown = motionEvent.getX();
                        yWhenDown = motionEvent.getY();
                        lastTouchDown = System.currentTimeMillis();
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown > CLICK_ACTION_THRESHHOLD) {
                            if(motionEvent.getY() == yWhenDown && motionEvent.getX() == xWhenDown) {
                                if (savebtn.getVisibility() == View.VISIBLE) {
                                    savebtn.setVisibility(View.GONE);
                                    tabType.setVisibility(View.GONE);
                                    swipeHint.setVisibility(View.GONE);
                                    savebtn2.setVisibility(View.GONE);
                                    tabType2.setVisibility(View.GONE);
                                    swipeHint2.setVisibility(View.GONE);
                                    savebtn3.setVisibility(View.GONE);
                                    tabType3.setVisibility(View.GONE);
                                    swipeHint3.setVisibility(View.GONE);
                                    savebtn4.setVisibility(View.GONE);
                                    tabType4.setVisibility(View.GONE);
                                    swipeHint4.setVisibility(View.GONE);
                                    savebtn5.setVisibility(View.GONE);
                                    tabType5.setVisibility(View.GONE);
                                    swipeHint5.setVisibility(View.GONE);
                                } else {
                                    if(!dbHelper.getChannelList().contains(tabName.getText().toString())) {
                                        savebtn.setVisibility(View.VISIBLE);
                                    }

                                    tabType.setVisibility(View.VISIBLE);
                                    swipeHint.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            savebtn.setVisibility(View.GONE);
                                            tabType.setVisibility(View.GONE);
                                            swipeHint.setVisibility(View.GONE);           }
                                    }, 1850);
                                }
                            }
                            else if((motionEvent.getY() < yWhenDown - 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showNext();
                            }
                            else if((motionEvent.getY() > yWhenDown + 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showPrevious();
                            }
                        }

                        return false;
                }
                return false;
            }
        });


        graph2.setOnTouchListener(new View.OnTouchListener() {
            private float xWhenDown,yWhenDown;
            private long lastTouchDown;
            private int CLICK_ACTION_THRESHHOLD = 20;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.requestDisallowInterceptTouchEvent(true);          //disable tab swipe
                int action = MotionEventCompat.getActionMasked(motionEvent);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if(graph2.getViewport().repeat)
                            graph2.getViewport().repeat = false;
                        else {
                            graph2.getViewport().repeat = true;
                        }
                        xWhenDown = motionEvent.getX();
                        yWhenDown = motionEvent.getY();
                        lastTouchDown = System.currentTimeMillis();
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown > CLICK_ACTION_THRESHHOLD) {
                            if(motionEvent.getY() == yWhenDown && motionEvent.getX() == xWhenDown) {
                                if (savebtn2.getVisibility() == View.VISIBLE) {
                                    savebtn.setVisibility(View.GONE);
                                    tabType.setVisibility(View.GONE);
                                    swipeHint.setVisibility(View.GONE);
                                    savebtn2.setVisibility(View.GONE);
                                    tabType2.setVisibility(View.GONE);
                                    swipeHint2.setVisibility(View.GONE);
                                    savebtn3.setVisibility(View.GONE);
                                    tabType3.setVisibility(View.GONE);
                                    swipeHint3.setVisibility(View.GONE);
                                    savebtn4.setVisibility(View.GONE);
                                    tabType4.setVisibility(View.GONE);
                                    swipeHint4.setVisibility(View.GONE);
                                    savebtn5.setVisibility(View.GONE);
                                    tabType5.setVisibility(View.GONE);
                                    swipeHint5.setVisibility(View.GONE);
                                } else {
                                    if(!dbHelper.getChannelList().contains(tabName.getText().toString())) {
                                        savebtn2.setVisibility(View.VISIBLE);
                                    }

                                    tabType2.setVisibility(View.VISIBLE);
                                    swipeHint2.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            savebtn2.setVisibility(View.GONE);
                                            tabType2.setVisibility(View.GONE);
                                            swipeHint2.setVisibility(View.GONE);           }
                                    }, 1850);
                                }
                            }
                            else if((motionEvent.getY() < yWhenDown - 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showNext();
                            }
                            else if((motionEvent.getY() > yWhenDown + 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showPrevious();
                            }
                        }
                        return false;
                }
                return false;
            }
        });

        graph3.setOnTouchListener(new View.OnTouchListener() {
            private float xWhenDown,yWhenDown;
            private long lastTouchDown;
            private int CLICK_ACTION_THRESHHOLD = 20;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.requestDisallowInterceptTouchEvent(true);          //disable tab swipe
                int action = MotionEventCompat.getActionMasked(motionEvent);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if(graph3.getViewport().repeat)
                            graph3.getViewport().repeat = false;
                        else {
                            graph3.getViewport().repeat = true;
                        }
                        xWhenDown = motionEvent.getX();
                        yWhenDown = motionEvent.getY();
                        lastTouchDown = System.currentTimeMillis();
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown > CLICK_ACTION_THRESHHOLD) {
                            if(motionEvent.getY() == yWhenDown && motionEvent.getX() == xWhenDown) {
                                if (savebtn3.getVisibility() == View.VISIBLE) {
                                    savebtn.setVisibility(View.GONE);
                                    tabType.setVisibility(View.GONE);
                                    swipeHint.setVisibility(View.GONE);
                                    savebtn2.setVisibility(View.GONE);
                                    tabType2.setVisibility(View.GONE);
                                    swipeHint2.setVisibility(View.GONE);
                                    savebtn3.setVisibility(View.GONE);
                                    tabType3.setVisibility(View.GONE);
                                    swipeHint3.setVisibility(View.GONE);
                                    savebtn4.setVisibility(View.GONE);
                                    tabType4.setVisibility(View.GONE);
                                    swipeHint4.setVisibility(View.GONE);
                                    savebtn5.setVisibility(View.GONE);
                                    tabType5.setVisibility(View.GONE);
                                    swipeHint5.setVisibility(View.GONE);
                                } else {
                                    if(!dbHelper.getChannelList().contains(tabName.getText().toString())) {
                                        savebtn3.setVisibility(View.VISIBLE);
                                    }

                                    tabType3.setVisibility(View.VISIBLE);
                                    swipeHint3.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            savebtn3.setVisibility(View.GONE);
                                            tabType3.setVisibility(View.GONE);
                                            swipeHint3.setVisibility(View.GONE);           }
                                    }, 1850);
                                }
                            }
                            else if((motionEvent.getY() < yWhenDown - 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showNext();
                            }
                            else if((motionEvent.getY() > yWhenDown + 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showPrevious();
                            }
                        }
                        return false;
                }
                return false;
            }
        });

        graph4.setOnTouchListener(new View.OnTouchListener() {
            private float xWhenDown,yWhenDown;
            private long lastTouchDown;
            private int CLICK_ACTION_THRESHHOLD = 20;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.requestDisallowInterceptTouchEvent(true);          //disable tab swipe
                int action = MotionEventCompat.getActionMasked(motionEvent);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if(graph4.getViewport().repeat)
                            graph4.getViewport().repeat = false;
                        else {
                            graph4.getViewport().repeat = true;
                        }
                        xWhenDown = motionEvent.getX();
                        yWhenDown = motionEvent.getY();
                        lastTouchDown = System.currentTimeMillis();
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown > CLICK_ACTION_THRESHHOLD) {
                            if(motionEvent.getY() == yWhenDown && motionEvent.getX() == xWhenDown) {
                                if (savebtn4.getVisibility() == View.VISIBLE) {
                                    savebtn.setVisibility(View.GONE);
                                    tabType.setVisibility(View.GONE);
                                    swipeHint.setVisibility(View.GONE);
                                    savebtn2.setVisibility(View.GONE);
                                    tabType2.setVisibility(View.GONE);
                                    swipeHint2.setVisibility(View.GONE);
                                    savebtn3.setVisibility(View.GONE);
                                    tabType3.setVisibility(View.GONE);
                                    swipeHint3.setVisibility(View.GONE);
                                    savebtn4.setVisibility(View.GONE);
                                    tabType4.setVisibility(View.GONE);
                                    swipeHint4.setVisibility(View.GONE);
                                    savebtn5.setVisibility(View.GONE);
                                    tabType5.setVisibility(View.GONE);
                                    swipeHint5.setVisibility(View.GONE);
                                } else {
                                    if(!dbHelper.getChannelList().contains(tabName.getText().toString())) {
                                        savebtn4.setVisibility(View.VISIBLE);
                                    }

                                    tabType4.setVisibility(View.VISIBLE);
                                    swipeHint4.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            savebtn4.setVisibility(View.GONE);
                                            tabType4.setVisibility(View.GONE);
                                            swipeHint4.setVisibility(View.GONE);           }
                                    }, 1850);
                                }
                            }
                            else if((motionEvent.getY() < yWhenDown - 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showNext();
                            }
                            else if((motionEvent.getY() > yWhenDown + 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showPrevious();
                            }
                        }


                        return false;
                }
                return false;
            }
        });

        graph5.setOnTouchListener(new View.OnTouchListener() {
            private float xWhenDown,yWhenDown;
            private long lastTouchDown;
            private int CLICK_ACTION_THRESHHOLD = 20;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.requestDisallowInterceptTouchEvent(true);          //disable tab swipe
                int action = MotionEventCompat.getActionMasked(motionEvent);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if(graph5.getViewport().repeat)
                            graph5.getViewport().repeat = false;
                        else {
                            graph5 .getViewport().repeat = true;
                        }
                        xWhenDown = motionEvent.getX();
                        yWhenDown = motionEvent.getY();
                        lastTouchDown = System.currentTimeMillis();
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown > CLICK_ACTION_THRESHHOLD) {
                            if(motionEvent.getY() == yWhenDown && motionEvent.getX() == xWhenDown) {
                                if (savebtn5.getVisibility() == View.VISIBLE) {
                                    savebtn.setVisibility(View.GONE);
                                    tabType.setVisibility(View.GONE);
                                    swipeHint.setVisibility(View.GONE);
                                    savebtn2.setVisibility(View.GONE);
                                    tabType2.setVisibility(View.GONE);
                                    swipeHint2.setVisibility(View.GONE);
                                    savebtn3.setVisibility(View.GONE);
                                    tabType3.setVisibility(View.GONE);
                                    swipeHint3.setVisibility(View.GONE);
                                    savebtn4.setVisibility(View.GONE);
                                    tabType4.setVisibility(View.GONE);
                                    swipeHint4.setVisibility(View.GONE);
                                    savebtn5.setVisibility(View.GONE);
                                    tabType5.setVisibility(View.GONE);
                                    swipeHint5.setVisibility(View.GONE);
                                } else {
                                    if(!dbHelper.getChannelList().contains(tabName.getText().toString())) {
                                        savebtn5.setVisibility(View.VISIBLE);
                                    }

                                    tabType5.setVisibility(View.VISIBLE);
                                    swipeHint5.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            savebtn5.setVisibility(View.GONE);
                                            tabType5.setVisibility(View.GONE);
                                            swipeHint5.setVisibility(View.GONE);           }
                                    }, 1850);
                                }
                            }
                            else if((motionEvent.getY() < yWhenDown - 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showNext();
                            }
                            else if((motionEvent.getY() > yWhenDown + 90) && (motionEvent.getX() != xWhenDown)) {
                                tabViewFlipper.showPrevious();
                            }
                        }

                        return false;
                }
                return false;
            }
        });


       return view;
    }

    // ADD
    private void setup () {
        String tab = tabName.getText().toString();

        if(!tab.isEmpty() && tab.length() > 0) {

            if(!dbHelper.getChannelList().contains(tab)) {

                //ADD TO DATABASE
                dbHelper.insertNewChannel(tab);

                tabName.setText("");

                Toast.makeText(getContext(), "\u201C"+tab+"\u201D"+" "+"has been added to your tab book.", Toast.LENGTH_SHORT).show();
            } else {
                tabName.setText("");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("\u201C"+tab+"\u201D"+" "+"is already in use please choose another name for your tab.")
                        .setNegativeButton(ok, null)
                        .create()
                        .show();
            }

        } else {

        }
    }
}
