package com.jantzapps.jantz.tablafy;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by jantz on 8/18/2017.
 */

public class CreateTabs {

    public static class FormTab{
        private final String instrument;
        private final Long time;
        private final ArrayList<Long> noteTimes;
        private final int [] rowNumbers;
        private final int [] playNumbers;

        public FormTab(String instrument, Long total, ArrayList<Long> noteTimes, int[] rowNumbers, int [] playNumbers) {
            this.instrument = instrument;
            this.time = total;
            this.noteTimes = noteTimes;
            this.rowNumbers = rowNumbers;
            this.playNumbers = playNumbers;
        }

        public Long getDuration() {
            return time;
        }
        public ArrayList<Long> getNoteTimes() {
            return noteTimes;
        }
        public int [] getRowNumbers() {
            return rowNumbers;
        }
        public int [] getPlayNumbers() {
            return playNumbers;
        }
        public String getInstrument() {
            return instrument;
        }
    }

public static void createGuitarTab (final String process, final Long totalDuration, final ArrayList<Long> noteTimes, ArrayList<Float> notePitches) {
    final String instrument = "guitar";
    final ArrayList <Double> testNotes = new ArrayList<>();

    final double minRange = 82.40;
    final double maxRange = 1174.72;

    testNotes.add(83.50);
    testNotes.add(92.56);
    testNotes.add(110.12);
    testNotes.add(123.48);
    testNotes.add(138.60);
    testNotes.add(123.48);
    testNotes.add(138.60);
    testNotes.add(123.48);
    testNotes.add(110.12);
    testNotes.add(123.48);
    testNotes.add(138.60);
    testNotes.add(123.48);

    class GuitarSetupBackground extends AsyncTask<Void,Void,StructureGuitar> {
        @Override
        protected StructureGuitar doInBackground(Void... params) {
            return new StructureGuitar(process,minRange,maxRange,testNotes);
        }

        @Override
        protected void onPostExecute(StructureGuitar guitar) {
            int drawables [];
            int rowNumber [];
            drawables = guitar.frets;
            rowNumber = guitar.strings;

            EventBus.getDefault().post(new CreateTabs.FormTab(instrument,totalDuration,noteTimes,rowNumber,drawables));
        }
    }

    new GuitarSetupBackground().execute();

    //StructureGuitar guitar = new StructureGuitar(process,minRange,maxRange,testNotes);
    //drawables = guitar.frets;
    //rowNumber = guitar.strings;

/*
    for (int i = 0; i < noteTimes.size(); i++) {
        if(notePitches.get(i) > 80 && notePitches.get(i) < 84) {
            drawables.add(R.drawable.tab_22);
            rowNumber.add(880);
        }
    }
    drawables.add(R.drawable.tab_18);
    drawables.add(R.drawable.tab_20);
    rowNumber.add(0);
    rowNumber.add(150);
    */


}
    public static void createPianoTab (final String process, final Long totalDuration, final ArrayList<Long> noteTimes, ArrayList<Float> notePitches) {
        final String instrument = "piano";

        final ArrayList <Double> testNotes = new ArrayList<>();

        final double minRange = 65.40;
        final double maxRange = 987.77;

        testNotes.add(83.50);
        testNotes.add(92.56);
        testNotes.add(110.12);
        testNotes.add(123.48);
        testNotes.add(138.60);


        class PianoSetupBackground extends AsyncTask<Void,Void,StructurePiano> {
            @Override
            protected StructurePiano doInBackground(Void... params) {
                return new StructurePiano(process,minRange,maxRange,testNotes);
            }

            @Override
            protected void onPostExecute(StructurePiano piano) {
                int drawables [];
                int rowNumber [];
                rowNumber = piano.octaves;
                drawables = piano.notes;

                EventBus.getDefault().post(new CreateTabs.FormTab(instrument,totalDuration,noteTimes,rowNumber,drawables));
            }
        }

        new PianoSetupBackground().execute();

        /*
        for (int i = 0; i < noteTimes.size(); i++) {
            if(notePitches.get(i) > 80 && notePitches.get(i) < 84) {
                drawables.add(R.drawable.tab_e);
                rowNumber.add(880);
            }
        }
        drawables.add(R.drawable.tab_g_upper);
        drawables.add(R.drawable.tab_b);
        rowNumber.add(0);
        rowNumber.add(150);
        */

    }
    public static void createBassTab (final String process, final Long totalDuration, final ArrayList<Long> noteTimes, ArrayList<Float> notePitches) {
        final String instrument = "bass";

        final ArrayList <Double> testNotes = new ArrayList<>();

        final double minRange = 41.20;
        final double maxRange = 311.12;

        testNotes.add(83.50);
        testNotes.add(92.56);
        testNotes.add(110.12);
        testNotes.add(123.48);
        testNotes.add(138.60);



        class BassSetupBackground extends AsyncTask<Void,Void,StructureBass> {
            @Override
            protected StructureBass doInBackground(Void... params) {
                return new StructureBass(process,minRange,maxRange,testNotes);
            }

            @Override
            protected void onPostExecute(StructureBass bass) {
                int drawables [];
                int rowNumber [];
                drawables = bass.frets;
                rowNumber = bass.strings;

                EventBus.getDefault().post(new CreateTabs.FormTab(instrument,totalDuration,noteTimes,rowNumber,drawables));
            }
        }

        new BassSetupBackground().execute();

        /*
        for (int i = 0; i < noteTimes.size(); i++) {
            if(notePitches.get(i) > 80 && notePitches.get(i) < 84) {
                drawables.add(R.drawable.tab_10);
                rowNumber.add(880);
            }
        }
        drawables.add(R.drawable.tab_12);
        drawables.add(R.drawable.tab_14);
        rowNumber.add(0);
        rowNumber.add(150);
        */

    }
    public static void createUkuleleTab (final String process, final Long totalDuration, final ArrayList<Long> noteTimes, ArrayList<Float> notePitches) {
        final String instrument = "ukulele";

        final ArrayList <Double> testNotes = new ArrayList<>();

        final double minRange = 391.92;
        final double maxRange = 880.00;

        testNotes.add(83.50);
        testNotes.add(92.56);
        testNotes.add(110.12);
        testNotes.add(123.48);
        testNotes.add(138.60);



        class UkuleleSetupBackground extends AsyncTask<Void,Void,StructureUkulele> {
            @Override
            protected StructureUkulele doInBackground(Void... params) {
                return new StructureUkulele(process,minRange,maxRange,testNotes);
            }

            @Override
            protected void onPostExecute(StructureUkulele ukulele) {
                int drawables [];
                int rowNumber [];
                drawables = ukulele.frets;
                rowNumber = ukulele.strings;

                EventBus.getDefault().post(new CreateTabs.FormTab(instrument,totalDuration,noteTimes,rowNumber,drawables));
            }
        }

        new UkuleleSetupBackground().execute();

        /*
        for (int i = 0; i < noteTimes.size(); i++) {
            if(notePitches.get(i) > 80 && notePitches.get(i) < 84) {
                drawables.add(R.drawable.tab_2);
                rowNumber.add(880);
            }
        }
        drawables.add(R.drawable.tab_6);
        drawables.add(R.drawable.tab_4);
        rowNumber.add(0);
        rowNumber.add(150);
        */

    }
    public static void createHarmonicaTab (final String process, final Long totalDuration, final ArrayList<Long> noteTimes, ArrayList<Float> notePitches) {
        final String instrument = "harmonica";

        final ArrayList <Double> testNotes = new ArrayList<>();

        final double minRange = 261.60;
        final double maxRange = 2092.80;

        testNotes.add(83.50);
        testNotes.add(92.56);
        testNotes.add(110.12);
        testNotes.add(123.48);
        testNotes.add(138.60);


        class HarmonicaSetupBackground extends AsyncTask<Void,Void,StructureHarmonica> {
            @Override
            protected StructureHarmonica doInBackground(Void... params) {
                return new StructureHarmonica(process,minRange,maxRange,testNotes);
            }

            @Override
            protected void onPostExecute(StructureHarmonica harmonica) {
                int drawables [];
                int rowNumber [];
                rowNumber = harmonica.blowsDraws;
                drawables = harmonica.holes;

                EventBus.getDefault().post(new CreateTabs.FormTab(instrument,totalDuration,noteTimes,rowNumber,drawables));
            }
        }

        new HarmonicaSetupBackground().execute();

        /*
        for (int i = 0; i < noteTimes.size(); i++) {
            if(notePitches.get(i) > 80 && notePitches.get(i) < 84) {
                drawables.add(R.drawable.tab_5);
                rowNumber.add(880);
            }
        }
        drawables.add(R.drawable.tab_6);
        drawables.add(R.drawable.tab_9);
        rowNumber.add(0);
        rowNumber.add(150);
        */


    }

}
